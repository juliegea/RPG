package modelo.entidades;

import java.util.List;
import modelo.acciones.Atacar;
import modelo.acciones.UsarHabilidad;
import modelo.acciones.Accion;

public class Hechicera extends Enemigo {

	public Hechicera(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad,
		int oroOtorgado, int expOtorgada) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad, oroOtorgado, expOtorgada);
	}
	
	// La Hechicera cura siempre al aliado mas herido.
	//
	// Por ahora no evalua la situacion: si se queda sin mana, usarHabilidad()
	// devuelve 0 y pierde el turno sin hacer nada. Lo correcto seria que
	// decidirAccion() verifique el mana disponible y si no alcanza devuelva
	// un Atacar, y que ademas no cure cuando todos los aliados estan al maximo.
	//
	// Eso exige que decidirAccion() y elegirObjetivo() lleguen a la misma
	// conclusion por separado, porque son dos metodos independientes.
	// La solucion seria que la Accion lleve su propio objetivo,
	// de modo que el enemigo decida ambas cosas de una sola vez.
	@Override
	public Accion decidirAccion(List<Personaje> personajes, List<Enemigo> enemigos) {
		if (getHabilidad().isEmpty()) {
		    return new Atacar();
		}
		return new UsarHabilidad(getHabilidad().get(0), List.of(elegirObjetivo(personajes, enemigos)));
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
