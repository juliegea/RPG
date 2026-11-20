package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.entidades.Personaje;
import modelo.items.Consumible;

public class UsarItem extends Accion {
    private Consumible consumible;

    public UsarItem(Consumible consumible) {
        this.consumible = consumible;
    }

    @Override
    public int aplicarAccion(Entidad ejecutor, Entidad objetivo) {
        if (ejecutor instanceof Personaje) {
            return consumible.usarItem((Personaje) ejecutor);
        }
        return 0;
    }

    @Override
    public String getNombre() {
        return "Item";
    }
}