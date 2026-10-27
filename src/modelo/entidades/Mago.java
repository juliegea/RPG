package modelo.entidades;

public class Mago extends Personaje {
	private int inteligencia;

	public Mago(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad);
		this.inteligencia = 10;
	}

	@Override
	protected void aplicarCrecimiento() {
		vidaMaxima = vidaMaxima + (vidaMaxima * 15 / 100);
		ataque = ataque + (ataque * 15 / 100);
		defensa = defensa + (defensa * 15 / 100);
		velocidad = velocidad + (velocidad * 15 / 100);
		if (inteligencia < 50) {
			inteligencia = inteligencia + 2;
		}
	}
	
	public int getInteligencia() {
		return inteligencia;
	}
	
	@Override
	public int bonusHabilidad() {
	    return inteligencia;
	}
}
