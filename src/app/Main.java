package app;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import controlador.ControladorBatalla;
import modelo.combate.*;
import modelo.entidades.*;
import modelo.habilidades.*;
import modelo.items.*;
import vista.*;
import modelo.combate.GestorDeGuardado;
import modelo.combate.Partida;

public class Main {
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> {
	        GestorDeGuardado gestor = new GestorDeGuardado();
	        PantallaInicio inicio = new PantallaInicio(gestor.existeGuardado());
	        inicio.conectarNueva(() -> iniciarJuego(null));
	        inicio.conectarCargar(() -> iniciarJuego(gestor.cargar()));
	        inicio.setVisible(true);
	    });
	}

	private static void iniciarJuego(Partida partidaCargada) {
	    Guerrero guerrero = new Guerrero("Guerrero", 1, 450, 120, 65, 60, 30);
	    Mago mago = new Mago("Mago", 1, 280, 320, 40, 35, 40);
	    Arquero arquero = new Arquero("Arquero", 1, 320, 180, 55, 40, 60);
	    Curador curador = new Curador("Curador", 1, 300, 280, 35, 40, 45);

	    List<Personaje> integrantes = new ArrayList<>();
	    integrantes.add(curador);
	    integrantes.add(arquero);
	    integrantes.add(mago);
	    integrantes.add(guerrero);

	    Party party = new Party(integrantes);

	    guerrero.agregarHabilidad(new Habilidad("Golpe contra el piso", 30, 3, TipoObjetivo.ENEMIGO, new Danio(45)));
	    mago.agregarHabilidad(new Habilidad("Descarga Arcana", 40, 1, TipoObjetivo.ENEMIGO, new Danio(80)));
	    mago.agregarHabilidad(new Habilidad("Agujero Negro", 10, 4, TipoObjetivo.ENEMIGO, new Danio(5000)));
	    arquero.agregarHabilidad(new Habilidad("Flecha de acero", 30, 1, TipoObjetivo.ENEMIGO, new Danio(70)));
	    arquero.agregarHabilidad(new Habilidad("Cacería", 70, 3, TipoObjetivo.ENEMIGO, new Danio(60)));
	    arquero.agregarHabilidad(new Habilidad("Flecha de Serpiente", 50, 1, TipoObjetivo.ENEMIGO,
	            new AplicarEstado(new DanioPorTurno("Veneno", 5, 4))));
	    arquero.agregarHabilidad(new Habilidad("Flecha Incendiaria", 35, 1, TipoObjetivo.ENEMIGO,
	            new AplicarEstado(new DanioPorTurno("Quemadura", 7, 4))));
	    curador.agregarHabilidad(new Habilidad("Luz Sanadora", 35, 1, TipoObjetivo.ALIADO, new Curacion(15)));
	    curador.agregarHabilidad(new Habilidad("Escudo Sagrado", 80, 1, TipoObjetivo.ALIADO,
	            new AplicarEstado(new Escudo(0, 1))));
	    curador.agregarHabilidad(new Habilidad("Purificación", 70, 1, TipoObjetivo.ALIADO, new LimpiarEstados()));

	    party.getInventario().agregarItem(new Consumible("Poción débil", 50, new Curacion(15)));
	    party.getInventario().agregarItem(new Consumible("Poción media", 100, new Curacion(25)));
	    party.getInventario().agregarItem(new Consumible("Poción fuerte", 200, new Curacion(50)));

	    Mercado mercado = new Mercado();
	    mercado.agregarAlCatalogo(new Consumible("Poción débil", 50, new Curacion(15)));
	    mercado.agregarAlCatalogo(new Consumible("Poción media", 100, new Curacion(25)));
	    mercado.agregarAlCatalogo(new Consumible("Poción fuerte", 200, new Curacion(50)));
	    mercado.agregarAlCatalogo(new Equipable("Espada de hierro", 200, 0, 15, 0, 0, ParteDelCuerpo.ARMA));
	    mercado.agregarAlCatalogo(new Equipable("Armadura de cuero", 180, 0, 0, 10, 5, ParteDelCuerpo.ARMADURA));
	    mercado.agregarAlCatalogo(new Equipable("Casco de acero", 150, 20, 0, 5, 0, ParteDelCuerpo.CASCO));
	    mercado.agregarAlCatalogo(new Equipable("Botas de cuero", 120, 10, 0, 0, 12, ParteDelCuerpo.BOTAS));
	    mercado.agregarAlCatalogo(new Equipable("Anillo arcano", 250, 0, 8, 0, 8, ParteDelCuerpo.ACCESORIO));
	    mercado.agregarAlCatalogo(new Equipable("Escudo de batalla", 220, 0, 0, 20, 0, ParteDelCuerpo.ESCUDO));

	    GeneradorDeBatalla generador = new GeneradorDeBatalla();
	    int numBatalla = partidaCargada != null ? Math.min(partidaCargada.getNumeroBatalla(), 5) : 1;
	    List<Enemigo> enemigos = generador.generarEnemigos(numBatalla);
	    Batalla batalla = new Batalla(party, enemigos);
	    batalla.iniciar();

	    PantallaBatalla pantalla = new PantallaBatalla();
	    ControladorBatalla controlador = new ControladorBatalla(pantalla, batalla, party);
	    controlador.setMercado(mercado);
	    pantalla.conectarControlador(controlador);

	    if (partidaCargada != null) {
	        controlador.aplicarPartidaCargada(partidaCargada);
	    } else {
	        controlador.iniciar();
	    }

	    pantalla.setVisible(true);
	}
}