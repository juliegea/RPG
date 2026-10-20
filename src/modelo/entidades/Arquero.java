package modelo.entidades;

public class Arquero extends Personaje {
	private int destreza;

	public Arquero(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad);
		this.destreza = 2;
		
	}

	@Override
	protected void aplicarCrecimiento() {
		vidaMaxima = vidaMaxima + (vidaMaxima * 15 / 100);
		ataque = ataque + (ataque * 15 / 100);
		defensa = defensa + (defensa * 15 / 100);
		velocidad = velocidad + (velocidad * 15 / 100);
		if (destreza < 26) {
			destreza = destreza + 2;
		}
	}
	
	public int getDestreza() {
		return destreza;
	}
	
	@Override
	public boolean atacaDosVeces() {
		int probabilidad = (int) (Math.random() * 100);
		return probabilidad <  destreza;
	}

}
