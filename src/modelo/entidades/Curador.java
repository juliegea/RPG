package modelo.entidades;

public class Curador extends Personaje {
	private int sabiduria;

	public Curador(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad) {
		super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad);
		this.sabiduria = 2;
	}

	@Override
	protected void aplicarCrecimiento() {
		vidaMaxima = vidaMaxima + (vidaMaxima * 15 / 100);
		ataque = ataque + (ataque * 15 / 100);
		defensa = defensa + (defensa * 15 / 100);
		velocidad = velocidad + (velocidad * 15 / 100);
		if (sabiduria < 20) {
			sabiduria = sabiduria + 2;
		}
	}
	
	public int getSabiduria() {
		return sabiduria;
	}
	
	@Override
	public int multiplicarCuracion() {
		int probabilidad = (int) (Math.random() * 100);
		if (probabilidad < sabiduria) {
			return 2;
		}
		return 1;
	}

}
