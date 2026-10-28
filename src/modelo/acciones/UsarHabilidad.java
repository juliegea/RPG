package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import java.util.List;

public class UsarHabilidad extends Accion {
	private Habilidad habilidad;
	private List<Entidad> objetivos;

	public UsarHabilidad(Habilidad habilidad, List<Entidad> objetivos) {
		this.habilidad = habilidad;
		this.objetivos = objetivos;
	}

	@Override
	public int aplicarAccion(Entidad ejecutor, Entidad objetivo) {
		int danio = ejecutor.usarHabilidad(habilidad, objetivos);
		return danio;
	}

	@Override
	public String getNombre() {
		return "Habilidad";
	}

}
