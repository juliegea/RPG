package modelo.habilidades;

import java.util.List;

import modelo.entidades.Entidad;

public class Curacion extends Efecto {

	public Curacion(int potencia) {
		super(potencia);
	}

	@Override
	public int aplicarEfecto(Entidad origen, List<Entidad> objetivos) {
		int potenciaFinal = (getPotencia() * origen.multiplicarCuracion());
		int total = 0;
		for (Entidad o: objetivos) {
			if (o.estaViva()) {
				o.curar(potenciaFinal);
				total += potenciaFinal;
			}
		}
		return total;
	}

}
