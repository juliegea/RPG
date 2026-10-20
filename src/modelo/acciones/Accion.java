package modelo.acciones;

import modelo.entidades.Entidad;

public abstract class Accion {
	
	public abstract int aplicarAccion(Entidad ejecutor, Entidad objetivo);
	
	public abstract String getNombre();

}
