package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import java.util.List;

public class UsarHabilidad extends Accion {
	private Habilidad habilidad;

	public UsarHabilidad(Habilidad habilidad) {
		this.habilidad = habilidad;
	}

	@Override
	public int aplicarAccion(Entidad ejecutor, Entidad objetivo) {
		int danio = ejecutor.usarHabilidad(habilidad, List.of(objetivo));
		return danio;
	}

	@Override
	public String getNombre() {
		return "Habilidad";
	}

}
