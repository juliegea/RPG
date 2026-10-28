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
import modelo.combate.Party;
import modelo.entidades.Enemigo;
import modelo.entidades.Entidad;
import modelo.entidades.Personaje;
import modelo.habilidades.Habilidad;
import modelo.habilidades.TipoObjetivo;
import vista.PantallaBatalla;

public class ControladorBatalla {

    private final PantallaBatalla vista;
    private final Batalla batalla;
    private final Party party;
    private String mensaje;

    
    public ControladorBatalla(PantallaBatalla vista, Batalla batalla, Party party) {
    	this.batalla = batalla;
    	this.party = party;
        this.vista = vista;
        this.mensaje = "Comienza el combate" + "\n";
    }
    
    public void iniciar() {
        jugarTurnosEnemigos();
        refrescarVista();
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

        Object elegido = JOptionPane.showInputDialog(
            vista,
            "¿A quién atacás?",
            "Elegir objetivo",
            JOptionPane.QUESTION_MESSAGE,
            null,
            vivos.toArray(),
            vivos.get(0)
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
            batalla.avanzarTurno();
        }
    }

    public void defender() {
    	batalla.ejecutarAccion(new Defender(), batalla.turnoActual());
    	mensaje = ("El " + batalla.turnoActual().getNombre() + " se esta defendiendo" + "\n");
    	batalla.avanzarTurno();
        jugarTurnosEnemigos();
        refrescarVista();
    }
    
    public void usarHabilidad() {
    	
    	List<Habilidad> habilidades = batalla.turnoActual().getHabilidad();
    	if (habilidades.isEmpty()) {
    		return;
    	}
    	
    	Object seleccion = JOptionPane.showInputDialog(
                vista,
                "¿Qué habilidad usás?",
                "Elegir habilidad",
                JOptionPane.QUESTION_MESSAGE,
                null,
                habilidades.toArray(),
                habilidades.get(0)
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
            	Object elegido = JOptionPane.showInputDialog(
            	        vista,
            	        "¿Sobre quién?",
            	        "Elegir objetivo",
            	        JOptionPane.QUESTION_MESSAGE,
            	        null,
            	        vivos.toArray(),
            	        vivos.get(0)
            	    );
            	if (elegido == null) {
            	        return;
                }
           	    objetivo = (Entidad) elegido;
           	    break;
           	    
       		case ALIADO:
       			candidatos.addAll(party.integrantesVivos());
       			Object personaje = JOptionPane.showInputDialog(
       			        vista,
       			        "¿Sobre quién?",
       			        "Elegir objetivo",
       			        JOptionPane.QUESTION_MESSAGE,
       			        null,
       			     party.integrantesVivos().toArray(),
       			     party.integrantesVivos().get(0)
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
    	int danio = batalla.ejecutarAccion(new UsarHabilidad(habilidad, objetivos), objetivo);
    	if (habilidad.getTipoObjetivo() == TipoObjetivo.ALIADO || habilidad.getTipoObjetivo() == TipoObjetivo.UNO_MISMO) {
    	    mensaje = "El " + batalla.turnoActual().getNombre() + " uso " + habilidad.getNombre()
    	            + " sobre " + objetivo.getNombre() + " regenerandole " + danio + "Hp. Vida restante: " + objetivo.getVidaActual() + "Hp\n";
    	} else {
    	    mensaje = "El " + batalla.turnoActual().getNombre() + " uso " + habilidad.getNombre()
    	            + " sobre " + objetivo.getNombre() + " realizandole " + danio + "Hp de daño. Vida restante: "
    	            + objetivo.getVidaActual() + "Hp\n";
    	}
        batalla.avanzarTurno();
        jugarTurnosEnemigos();
        refrescarVista();
    }

    public void usarItem() {

        // TODO: mostrar los consumibles del inventario, construir una
        // UsarItem con el elegido y ejecutarla
        refrescarVista();
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
            1,
            personajes,
            enemigos,
            batalla.turnoActual().getNombre(),
            mensaje,
            batalla.batallaTermino(),
            batalla.ganadorDeBatalla()
        );
    }
     private EntidadDTO convertirEntidad(Entidad entidad, String sprite) {
          List<String> estados = new ArrayList<>();
          // recorrer los efectos de estado activos y agregar sus nombres
     
          return new EntidadDTO(
              entidad.getNombre(),
              entidad.getNivel(),
              entidad.getVidaActual(),
              entidad.getVidaMaxima(),
              entidad.getManaActual(),
              entidad.getManaMaximo(),
             entidad.getAtaque(),
              entidad.getDefensa(),
              entidad.getVelocidad(),
              estados,
              sprite
         );
      }
}
