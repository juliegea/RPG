package modelo.items;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
	private List<Item> items;

	public Inventario(List<Item> item) {
		this.items = item;
	}
	
	public void agregarItem(Item item) {
		items.add(item);
	}
	
	public void quitarItem(Item item) {
		items.remove(item);
	}
	
	public List<Item> verInventario() {
		return items;
	}
	
	public List<Consumible> filtrarConsumible() {
		List<Consumible> consumibles = new ArrayList<>();
		for (Item c: items) {
			if (c.esConsumible()) {
				consumibles.add((Consumible) c);
			}
		}
		return consumibles;
	}
}
