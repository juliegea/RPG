package modelo.entidades;

import java.util.List;
import modelo.acciones.Atacar;

import modelo.acciones.Accion;

public class Demonio extends Enemigo {

	public Demonio(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad,
		int oroOtorgado, int expOtorgada) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad, oroOtorgado, expOtorgada);
	}

	@Override
	public Accion decidirAccion(List<Personaje> personajes, List<Enemigo> enemigos) {
		return new Atacar();
	}
	
	@Override
	public Entidad elegirObjetivo(List<Personaje> personajes, List<Enemigo> enemigos) {
		Personaje elegido = null;
		for (Personaje p : personajes) {
			if (p.estaViva() && (elegido == null || p.getVidaActual() < elegido.getVidaActual())) {
			    elegido = p;
			}
		}
		return elegido;
	}
}
