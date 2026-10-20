package dto;

import java.util.List;

/**
 * Representa el estado completo de una batalla tal como lo ve la interfaz.
 *
 * A diferencia del ejemplo de clase, que enfrentaba a un jugador contra un
 * enemigo, aca hay una lista de personajes y una lista de enemigos, porque
 * la party tiene hasta 4 integrantes y cada batalla tiene varios enemigos.
 */
public class EstadoBatallaDTO {

    private final int numeroBatalla;

    private final List<EntidadDTO> personajes;
    private final List<EntidadDTO> enemigos;

    private final String nombreEnTurno;

    private final String mensaje;

    private final boolean batallaTerminada;
    private final boolean gano;

    public EstadoBatallaDTO(
            int numeroBatalla,
            List<EntidadDTO> personajes,
            List<EntidadDTO> enemigos,
            String nombreEnTurno,
            String mensaje,
            boolean batallaTerminada,
            boolean gano) {

        this.numeroBatalla = numeroBatalla;
        this.personajes = personajes;
        this.enemigos = enemigos;
        this.nombreEnTurno = nombreEnTurno;
        this.mensaje = mensaje;
        this.batallaTerminada = batallaTerminada;
        this.gano = gano;
    }

    public int getNumeroBatalla() {
        return numeroBatalla;
    }

    public List<EntidadDTO> getPersonajes() {
        return personajes;
    }

    public List<EntidadDTO> getEnemigos() {
        return enemigos;
    }

    public String getNombreEnTurno() {
        return nombreEnTurno;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isBatallaTerminada() {
        return batallaTerminada;
    }

    public boolean isGano() {
        return gano;
    }
}
