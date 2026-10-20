package modelo.entidades;

public class Guerrero extends Personaje {
	private int fuerza;

	public Guerrero(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad);
		this.fuerza = 2;
	}

	@Override
	protected void aplicarCrecimiento() {
		vidaMaxima = vidaMaxima + (vidaMaxima * 15 / 100);
		ataque = ataque + (ataque * 15 / 100);
		defensa = defensa + (defensa * 15 / 100);
		velocidad = velocidad + (velocidad * 15 / 100);
		if (fuerza < 30) {
			fuerza = fuerza + 2;
		}
	}
	
	public int getFuerza() {
		return fuerza;
	}
	
	@Override
	public int calcularDanioBase() {
		int probabilidad = (int) (Math.random() * 100);
		if (probabilidad < fuerza) {
			return ataqueEfectivo() * 2;
		}
		return ataqueEfectivo() ;
	}
}
