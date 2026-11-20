package modelo.entidades;

import java.util.List;
import modelo.acciones.Atacar;
import modelo.acciones.UsarHabilidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.Accion;

public class Hechicera extends Enemigo {

	public Hechicera(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad,
		int oroOtorgado, int expOtorgada) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad, oroOtorgado, expOtorgada);
	}
	
	@Override
	public Accion decidirAccion(List<Personaje> personajes, List<Enemigo> enemigos) {
	    if (getHabilidad().isEmpty()) {
	        return new Atacar();
	    }
	    Habilidad hab = getHabilidad().get(0);
	    if (getManaActual() >= hab.getCostoMana()) {
	        return new UsarHabilidad(hab, List.of(elegirObjetivo(personajes, enemigos)));
	    }
	    return new Atacar();
	}
	
	@Override
	public Entidad elegirObjetivo(List<Personaje> personajes, List<Enemigo> enemigos) {
	    Habilidad hab = getHabilidad().isEmpty() ? null : getHabilidad().get(0);
	    if (hab != null && getManaActual() >= hab.getCostoMana()) {
	        // busca aliado más herido
	        Enemigo elegido = null;
	        for (Enemigo e : enemigos) {
	            if (e.estaViva()) {
	                if (elegido == null || e.getVidaActual() * elegido.getVidaMaxima() 
	                        < elegido.getVidaActual() * e.getVidaMaxima()) {
	                    elegido = e;
	                }
	            }
	        }
	        return elegido;
	    } else {
	        // busca personaje más herido
	        Personaje elegido = null;
	        for (Personaje p : personajes) {
	            if (p.estaViva()) {
	                if (elegido == null || p.getVidaActual() < elegido.getVidaActual()) {
	                    elegido = p;
	                }
	            }
	        }
	        return elegido;
	    }
	}
}
