package modelo.combate;

import java.util.ArrayList;
import java.util.List;
import modelo.items.Item;

public class Mercado {
    private final List<Item> catalogo;

    public Mercado() {
        this.catalogo = new ArrayList<>();
    }

    public void agregarAlCatalogo(Item item) {
        catalogo.add(item);
    }

    public List<Item> verCatalogo() {
        return catalogo;
    }

    public boolean comprarItem(Item item, Party party) {
        if (party.puedoPagar(item.getPrecio())) {
            party.restarOro(item.getPrecio());
            party.getInventario().agregarItem(item);
            return true;
        }
        return false;
    }

    public void venderItem(Item item, Party party) {
        party.sumarOro(item.precioDeVenta());
        party.getInventario().quitarItem(item);
    }
}
