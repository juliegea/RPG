package modelo.entidades;

import java.util.List;

import modelo.habilidades.EfectoDeEstado;
import modelo.habilidades.Habilidad;

import java.util.ArrayList;

public abstract class Entidad {
	private String nombre;
	protected int nivel;
	protected int vidaMaxima;
	private int vidaActual;
	private int manaMaximo;
	private int manaActual;
	protected int ataque;
	protected int defensa;
	protected int velocidad;
	protected boolean defendiendo;

	private final List<Habilidad> habilidades;
	private final List<EfectoDeEstado> efectosDeEstado;

	public String getNombre() {
		return nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public int getVidaMaxima() {
		return vidaMaxima;
	}

	public int getVidaActual() {
		return vidaActual;
	}

	public int getManaMaximo() {
		return manaMaximo;
	}

	public int getManaActual() {
		return manaActual;
	}

	public int getAtaque() {
		return ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public int getVelocidad() {
		return velocidad;
	}
	
	public List<Habilidad> getHabilidad() {
		return habilidades;
	}

	public Entidad(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataque, int defensa, int velocidad) {
		this.nombre = nombre;
		this.nivel = nivel;
		this.vidaMaxima = vidaMaxima;
		this.manaMaximo = manaMaximo;
		this.ataque = ataque;
		this.defensa = defensa;
		this.velocidad = velocidad;
		this.vidaActual = vidaMaxima;
		this.manaActual = manaMaximo;
		this.defendiendo = false;
		this.habilidades = new ArrayList<>();
		this.efectosDeEstado = new ArrayList<>();
	}

	public int recibirAtaque(int poderAtaque) {
		for (EfectoDeEstado e: efectosDeEstado) {
			if (e.bloqueaDanio()) {
				e.descontarTurnos();
				return 0;	
			}
		}
		int danio = poderAtaque - (defensaEfectiva() / 2);
		if (danio < 1) {
			danio = 1;
		}
		vidaActual -= danio;
		
		if (vidaActual < 0) {
			vidaActual = 0;
		}

		return danio;
	}
	
	public int recibirDanio(int danio) {
		vidaActual -= danio;
		if (vidaActual < 0) {
			vidaActual = 0;
		}
		return danio;
	}

	public boolean estaViva() {
		return vidaActual > 0;
	}

	public void curar(int cantidad) {
		vidaActual = vidaActual + cantidad;
		if (vidaActual > vidaMaxima) {
			vidaActual = vidaMaxima;
		}
	}

	public boolean consumirMana(int costo) {
		if (costo > manaActual) {
			return false;
		} else {
			this.manaActual -= costo;
			return true;
		}
	}

	public void defender() {
		defendiendo = true;
	}

	public int usarHabilidad(Habilidad habilidad, List<Entidad> objetivos) {
		if (consumirMana(habilidad.getCostoMana())) {
			return habilidad.aplicarHabilidad(this, objetivos);
		}
		return 0;
	}

	protected int defensaEfectiva() {
		if (defendiendo) {
			return defensa * 3/2;
		}
		return defensa;
	}

	protected int ataqueEfectivo() {
		return ataque;
	}

	public int calcularDanioBase() {
		return ataqueEfectivo();
	}
	
	public void aplicarEstado(EfectoDeEstado estado) {
		efectosDeEstado.add(estado);
	}

	public void procesarEstados() {
		defendiendo = false;
		List<EfectoDeEstado> expirados = new ArrayList<>();
		for (EfectoDeEstado e: efectosDeEstado) {
			e.aplicarEfectoEstado(this);
			e.descontarTurnos();
			if (e.expiroEfecto()) {
				expirados.add(e);	
			}
		}
		efectosDeEstado.removeAll(expirados);

	}

	public boolean atacaDosVeces() {
		return false;
	}

	public int multiplicarCuracion() {
		return 1;
	}
	
	public int bonusHabilidad() {
	    return 0;
	}

	public void limpiarEstadosNegativos() {
		List<EfectoDeEstado> negativos = new ArrayList<>();
		for (EfectoDeEstado e: efectosDeEstado) {
			if (e.esNegativo()) {
				negativos.add(e);	
			}
		}
		efectosDeEstado.removeAll(negativos);
	}
	
	public void restaurarMana() { 
		manaActual = manaMaximo; 
	}
	
	public List<String> mostrarNombreEstados() {
		List<String> nombresEstados = new ArrayList<>();
		for (EfectoDeEstado e: efectosDeEstado) {
			nombresEstados.add(e.getNombre());
		}
		return nombresEstados;
	}
	
	public void agregarHabilidad(Habilidad habilidad) {
			habilidades.add(habilidad);
	}
	
	@Override
	public String toString() {
	    return nombre;
	}
}
