package controlador;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.EntidadDTO;
import dto.EstadoBatallaDTO;
import modelo.acciones.Accion;
import modelo.acciones.Atacar;
import modelo.acciones.Defender;
import modelo.acciones.UsarHabilidad;
import modelo.combate.Batalla;
import modelo.combate.GeneradorDeBatalla;
import modelo.combate.Mercado;
import modelo.combate.Party;
import modelo.entidades.Enemigo;
import modelo.entidades.Entidad;
import modelo.entidades.Personaje;
import modelo.habilidades.Habilidad;
import modelo.habilidades.TipoObjetivo;
import modelo.items.Consumible;
import vista.DialogoEleccion;
import vista.PantallaBatalla;
import vista.PantallaDerrota;
import vista.PantallaEquipamiento;
import vista.PantallaMercado;
import vista.PantallaVictoria;

public class ControladorBatalla {

    private final PantallaBatalla vista;
    private Batalla batalla;
    private final Party party;
    private String mensaje;
    private GeneradorDeBatalla generador;
    private int numeroBatalla;
    private Mercado mercado;
    private int oroGanado = 0;
    private int oroGastado = 0;
    private int enemigosDerrota = 0;
    private int muertesParty = 0;

    
    public ControladorBatalla(PantallaBatalla vista, Batalla batalla, Party party) {
    	this.batalla = batalla;
    	this.party = party;
        this.vista = vista;
        this.mensaje = "Comienza el combate" + "\n";
        this.generador = new GeneradorDeBatalla();
        this.numeroBatalla = 1;
    }
    
