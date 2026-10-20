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
	
	public void usarObjeto(Item objeto) {
		
	}

	public void desequiparObjeto(Item objeto) {
		
	}
	
	private void subirNivel() {
		nivel++;
		aplicarCrecimiento();
		
		
	}
	
	protected abstract void aplicarCrecimiento();
	
	public int getExperiencia() {
		return experiencia;
	}
	
}
