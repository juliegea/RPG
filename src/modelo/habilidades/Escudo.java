package modelo.habilidades;

import modelo.entidades.Entidad;

public class Escudo extends EfectoDeEstado {

	public Escudo(int potencia, int turnosRestantes) {
		super("Escudo", potencia, turnosRestantes);
	}

	@Override
	public int aplicarEfectoEstado(Entidad objetivo) {
		return 0;
	}

	@Override
	public boolean esNegativo() {
		return false;
	}
	
	@Override
	public boolean bloqueaDanio() {
		return true;
	}

}
