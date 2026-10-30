package modelo.combate;

import java.util.ArrayList;
import java.util.List;

import modelo.entidades.Brujo;
import modelo.entidades.Demonio;
import modelo.entidades.Elfo;
import modelo.entidades.Enemigo;
import modelo.entidades.Hechicera;
import modelo.habilidades.Curacion;
import modelo.habilidades.Habilidad;
import modelo.habilidades.TipoObjetivo;

public class GeneradorDeBatalla {

	public GeneradorDeBatalla() {
	}

	public List<Enemigo> generarEnemigos(int numeroBatalla) {
		List<Enemigo> enemigos = new ArrayList<>();
		int nivel = numeroBatalla;
		switch (numeroBatalla) {
    		case 1:
    			enemigos.add(new Brujo("Brujo", 1, 180, 200, 45, 25, 45, 80, 450));
                enemigos.add(new Brujo("Brujo", 1, 180, 200, 45, 25, 45, 80, 450));
                enemigos.add(new Elfo("Elfo", 1, 200, 150, 50, 30, 65, 80, 450));
    			break;
    		case 2:
    			enemigos.add(new Brujo("Brujo", nivel, 180 * nivel, 200, 45 * nivel, 25 * nivel, 45, 80 * nivel, 450 * nivel));
    			enemigos.add(new Elfo("Elfo", nivel, 200 * nivel, 150, 50 * nivel, 30 * nivel, 65, 80 * nivel, 450 * nivel));
    			enemigos.add(new Elfo("Elfo", nivel, 200 * nivel, 150, 50 * nivel, 30 * nivel, 65, 80 * nivel, 450 * nivel));
    			enemigos.add(crearHechicera(nivel));
    			
    			break;
    		case 3:
    			enemigos.add(new Demonio("Demonio", nivel, 280 * nivel, 120, 60 * nivel, 45 * nivel, 25, 100, 550));
    			enemigos.add(new Demonio("Demonio", nivel, 280 * nivel, 120, 60 * nivel, 45 * nivel, 25, 100, 550));
    			enemigos.add(new Brujo("Brujo", nivel, 180 * nivel, 200, 45 * nivel, 25 * nivel, 45, 80, 450));
    			enemigos.add(crearHechicera(nivel));
    			break;
    		case 4:
    			enemigos.add(new Elfo("Elfo", nivel, 200 * nivel, 150, 50 * nivel, 30 * nivel, 65, 80, 450));
    			enemigos.add(new Demonio("Demonio", nivel, 280 * nivel, 120, 60 * nivel, 45 * nivel, 25, 100, 550));
    			enemigos.add(new Demonio("Demonio", nivel, 280 * nivel, 120, 60 * nivel, 45 * nivel, 25, 100, 550));
    			enemigos.add(crearHechicera(nivel));
    			break;
    		default:
    			break;
		}
		return enemigos;
	}
	
	private Hechicera crearHechicera(int nivel) {
	    Hechicera h = new Hechicera("Hechicera", nivel, 170 * nivel, 250, 30 * nivel, 25 * nivel, 40, 70, 400);
	    h.agregarHabilidad(new Habilidad("Curación", 40, 1, TipoObjetivo.ALIADO, new Curacion(15)));
	    return h;
	}
}