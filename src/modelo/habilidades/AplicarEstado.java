package modelo.habilidades;

import java.util.List;

import modelo.entidades.Entidad;

public class AplicarEstado extends Efecto {
	private EfectoDeEstado efectoDeEstado;

	public AplicarEstado(int potencia) {
		super(potencia);
	}

	@Override
	public int aplicarEfecto(Entidad origen, List<Entidad> objetivos) {
		return 0;
	}

}
