package modelo.acciones;

import modelo.entidades.Entidad;

public class Atacar extends Accion {

	public Atacar() {
		
	}

	@Override
	public int aplicarAccion(Entidad ejecutor, Entidad objetivo) {
		int danio = objetivo.recibirAtaque(ejecutor.calcularDanioBase());
		if (ejecutor.atacaDosVeces() && objetivo.estaViva()) {
			danio += objetivo.recibirAtaque(ejecutor.calcularDanioBase());
		}
		return danio;
		
	}

	@Override
	public String getNombre() {
		return "Atacar";
	}

}
