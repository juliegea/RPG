package app;

import java.util.ArrayList;
import java.util.List;

import controlador.ControladorBatalla;
import modelo.combate.Batalla;
import modelo.combate.Party;
import modelo.entidades.Arquero;
import modelo.entidades.Brujo;
import modelo.entidades.Curador;
import modelo.entidades.Demonio;
import modelo.entidades.Elfo;
import modelo.entidades.Enemigo;
import modelo.entidades.Guerrero;
import modelo.entidades.Hechicera;
import modelo.entidades.Mago;
import modelo.entidades.Personaje;
import modelo.habilidades.AplicarEstado;
import modelo.habilidades.Curacion;
import modelo.habilidades.Danio;
import modelo.habilidades.DanioPorTurno;
import modelo.habilidades.Escudo;
import modelo.habilidades.Habilidad;
import modelo.habilidades.LimpiarEstados;
import modelo.habilidades.TipoObjetivo;
import vista.PantallaBatalla;

/**
 * Punto de entrada.
 *
 * El orden de armado importa y es el mismo del ejemplo de clase:
 *
 *   1. crear el modelo            (todavia no existe)
 *   2. crear la vista
 *   3. crear el controlador pasandole modelo y vista
 *   4. conectar el controlador a la vista
 *   5. pedir el primer refresco y mostrar la ventana
 *
 * Todo dentro de invokeLater porque Swing debe construirse en su
 * propio hilo de eventos.
 */
public class Main {

