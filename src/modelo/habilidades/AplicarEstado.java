package modelo.habilidades;

import java.util.List;

import modelo.entidades.Entidad;

public class AplicarEstado extends Efecto {
	private EfectoDeEstado efectoDeEstado;

	public AplicarEstado(EfectoDeEstado efectoDeEstado) {
		super(0);
		this.efectoDeEstado = efectoDeEstado;
	}

	@Override
	public int aplicarEfecto(Entidad origen, List<Entidad> objetivos) {
		for (Entidad o: objetivos) {
				o.aplicarEstado(efectoDeEstado);
		}
		return 0;
	}

}