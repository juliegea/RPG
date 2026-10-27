package modelo.habilidades;

import java.util.List;

import modelo.entidades.Entidad;

public class Danio extends Efecto {

	public Danio(int potencia) {
		super(potencia);
	}

	@Override
	public int aplicarEfecto(Entidad origen, List<Entidad> objetivos) {
		int potenciaFinal = getPotencia() + (getPotencia() * origen.bonusHabilidad() / 100);
		int total = 0;
		for (Entidad o: objetivos) {
			if (o.estaViva()) {
				total += o.recibirAtaque(potenciaFinal);
			}
		}
		return total;
	}

}
