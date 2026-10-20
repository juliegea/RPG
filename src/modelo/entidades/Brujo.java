package modelo.entidades;

import java.util.List;
import modelo.acciones.Atacar;

import modelo.acciones.Accion;

public class Brujo extends Enemigo {

	public Brujo(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad,
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
			if (p.estaViva() && (elegido == null || p.getManaActual() > elegido.getManaActual())) {
			    elegido = p;
			}
		}
		return elegido;
	}
}
