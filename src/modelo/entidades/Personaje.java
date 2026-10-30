package modelo.entidades;

import java.util.ArrayList;
import java.util.List;

import modelo.items.Equipable;
import modelo.items.Item;

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
	
	public void usarObjeto(Equipable equipo) {
	    this.equipo.add(equipo);
	}

	public void desequiparObjeto(Equipable equipo) {
	    this.equipo.remove(equipo);
	}

	public List<Equipable> getEquipo() {
	    return equipo;
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
	
}
