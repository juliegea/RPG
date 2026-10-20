package modelo.entidades;

import java.util.List;
import modelo.acciones.Accion;

public abstract class Enemigo extends Entidad {
	private int expOtorgada;
	private int oroOtorgado;

	public Enemigo(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad, int oroOtorgado, int expOtorgada) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad);
		this.expOtorgada = expOtorgada;
		this.oroOtorgado = oroOtorgado;	
	}
	
	public int recompensaExperiencia() {
		return expOtorgada;
	}
	
	public int recompensaOro() {
		return oroOtorgado;
	}
	
	public abstract Accion decidirAccion(List<Personaje> personajes, List<Enemigo> enemigos);
	
	public abstract Entidad elegirObjetivo(List<Personaje> personajes, List<Enemigo> enemigos);
}
