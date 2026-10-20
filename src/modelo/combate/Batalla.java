package modelo.combate;

import java.util.ArrayList;
import java.util.List;

import modelo.acciones.Accion;
import modelo.entidades.Enemigo;
import modelo.entidades.Entidad;
import modelo.entidades.Personaje;

public class Batalla {
	private final Party party;
	private final List<Enemigo> enemigos;
	private final List<Entidad> turnos;
	private boolean batallaTerminada;
	private boolean ganadorBatalla;
	private int posicionActual;
	
	public Batalla(Party party, List<Enemigo> enemigos) {
		this.party = party;
		this.enemigos = enemigos;
		this.turnos = new ArrayList<>();
		this.batallaTerminada = false;
		this.ganadorBatalla = false;
		this.posicionActual = 0;
	}
	public void iniciar() {
		ordenarTurnos();
		this.posicionActual = 0;
	}
	
	private void ordenarTurnos() {
		turnos.addAll(enemigos);
		turnos.addAll(party.integrantesVivos());
		turnos.sort((a, b) -> b.getVelocidad() - a.getVelocidad());
	}
	
	public Entidad turnoActual() {
		return turnos.get(posicionActual);
	}
	
	public void avanzarTurno() {
		posicionActual = (posicionActual + 1) % turnos.size();
		while (!turnos.get(posicionActual).estaViva()) {
			posicionActual = (posicionActual + 1) % turnos.size();
		}
		turnoActual().procesarEstados();
	}
	
	private boolean quedanEnemigosVivos() {
		for (Enemigo e: enemigos) {
			if (e.estaViva()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean batallaTermino() {
		if (party.partyDerrotada()) {
			ganadorBatalla = false;
			batallaTerminada = true;
			return true;
		}
		if (!quedanEnemigosVivos()) {
			ganadorBatalla = true;
			batallaTerminada = true;
			return true;
		}
		return false;
	}
	
	public boolean ganadorDeBatalla() {
		return ganadorBatalla;
	}
	
	public int ejecutarAccion(Accion accion, Entidad objetivo) {
	    return accion.aplicarAccion(turnoActual(), objetivo);
	}
	
	public void otorgarExperiencia(Enemigo enemigo, Personaje personaje) {
		personaje.ganarExperiencia(enemigo.recompensaExperiencia());
	}
	
	public void otorgarOro(Enemigo enemigo, Party party) {
		party.sumarOro(enemigo.recompensaOro());
	}
	
	public List<Enemigo> getEnemigo() {
		return enemigos;
	}
}
