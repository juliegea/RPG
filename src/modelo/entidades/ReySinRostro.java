package modelo.entidades;

import java.util.List;
import modelo.acciones.Accion;
import modelo.acciones.Atacar;

public class ReySinRostro extends Enemigo {
    private boolean escudoActivo;

    public ReySinRostro(String nombre, int nivel, int vidaMaxima, int manaMaximo,
            int ataque, int defensa, int velocidad, int oroOtorgado, int expOtorgada) {
        super(nombre, nivel, vidaMaxima, manaMaximo, ataque, defensa, velocidad, oroOtorgado, expOtorgada);
        this.escudoActivo = false;
    }

    @Override
    public Accion decidirAccion(List<Personaje> personajes, List<Enemigo> enemigos) {
        return new Atacar();
    }

    @Override
    public Entidad elegirObjetivo(List<Personaje> personajes, List<Enemigo> enemigos) {
        Personaje elegido = null;
        for (Personaje p : personajes) {
            if (p.estaViva()) {
                if (elegido == null || p.getAtaque() > elegido.getAtaque()) {
                    elegido = p;
                }
            }
        }
        return elegido;
    }

    @Override
    public int recibirAtaque(int poderAtaque) {
        if (!escudoActivo && getVidaActual() <= getVidaMaxima() / 4) {
            escudoActivo = true;
        }
        if (escudoActivo) {
            poderAtaque = poderAtaque / 2;
        }
        return super.recibirAtaque(poderAtaque);
    }
}