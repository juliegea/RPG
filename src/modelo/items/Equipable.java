package modelo.items;

import modelo.entidades.Personaje;

public class Equipable extends Item {
	private ParteDelCuerpo parteDelCuerpo;
	private int bonoVida;
	private int bonoAtaque;
	private int bonoDefensa;
	private int bonoVelocidad;
	

	public Equipable(String nombre, int precio, int bonoVida, int bonoAtaque, int bonoDefensa, int bonoVelocidad, ParteDelCuerpo parteDelCuerpo) {
		super(nombre, precio);
		this.bonoVida = bonoVida;
		this.bonoAtaque = bonoAtaque;
		this.bonoDefensa = bonoDefensa;
		this.bonoVelocidad = bonoVelocidad;
		this.parteDelCuerpo = parteDelCuerpo;
	}

	@Override
	public int usarItem(Personaje personaje) {
		return 0;
	}
	
	public ParteDelCuerpo getParteDelCuerpo() {
		return parteDelCuerpo;
	}
	
	public int getBonoVida() {
		return bonoVida;
	}

	public int getBonoAtaque() {
		return bonoAtaque;
	}

	public int getBonoDefensa() {
		return bonoDefensa;
	}

	public int getBonoVelocidad() {
		return bonoVelocidad;
	}
	
	@Override
	public boolean esConsumible() {
		return false;
	}
}
