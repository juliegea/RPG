package modelo.items;

import modelo.entidades.Personaje;

public abstract class Item {
	private String nombre;
	private int precio;
	
	public Item(String nombre, int precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public abstract int usarItem(Personaje personaje);
	
	public int getPrecio() {
		return precio;
	}
	
	public int precioDeVenta() {
		return precio * 75/100;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public abstract boolean esConsumible();
	
	@Override
	public String toString() {
	    return nombre + " (" + precio + " oro)";
	}
}