    public static void main(String[] args) {
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
    	
    	// Guerrero
    	Habilidad golpeContraElPiso = new Habilidad("Golpe contra el piso", 30, 3, TipoObjetivo.ENEMIGO, new Danio(45));
    	guerrero.agregarHabilidad(golpeContraElPiso);

    	// Mago
    	Habilidad descargaArcana = new Habilidad("Descarga Arcana", 40, 1, TipoObjetivo.ENEMIGO, new Danio(80));
    	mago.agregarHabilidad(descargaArcana);

    	Habilidad agujeroNegro = new Habilidad("Agujero Negro", 160, 4, TipoObjetivo.ENEMIGO, new Danio(200));
    	mago.agregarHabilidad(agujeroNegro);

    	// Arquero
    	Habilidad flechaDeAcero = new Habilidad("Flecha de acero", 30, 1, TipoObjetivo.ENEMIGO, new Danio(70));
    	arquero.agregarHabilidad(flechaDeAcero);

    	Habilidad caceria = new Habilidad("Cacería", 70, 3, TipoObjetivo.ENEMIGO, new Danio(60));
    	arquero.agregarHabilidad(caceria);

    	Habilidad flechaDeSerpiente = new Habilidad("Flecha de Serpiente", 50, 1, TipoObjetivo.ENEMIGO,
    	        new AplicarEstado(new DanioPorTurno("Veneno", 5, 4)));
    	arquero.agregarHabilidad(flechaDeSerpiente);

    	Habilidad flechaIncendiaria = new Habilidad("Flecha Incendiaria", 35, 1, TipoObjetivo.ENEMIGO,
    	        new AplicarEstado(new DanioPorTurno("Quemadura", 7, 4)));
    	arquero.agregarHabilidad(flechaIncendiaria);

    	// Curador
    	Habilidad luzSanadora = new Habilidad("Luz Sanadora", 35, 1, TipoObjetivo.ALIADO, new Curacion(15));
    	curador.agregarHabilidad(luzSanadora);

    	Habilidad escudoSagrado = new Habilidad("Escudo Sagrado", 80, 1, TipoObjetivo.ALIADO, new AplicarEstado(new Escudo(0, 1)));
    	curador.agregarHabilidad(escudoSagrado);

    	Habilidad purificacion = new Habilidad("Purificación", 70, 1, TipoObjetivo.ALIADO, new LimpiarEstados());
    	curador.agregarHabilidad(purificacion);
   
    	
    	Brujo brujo = new Brujo("Brujo", 1, 180, 200, 45, 25, 45, 80, 450);
    	Elfo elfo = new Elfo("Elfo", 1, 200, 150, 50, 30, 65, 80, 450);
    	Demonio demonio = new Demonio("Demonio", 1, 280, 120, 60, 45, 25, 100, 550);
    	Hechicera hechicera = new Hechicera("Hechicera", 1, 170, 250, 30, 25, 40, 70, 400);
    	
    	List<Enemigo> enemigos = new ArrayList<>();
    	enemigos.add(brujo);
    	enemigos.add(elfo);
    	enemigos.add(demonio);
    	enemigos.add(hechicera);
    	
    	Batalla batalla = new Batalla(party, enemigos);
    	
    	Habilidad curacionHechicera = new Habilidad("Curación", 40, 1, TipoObjetivo.ALIADO, new Curacion(15));
    	hechicera.agregarHabilidad(curacionHechicera);
    	
    	batalla.iniciar();
    	
//    	for (int i = 0; i < 8; i++) {
//    		System.out.println("Turno actual: " + batalla.turnoActual().getNombre());
//    		batalla.avanzarTurno();
//    	}
//    	
//    	int vueltas = 0;
    	
//    	while (!batalla.batallaTermino() && vueltas < 200) {
//    		vueltas++;
//    		Entidad actual = batalla.turnoActual();
//    		Entidad objetivo = null;
//
//    		if (actual instanceof Enemigo) {
//    		    objetivo = party.integrantesVivos().get(0);
//    		} else {
//    			for (Enemigo e: enemigos) {
//    				if (e.estaViva()) {
//    					objetivo = e;
//    					break;
//    				}
//    			}
//    		}
//    		int danio = batalla.ejecutarAccion(new Atacar(), objetivo);
//    		System.out.println("Turno actual: " + batalla.turnoActual().getNombre());
//    		System.out.println("El personaje: " + batalla.turnoActual().getNombre() + " Ataco a: " + objetivo.getNombre() + " realizando un daño de:" + danio +"Hp" + " Vida restante de: " + objetivo.getNombre() +" " + objetivo.getVidaActual() + "Hp");
//    		batalla.avanzarTurno();
    		
    		
//    	}

//        SwingUtilities.invokeLater(() -> {
//
            // 2. vista
            PantallaBatalla pantalla =
                new PantallaBatalla();

            // 3. controlador
            ControladorBatalla controlador =
                new ControladorBatalla(pantalla, batalla, party);

            // 4. conexion
            pantalla.conectarControlador(controlador);

            // 5. primer dibujado
            controlador.iniciar();

            pantalla.setVisible(true);
    	
    	
//        });
    	
//    	Entidad guerrero = new Guerrero("Guerrero", 1, 450, 120, 65, 60, 30);
//    	
//    	Entidad brujo = new Guerrero("Brujo", 1, 180, 200, 45, 25, 45);
//    	
//    	System.out.println("Vida total: " + brujo.getVidaActual());
//    	
//    	int danio = brujo.recibirAtaque(guerrero.getAtaque());
//    	
//    	System.out.println("Danio realizado: " + danio);
//    	System.out.println("Vida total: " + brujo.getVidaActual());
    	
//    	Guerrero guerrero = new Guerrero("Guerrero", 1, 450, 120, 65, 60, 30);
//    	System.out.println("Ataque:" + guerrero.getAtaque());
//    	System.out.println("Nivel:" + guerrero.getNivel());
//    	System.out.println("Exp:" + guerrero.getExperiencia());
//    	System.out.println("Fuerza:" + guerrero.getFuerza());
//    	guerrero.ganarExperiencia(200);
//    	System.out.println("Ataque:" + guerrero.getAtaque());
//    	System.out.println("Nivel:" + guerrero.getNivel());
//    	System.out.println("Exp:" + guerrero.getExperiencia());
//    	System.out.println("Fuerza:" + guerrero.getFuerza());
    	
//    	Guerrero guerrero = new Guerrero("Guerrero", 1, 450, 120, 65, 60, 30);
//    	for (int i = 0; i < 20; i++) {
//    	    System.out.println("Daño: " + guerrero.calcularDanioBase());
//    	}
    	
    	
    	
    	
    	
    	
    }
}
