package controlador;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.EntidadDTO;
import dto.EstadoBatallaDTO;
import modelo.acciones.Accion;
import modelo.acciones.Atacar;
import modelo.acciones.Defender;
import modelo.combate.Batalla;
import modelo.combate.Party;
import modelo.entidades.Enemigo;
import modelo.entidades.Entidad;
import modelo.entidades.Personaje;
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
        int danio = batalla.ejecutarAccion(new Atacar(), (Entidad) objetivo);
        mensaje = ("El " + batalla.turnoActual().getNombre() + " Ataco a " + objetivo.getNombre() + " realizandole un daño de " + danio +"Hp. " + "Vida restante de " + objetivo.getNombre() +": " + objetivo.getVidaActual() + "Hp" + "\n");
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
            mensaje += ("El " + batalla.turnoActual().getNombre() + " Ataco a " + objetivo.getNombre() + " realizandole un daño de " + danio +"Hp. " + "Vida restante de " + objetivo.getNombre() +": " + objetivo.getVidaActual() + "Hp" + "\n");
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

        // TODO: pedir a la vista que habilidad y que objetivo eligio el
        // jugador, construir una UsarHabilidad y ejecutarla
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
