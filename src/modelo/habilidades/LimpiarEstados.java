package modelo.habilidades;

import java.util.List;

import modelo.entidades.Entidad;

public class LimpiarEstados extends Efecto {

	public LimpiarEstados() {
		super(0);
	}

	@Override
	public int aplicarEfecto(Entidad origen, List<Entidad> objetivos) {
		for (Entidad o: objetivos) {
				o.limpiarEstadosNegativos();
		}
		return 0;
	}

}
