package dto;

import java.util.List;

/**
 * Representa a una entidad tal como la ve la interfaz.
 *
 * Es un objeto plano: no tiene logica y todos sus campos son finales.
 * La vista nunca recibe objetos del modelo, solo DTOs, de modo que no
 * puede invocar metodos que modifiquen el estado del juego.
 */
public class EntidadDTO {

    private final String nombre;
    private final int nivel;

    private final int vidaActual;
    private final int vidaMaxima;

    private final int manaActual;
    private final int manaMaximo;

    private final int ataque;
    private final int defensa;
    private final int velocidad;

    private final List<String> estados;

    private final String sprite;
    
    private final int bonoAtaque;
    private final int bonoDefensa;
    private final int bonoVelocidad;

    public EntidadDTO(
            String nombre,
            int nivel,
            int vidaActual,
            int vidaMaxima,
            int manaActual,
            int manaMaximo,
            int ataque,
            int defensa,
            int velocidad,
            List<String> estados,
            String sprite, 
    		int bonoAtaque, 
    		int bonoDefensa, 
    		int bonoVelocidad) {

        this.nombre = nombre;
        this.nivel = nivel;

        this.vidaActual = vidaActual;
        this.vidaMaxima = vidaMaxima;

        this.manaActual = manaActual;
        this.manaMaximo = manaMaximo;

        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;

        this.estados = estados;

        this.sprite = sprite;
        
        this.bonoAtaque = bonoAtaque;
        
        this.bonoDefensa = bonoDefensa;
        
        this.bonoVelocidad = bonoVelocidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getManaActual() {
        return manaActual;
    }

    public int getManaMaximo() {
        return manaMaximo;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public List<String> getEstados() {
        return estados;
    }

    public String getSprite() {
        return sprite;
    }
    
    public int getBonoAtaque() { return bonoAtaque; }
    public int getBonoDefensa() { return bonoDefensa; }
    public int getBonoVelocidad() { return bonoVelocidad; }
}
