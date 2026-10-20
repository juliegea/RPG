package modelo.acciones;

import modelo.entidades.Entidad;

public class Defender extends Accion {

	public Defender() {
	}

	@Override
	public int aplicarAccion(Entidad ejecutor, Entidad objetivo) {
		ejecutor.defender();
		return 0;
	}

	@Override
	public String getNombre() {
		return "Defender" ;
	}

}
