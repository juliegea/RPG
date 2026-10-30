package app;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controlador.ControladorBatalla;
import modelo.combate.*;
import modelo.entidades.*;
import modelo.habilidades.*;
import modelo.items.*;
import vista.*;

import java.io.File;
import java.awt.Font;

public class Main {
	public static void main(String[] args) {
	    try {
	        Font pixel = Font.createFont(Font.TRUETYPE_FONT,
	            new File("src/recursos/PressStart2P-Regular.ttf")).deriveFont(11f);

	        UIManager.put("Label.font", pixel);
	        UIManager.put("Button.font", pixel.deriveFont(12f));
	        UIManager.put("TextArea.font", pixel.deriveFont(10f));
	        UIManager.put("List.font", pixel);
	        UIManager.put("ComboBox.font", pixel);
	        UIManager.put("OptionPane.messageFont", pixel);
	        UIManager.put("TitledBorder.font", pixel);
	    } catch (Exception e) {
	        System.out.println("No se pudo cargar la fuente pixel, usando Monospaced");
	        Font pixel = new Font("Monospaced", Font.PLAIN, 12);
	        UIManager.put("Label.font", pixel);
	        UIManager.put("Button.font", pixel);
	    }

	    SwingUtilities.invokeLater(() -> {
	        PantallaInicio inicio = new PantallaInicio();
	        inicio.conectarAccion(() -> iniciarJuego());
	        inicio.setVisible(true);
	    });
	}

    private static void iniciarJuego() {
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

        // Habilidades guerrero
        guerrero.agregarHabilidad(new Habilidad("Golpe contra el piso", 30, 3, TipoObjetivo.ENEMIGO, new Danio(45)));

        // Habilidades mago
        mago.agregarHabilidad(new Habilidad("Descarga Arcana", 40, 1, TipoObjetivo.ENEMIGO, new Danio(80)));
        mago.agregarHabilidad(new Habilidad("Agujero Negro", 160, 4, TipoObjetivo.ENEMIGO, new Danio(200)));

        // Habilidades arquero
        arquero.agregarHabilidad(new Habilidad("Flecha de acero", 30, 1, TipoObjetivo.ENEMIGO, new Danio(70)));
        arquero.agregarHabilidad(new Habilidad("Cacería", 70, 3, TipoObjetivo.ENEMIGO, new Danio(60)));
        arquero.agregarHabilidad(new Habilidad("Flecha de Serpiente", 50, 1, TipoObjetivo.ENEMIGO,
                new AplicarEstado(new DanioPorTurno("Veneno", 5, 4))));
        arquero.agregarHabilidad(new Habilidad("Flecha Incendiaria", 35, 1, TipoObjetivo.ENEMIGO,
                new AplicarEstado(new DanioPorTurno("Quemadura", 7, 4))));

        // Habilidades curador
        curador.agregarHabilidad(new Habilidad("Luz Sanadora", 35, 1, TipoObjetivo.ALIADO, new Curacion(15)));
        curador.agregarHabilidad(new Habilidad("Escudo Sagrado", 80, 1, TipoObjetivo.ALIADO,
                new AplicarEstado(new Escudo(0, 1))));
        curador.agregarHabilidad(new Habilidad("Purificación", 70, 1, TipoObjetivo.ALIADO, new LimpiarEstados()));

        // Items iniciales
        party.getInventario().agregarItem(new Consumible("Poción débil", 50, new Curacion(15)));
        party.getInventario().agregarItem(new Consumible("Poción media", 100, new Curacion(25)));
        party.getInventario().agregarItem(new Consumible("Poción fuerte", 200, new Curacion(50)));

        // Mercado
        Mercado mercado = new Mercado();
        mercado.agregarAlCatalogo(new Consumible("Poción débil", 50, new Curacion(15)));
        mercado.agregarAlCatalogo(new Consumible("Poción media", 100, new Curacion(25)));
        mercado.agregarAlCatalogo(new Consumible("Poción fuerte", 200, new Curacion(50)));
        mercado.agregarAlCatalogo(new Equipable("Espada de hierro", 200, 0, 15, 0, 0, ParteDelCuerpo.ARMA));
        mercado.agregarAlCatalogo(new Equipable("Armadura de cuero", 180, 0, 0, 10, 5, ParteDelCuerpo.ARMADURA));

        // Batalla
        GeneradorDeBatalla generador = new GeneradorDeBatalla();
        List<Enemigo> enemigos = generador.generarEnemigos(1);
        Batalla batalla = new Batalla(party, enemigos);
        batalla.iniciar();

        // Vista
        PantallaBatalla pantalla = new PantallaBatalla();
        ControladorBatalla controlador = new ControladorBatalla(pantalla, batalla, party);
        controlador.setMercado(mercado);
        pantalla.conectarControlador(controlador);
        controlador.iniciar();
        pantalla.setVisible(true);
    }
}