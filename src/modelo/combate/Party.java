package modelo.combate;

import java.util.ArrayList;
import java.util.List;

import modelo.entidades.Enemigo;
import modelo.entidades.Personaje;
import modelo.items.Inventario;

public class Party {
	private final List<Personaje> personajes;
	private Inventario inventario;
	private int oroTotal;
	
	public Party(List<Personaje> personajes) {
		this.personajes = personajes;
		this.oroTotal = 0;
		this.inventario = new Inventario(new ArrayList<>());
	}
	
	public boolean partyDerrotada() {
		return integrantesVivos().isEmpty();
	}
	
	public List<Personaje> integrantesVivos() {
		List<Personaje> vivos = new ArrayList<>();
		for (Personaje vivo : personajes) {
			if (vivo.estaViva()) {
				vivos.add(vivo);
			}
		}
		return vivos;
	}
	
	public int getOro() {
		return oroTotal;
	}
	
	public void sumarOro(int cantidad) {
		oroTotal += cantidad;
	}
	
	public void restarOro(int cantidad) {
		oroTotal -= cantidad;
	}
	
	public void setOro(int cantidad) {
		oroTotal = cantidad;
	}
	
	public boolean puedoPagar(int cantidad) {
		return oroTotal >= cantidad;
	}
	
	public Inventario getInventario() {
		return inventario;
	}
	
	public List<Personaje> getIntegrantes() {
		return personajes;
	}

}
