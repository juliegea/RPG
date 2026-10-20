package modelo.entidades;

import java.util.List;
import modelo.acciones.Atacar;

import modelo.acciones.Accion;

public class Hechicera extends Enemigo {

	public Hechicera(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad,
		int oroOtorgado, int expOtorgada) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad, oroOtorgado, expOtorgada);
	}

	@Override
	public Accion decidirAccion(List<Personaje> personajes, List<Enemigo> enemigos) {
		return new Atacar();
	}
	
	@Override
	public Entidad elegirObjetivo(List<Personaje> personajes, List<Enemigo> enemigos) {
		Enemigo elegido = null;
		for (Enemigo e : enemigos) {
			if (e.estaViva() && (elegido == null || e.getVidaActual() * elegido.getVidaMaxima()  <  elegido.getVidaActual() * e.getVidaMaxima())) {
			    elegido = e;
			}
		}
		return elegido;
	}
}
