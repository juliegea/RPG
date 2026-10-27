package modelo.habilidades;

import modelo.entidades.Entidad;

public class DanioPorTurno extends EfectoDeEstado {

	public DanioPorTurno(String nombre, int potencia, int turnosRestantes) {
		super (nombre, potencia, turnosRestantes);
	}

	@Override
	public int aplicarEfectoEstado(Entidad objetivo) {
		int danio = objetivo.getVidaMaxima() * getPotencia()/100;
		return objetivo.recibirDanio(danio);
	}

	@Override
	public boolean esNegativo() {
		return true;
	}

}
