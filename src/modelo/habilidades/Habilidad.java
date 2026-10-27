package modelo.habilidades;

import java.util.List;

import modelo.entidades.Entidad;

public class Habilidad {
	private String nombre;
	private int costoMana;
	private int alcance;
	private TipoObjetivo tipoObjetivo;
	private Efecto efecto;
	public Habilidad(String nombre, int costoMana, int alcance, TipoObjetivo tipoObjetivo, Efecto efecto) {
		this.nombre = nombre;
		this.costoMana = costoMana;
		this.alcance = alcance;
		this.tipoObjetivo = tipoObjetivo;
		this.efecto = efecto;
	}
	
	public int aplicarHabilidad(Entidad origen, List<Entidad> objetivos) {
		return efecto.aplicarEfecto(origen, objetivos);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getCostoMana() {
		return costoMana;
	}
	
	public TipoObjetivo getTipoObjetivo() {
		return tipoObjetivo;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
