package modelo.habilidades;

import java.util.List;

import modelo.entidades.Entidad;

public class Curacion extends Efecto {

	public Curacion(int potencia) {
		super(potencia);
	}

	@Override
	public int aplicarEfecto(Entidad origen, List<Entidad> objetivos) {
		int total = 0;
		for (Entidad o: objetivos) {
			int potenciaFinal = ((o.getVidaMaxima() * getPotencia()/100) * origen.multiplicarCuracion());
			if (o.estaViva()) {
				int vidaAntes = o.getVidaActual();
				o.curar(potenciaFinal);
				total += o.getVidaActual() - vidaAntes;
			}
		}
		return total;
	}

}
