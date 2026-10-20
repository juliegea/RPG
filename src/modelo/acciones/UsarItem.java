package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.items.Consumible;

public class UsarItem extends Accion {
	private Consumible consumible;

	public UsarItem(Consumible consumible) {
		this.consumible = consumible;
	}

	@Override
	public int aplicarAccion(Entidad ejecutor, Entidad objetivo) {
		return 0;
	}

	@Override
	public String getNombre() {
		return "Item";
	}

}
