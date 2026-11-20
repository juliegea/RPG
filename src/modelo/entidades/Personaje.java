package modelo.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.items.Equipable;

public abstract class Personaje extends Entidad {
	
	private int experiencia;
	
	private final List<Equipable> equipo;

	public Personaje(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad);
		this.experiencia = 0;
		this.equipo = new ArrayList<>();
	}
	
	public void ganarExperiencia(int cantidad) {
		experiencia  += cantidad;
		while (experiencia >= 50 * nivel) {
			experiencia -= 50 * nivel;
			subirNivel();
		}
		
	}
	
	public void usarObjeto(Equipable nuevo) {
	    for (int i = 0; i < equipo.size(); i++) {
	        if (equipo.get(i).getParteDelCuerpo() == nuevo.getParteDelCuerpo()) {
	            equipo.remove(i);
	            break;
	        }
	    }
	    equipo.add(nuevo);
	}

	public void desequiparObjeto(Equipable equipo) {
	    this.equipo.remove(equipo);
	}

	public List<Equipable> getEquipo() {
		return Collections.unmodifiableList(equipo);
	}
	
	private void subirNivel() {
		nivel++;
		aplicarCrecimiento();
		
		
	}
	
	protected abstract void aplicarCrecimiento();
	
	public int getExperiencia() {
		return experiencia;
	}
	
	@Override
	protected int ataqueEfectivo() {
	    int total = ataque;
	    for (Equipable e : equipo) {
	        total += e.getBonoAtaque();
	    }
	    return total;
	}

	@Override
	protected int defensaEfectiva() {
	    int total = defensa;
	    for (Equipable e : equipo) {
	        total += e.getBonoDefensa();
	    }
	    if (defendiendo) {
	        total = total * 3 / 2;
	    }
	    return total;
	}
	
	@Override
	public int getVelocidad() {
	    int total = velocidad;
	    for (Equipable e : equipo) {
	        total += e.getBonoVelocidad();
	    }
	    return total;
	}

	@Override
	public int getVidaMaxima() {
	    int total = vidaMaxima;
	    for (Equipable e : equipo) {
	        total += e.getBonoVida();
	    }
	    return total;
	}
}
