package modelo.habilidades;

import modelo.entidades.Entidad;

public abstract class EfectoDeEstado {
	private String nombre;
	private int potencia;
	private int turnosRestantes;
	public EfectoDeEstado(String nombre, int potencia, int turnosRestantes) {
		this.nombre = nombre;
		this.potencia = potencia;
		this.turnosRestantes = turnosRestantes;
	}
	
	public abstract int aplicarEfectoEstado(Entidad objetivo);
	
	public abstract boolean esNegativo();
	
	public String getNombre() {
		return nombre;
	}

	public int getPotencia() {
		return potencia;
	}

	public int getTurnosRestantes() {
		return turnosRestantes;
	}
	
	public void descontarTurnos() {
		if (turnosRestantes != -1) {
			turnosRestantes -= 1;
		}
	}

	public boolean expiroEfecto() {
		if (turnosRestantes == 0) {
			return true;
		}
		return false;
	}
	
	public boolean bloqueaDanio() {
		return false;
	}
}