    public void iniciar() {
        jugarTurnosEnemigos();
        refrescarVista();
    }
    
    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }

    private void siguienteBatalla() {
        for (Personaje p : party.getIntegrantes()) {
            p.curar(p.getVidaMaxima());
            p.restaurarMana();
        }

        boolean esFinal = (numeroBatalla >= 4);

        PantallaVictoria victoria = new PantallaVictoria(
        	    numeroBatalla, party.getOro(), esFinal,
        	    enemigosDerrota, muertesParty, oroGanado, oroGastado,
        	    () -> {
        	        numeroBatalla++;
        	        List<Enemigo> nuevos = generador.generarEnemigos(numeroBatalla);
        	        batalla = new Batalla(party, nuevos);
        	        batalla.iniciar();
        	        mensaje = "Batalla " + numeroBatalla + " comienza\n";
        	        jugarTurnosEnemigos();
        	        refrescarVista();
        	    },
        	    () -> new PantallaMercado(mercado, party).setVisible(true),
        	    () -> new PantallaEquipamiento(party).setVisible(true),
        	    () -> System.exit(0)
        	);
        victoria.setVisible(true);
    }

    public void atacar() {
        List<Enemigo> vivos = new ArrayList<>();
        	for (Enemigo vivo : batalla.getEnemigo()) {
        		if (vivo.estaViva()) {
    				vivos.add(vivo);
    			}
        	}
        
        if (vivos.isEmpty()) {
        	return;
        }

        Object elegido = DialogoEleccion.mostrar(
        	    vista,
        	    "Elegir objetivo",
        	    "¿A quién atacás?",
        	    vivos.toArray()
        	);

        if (elegido == null) {
            return;
        }
        
        
        Entidad objetivo = (Entidad) elegido;
        boolean estabaVivo = objetivo.estaViva();
        int danio = batalla.ejecutarAccion(new Atacar(), (Entidad) objetivo);
        mensaje = ("El " + batalla.turnoActual().getNombre() + " Ataco a " + objetivo.getNombre() + " realizandole " + danio +"Hp de daño. Vida restante: " + objetivo.getNombre() +": " + objetivo.getVidaActual() + "Hp" + "\n");
        if (estabaVivo && !objetivo.estaViva() && objetivo instanceof Enemigo) {
            Enemigo muerto = (Enemigo) objetivo;
            Personaje rematador = (Personaje) batalla.turnoActual();
            int nivelAntes = rematador.getNivel();

            batalla.otorgarExperiencia(muerto, rematador);
            batalla.otorgarOro(muerto, party);
            
            enemigosDerrota++;
            oroGanado += muerto.recompensaOro();

            mensaje += muerto.getNombre() + " fue derrotado. "
                    + rematador.getNombre() + " gano "
                    + muerto.recompensaExperiencia() + " de experiencia y la party "
                    + muerto.recompensaOro() + " de oro\n";

            if (rematador.getNivel() > nivelAntes) {
                mensaje += rematador.getNombre() + " subio al nivel " + rematador.getNivel() + "\n";
            }
        }
        batalla.avanzarTurno();
        jugarTurnosEnemigos();
        if (batalla.batallaTermino()) {
            if (batalla.ganadorDeBatalla()) {
                siguienteBatalla();
            } else {
                mostrarDerrota();
            }
            return;
        }
        refrescarVista();
    }
    
    private void jugarTurnosEnemigos() {
        while (batalla.turnoActual() instanceof Enemigo && !batalla.batallaTermino()) {
            Enemigo enemigo = (Enemigo) batalla.turnoActual();

            Accion accion = enemigo.decidirAccion(party.getIntegrantes(), batalla.getEnemigo());
            Entidad objetivo = enemigo.elegirObjetivo(party.getIntegrantes(), batalla.getEnemigo());
            int danio = batalla.ejecutarAccion(accion, objetivo);
            if (objetivo instanceof Enemigo) {
                mensaje += "La " + batalla.turnoActual().getNombre() + " curo a " + objetivo.getNombre()
                        + " regenerandole " + danio + "Hp. Vida actual: " + objetivo.getVidaActual() + "Hp\n";
            } else {
                mensaje += "El " + batalla.turnoActual().getNombre() + " ataco a " + objetivo.getNombre()
                        + " realizandole " + danio + "Hp de daño. Vida restante: " + objetivo.getVidaActual() + "Hp\n";
            }
            for (Personaje p : party.getIntegrantes()) {
                if (!p.estaViva()) muertesParty++;
            }
            
            batalla.avanzarTurno();
        }
    }

    public void defender() {
    	batalla.ejecutarAccion(new Defender(), batalla.turnoActual());
    	mensaje = ("El " + batalla.turnoActual().getNombre() + " se esta defendiendo" + "\n");
    	batalla.avanzarTurno();
        jugarTurnosEnemigos();
        if (batalla.batallaTermino()) {
            if (batalla.ganadorDeBatalla()) {
                siguienteBatalla();
            } else {
                mostrarDerrota();
            }
            return;
        }
        refrescarVista();
    }
    
    public void usarHabilidad() {
    	
    	List<Habilidad> habilidades = batalla.turnoActual().getHabilidad();
    	if (habilidades.isEmpty()) {
    		return;
    	}
    	
    	Object seleccion = DialogoEleccion.mostrar(
    		    vista,
    		    "¿Qué habilidad usás?",
    		    "Elegir habilidad",
    		    habilidades.toArray()
    		);
    	
    	if (seleccion == null) {
    		return;
    	}
    	
    	Habilidad habilidad = (Habilidad) seleccion;
    	Entidad objetivo = null;
    	List<Entidad> candidatos = new ArrayList<>();
    	
    	switch (habilidad.getTipoObjetivo()) {
       		case ENEMIGO:
       			List<Enemigo> vivos = new ArrayList<>();
            	for (Enemigo vivo : batalla.getEnemigo()) {
            		if (vivo.estaViva()) {
        				vivos.add(vivo);
        			}
            	}
            
            	if (vivos.isEmpty()) {
            		return;
            	}
            	candidatos.addAll(vivos);
            	
            	Object elegido = DialogoEleccion.mostrar(
            		    vista,
            		    "¿Sobre quién?",
            	        "Elegir objetivo",
            	        vivos.toArray()
            		);
            	if (elegido == null) {
            	        return;
                }
           	    objetivo = (Entidad) elegido;
           	    break;
           	    
       		case ALIADO:
       			candidatos.addAll(party.integrantesVivos());
       			
       			Object personaje = DialogoEleccion.mostrar(
            		    vista,
            		    "¿Sobre quién?",
            	        "Elegir objetivo",
            	        party.integrantesVivos().toArray()
            		);
       			    if (personaje == null) {
       			        return;
       			    }
       			    objetivo = (Entidad) personaje;
       			    break;
  
       		case UNO_MISMO:
       			candidatos.add(batalla.turnoActual());
       			objetivo = batalla.turnoActual();
       			break;
    		}
    	
    	List<Entidad> objetivos = new ArrayList<>();
    	int radio = habilidad.getAlcance() / 2;
    	int posicion = candidatos.indexOf(objetivo);

    	for (int i = posicion - radio; i <= posicion + radio; i++) {
    	    if (i >= 0 && i < candidatos.size()) {
    	        objetivos.add(candidatos.get(i));
    	    }
    	}
    	
    	List<Entidad> vivosAntes = new ArrayList<>();
    	for (Entidad o : objetivos) {
    	    if (o.estaViva()) vivosAntes.add(o);
    	}

    	
    	int danio = batalla.ejecutarAccion(new UsarHabilidad(habilidad, objetivos), objetivo);
    	if (habilidad.getTipoObjetivo() == TipoObjetivo.ALIADO || habilidad.getTipoObjetivo() == TipoObjetivo.UNO_MISMO) {
    	    mensaje = "El " + batalla.turnoActual().getNombre() + " uso " + habilidad.getNombre()
    	            + " sobre " + objetivo.getNombre() + " regenerandole " + danio + "Hp. Vida restante: " + objetivo.getVidaActual() + "Hp\n";
    	} else {
    	    mensaje = "El " + batalla.turnoActual().getNombre() + " uso " + habilidad.getNombre()
    	            + " sobre " + objetivo.getNombre() + " realizandole " + danio + "Hp de daño. Vida restante: "
    	            + objetivo.getVidaActual() + "Hp\n";
    	}
    	
    	for (Entidad o : vivosAntes) {
    	    if (!o.estaViva() && o instanceof Enemigo) {
    	    	enemigosDerrota++;
    	        Enemigo muerto = (Enemigo) o;
    	        Personaje rematador = (Personaje) batalla.turnoActual();
    	        int nivelAntes = rematador.getNivel();
    	        batalla.otorgarExperiencia(muerto, rematador);
    	        batalla.otorgarOro(muerto, party);
    	        
    	        enemigosDerrota++;
    	        oroGanado += muerto.recompensaOro();
    	        
    	        mensaje += muerto.getNombre() + " fue derrotado. "
    	                + rematador.getNombre() + " gano "
    	                + muerto.recompensaExperiencia() + " exp y la party "
    	                + muerto.recompensaOro() + " oro\n";
    	        if (rematador.getNivel() > nivelAntes) {
    	            mensaje += rematador.getNombre() + " subio al nivel " + rematador.getNivel() + "\n";
    	        }
    	    }
    	}
        batalla.avanzarTurno();
        jugarTurnosEnemigos();
        if (batalla.batallaTermino()) {
            if (batalla.ganadorDeBatalla()) {
                siguienteBatalla();
            } else {
                mostrarDerrota();
            }
            return;
        }
        refrescarVista();
    }

    public void usarItem() {
        List<Consumible> consumibles = party.getInventario().filtrarConsumible();
        if (consumibles.isEmpty()) {
            return;
        }

        String[] nombres = new String[consumibles.size()];
        String[] sprites = new String[consumibles.size()];
        for (int i = 0; i < consumibles.size(); i++) {
            nombres[i] = consumibles.get(i).getNombre();
            sprites[i] = "pocion_vida.png";
        }

        Object elegidoItem = DialogoEleccion.mostrar(
            vista, "Elegir ítem", "¿Qué ítem usás?", nombres, sprites
        );
        if (elegidoItem == null) return;

        int idx = -1;
        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i].equals(elegidoItem)) { idx = i; break; }
        }
        if (idx < 0) return;
        Consumible consumible = consumibles.get(idx);

        Object elegidoPersonaje = DialogoEleccion.mostrar(
            vista, "Elegir personaje", "¿Sobre qué personaje?",
            party.integrantesVivos().toArray()
        );
        if (elegidoPersonaje == null) return;

        Personaje personaje = (Personaje) elegidoPersonaje;
        int vidaAntes = personaje.getVidaActual();
        consumible.usarItem(personaje);
        party.getInventario().quitarItem(consumible);
        int vidaRecuperada = personaje.getVidaActual() - vidaAntes;

        mensaje = batalla.turnoActual().getNombre() + " uso " + consumible.getNombre()
                + " sobre " + personaje.getNombre() + " recuperando " + vidaRecuperada + "Hp\n";
        batalla.avanzarTurno();
        jugarTurnosEnemigos();
        if (batalla.batallaTermino() && batalla.ganadorDeBatalla()) {
            siguienteBatalla();
            return;
        }
        refrescarVista();
    }
    
    private void mostrarDerrota() {
        PantallaDerrota derrota = new PantallaDerrota(
            numeroBatalla,
            () -> {
                numeroBatalla = 1;
                oroGanado = 0;
                oroGastado = 0;
                enemigosDerrota = 0;
                muertesParty = 0;
                party.setOro(0);

                for (Personaje p : party.getIntegrantes()) {
                    p.curar(p.getVidaMaxima());
                    p.restaurarMana();
                }

                List<Enemigo> nuevos = generador.generarEnemigos(1);
                batalla = new Batalla(party, nuevos);
                batalla.iniciar();
                mensaje = "Batalla 1 comienza\n";
                jugarTurnosEnemigos();
                refrescarVista();
            },
            () -> System.exit(0)
        );
        derrota.setVisible(true);
    }
    

    // ------------------------------------------------------------
    // Modelo -> DTO -> vista
    // ------------------------------------------------------------

    public void refrescarVista() {

        vista.actualizar(crearEstadoDTO());
    }

    private EstadoBatallaDTO crearEstadoDTO() {

        // TODO: reemplazar por los datos reales de batalla

        List<EntidadDTO> personajes = new ArrayList<>();
        List<EntidadDTO> enemigos = new ArrayList<>();
        
        for (Personaje p : party.getIntegrantes()) {
            personajes.add(convertirEntidad(p, null));
        }
        
        for (Enemigo e : batalla.getEnemigo()) {
            enemigos.add(convertirEntidad(e, null));
        }
        

        return new EstadoBatallaDTO(
        	numeroBatalla,
            personajes,
            enemigos,
            batalla.turnoActual().getNombre(),
            mensaje,
            batalla.batallaTermino(),
            batalla.ganadorDeBatalla()
        );
    }
    private EntidadDTO convertirEntidad(Entidad entidad, String sprite) {
        List<String> estados = entidad.mostrarNombreEstados();
        String spriteNombre = entidad.getNombre().toLowerCase() + ".png";

        int bonoAtq = 0, bonoDef = 0, bonoVel = 0;
        if (entidad instanceof Personaje) {
            Personaje p = (Personaje) entidad;
            for (modelo.items.Equipable eq : p.getEquipo()) {
                bonoAtq += eq.getBonoAtaque();
                bonoDef += eq.getBonoDefensa();
                bonoVel += eq.getBonoVelocidad();
            }
        }

        return new EntidadDTO(
            entidad.getNombre(), entidad.getNivel(),
            entidad.getVidaActual(), entidad.getVidaMaxima(),
            entidad.getManaActual(), entidad.getManaMaximo(),
            entidad.getAtaque(), entidad.getDefensa(), entidad.getVelocidad(),
            estados, spriteNombre,
            bonoAtq, bonoDef, bonoVel
        );
    }
     
}
