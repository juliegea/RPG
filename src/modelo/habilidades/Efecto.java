package modelo.habilidades;

import java.util.List;
import modelo.entidades.Entidad;

public abstract class Efecto {
	private int potencia;
	
	public Efecto(int potencia) {
		this.potencia = potencia;
	}
	
	public int getPotencia() {
		return potencia;
	}
	
	public abstract int aplicarEfecto(Entidad origen, List<Entidad> objetivos);
	
	
}
