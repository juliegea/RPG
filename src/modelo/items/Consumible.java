package modelo.items;

import java.util.List;

import modelo.entidades.Personaje;
import modelo.habilidades.Efecto;

public class Consumible extends Item {
	private Efecto efecto;
	

	public Consumible(String nombre, int precio, Efecto efecto) {
		super(nombre, precio);
		this.efecto = efecto;
	}

	@Override
	public int usarItem(Personaje personaje) {
		return efecto.aplicarEfecto(personaje, List.of(personaje));
	}
	
	@Override
	public boolean esConsumible() {
		return true;
	}

}
