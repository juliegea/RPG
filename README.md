# ⚔️ RPG por Turnos
## Descripción

Juego RPG por turnos desarrollado en Java con interfaz gráfica Swing. El jugador controla un party de 4 personajes (Guerrero, Mago, Arquero, Curador) a través de 5 batallas contra enemigos generados progresivamente, culminando en un jefe final: el Rey sin Rostro.

## Características

- **Combate por turnos** con sistema de velocidad para orden de turno
- **4 clases de personaje** con mecánicas únicas (crítico, doble ataque, curación doble, bonus mágico)
- **Sistema de habilidades** con daño, curación, estados (veneno, quemadura, escudo) y limpieza de estados
- **Mercado** para comprar y vender ítems
- **Sistema de equipamiento** con 6 slots: Arma, Armadura, Casco, Botas, Accesorio, Escudo
- **Guardado y carga** de partida
- **5 batallas progresivas** con jefe final
- **Sprites pixel art** para personajes, enemigos e ítems
- **Interfaz temática** con estética fantasía (púrpura/dorado)

## Arquitectura

El proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)** con **DTOs** para la comunicación entre capas.

```
src/
  app/Main.java                        → Punto de entrada
  controlador/ControladorBatalla.java   → Coordina modelo y vista
  dto/                                  → EntidadDTO, EstadoBatallaDTO
  modelo/
    entidades/                          → Personajes y enemigos
    acciones/                           → Atacar, Defender, UsarHabilidad, UsarItem
    habilidades/                        → Habilidades, efectos, estados
    items/                              → Consumibles y equipables
    combate/                            → Batalla, Party, Mercado, Generador, Guardado
  vista/                                → Pantallas Swing
  util/                                 → CargadorImagenes, IconoOro
```

## Principios GRASP aplicados

| Principio | Aplicación |
|---|---|
| Experto | Entidad calcula su propio daño recibido |
| Creador | GeneradorDeBatalla crea enemigos |
| Controlador | ControladorBatalla coordina todo el flujo |
| Bajo Acoplamiento | Vista solo conoce DTOs |
| Alta Cohesión | Cada clase tiene una responsabilidad clara |
| Polimorfismo | Efecto, Accion, EfectoDeEstado, Enemigo |
| Fabricación Pura | DTO, GestorDeGuardado, GeneradorDeBatalla |
| Variaciones Protegidas | DTOs protegen la vista de cambios en el modelo |

## Patrones de diseño

- **MVC** — separación modelo/vista/controlador
- **Command** — acciones como objetos (Atacar, Defender, etc.)
- **Strategy** — IA de enemigos con decidirAccion() polimórfico
- **DTO** — transferencia de datos entre capas

## Cómo ejecutar

1. Clonar el repositorio
2. Abrir en un IDE con soporte Java (Eclipse, IntelliJ, VS Code)
3. Ejecutar `app/Main.java`

## Tecnologías

- Java 17+
- Swing (GUI)
- Serialización en archivo de texto (guardado)
