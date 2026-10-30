package vista;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import controlador.ControladorBatalla;
import dto.EntidadDTO;
import dto.EstadoBatallaDTO;
import vista.paneles.PanelAcciones;
import vista.paneles.PanelEstadoEntidad;
import vista.paneles.PanelRegistro;

public class PantallaBatalla extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JLabel lblTitulo;
    private final JLabel lblTurno;
    private final JPanel filaEnemigos;
    private final JPanel filaPersonajes;
    private final List<PanelEstadoEntidad> panelesEnemigos;
    private final List<PanelEstadoEntidad> panelesPersonajes;
    private final PanelRegistro panelRegistro;
    private final JPanel panelInferior;
    private PanelAcciones panelAcciones;

    public PantallaBatalla() {
        setTitle("RPG por turnos");
        setSize(1100, 750);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelesEnemigos = new ArrayList<>();
        panelesPersonajes = new ArrayList<>();

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        contenido.setBackground(new Color(15, 10, 25));
        setContentPane(contenido);

        lblTitulo = new JLabel("Batalla", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(230, 210, 170));

        lblTurno = new JLabel("Turno de: -", SwingConstants.RIGHT);
        lblTurno.setForeground(new Color(255, 200, 50));
        lblTurno.setFont(new Font("Serif", Font.BOLD, 16));

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(80, 60, 120)));
        encabezado.add(lblTitulo, BorderLayout.WEST);
        encabezado.add(lblTurno, BorderLayout.EAST);

        filaEnemigos = new JPanel(new GridLayout(1, 0, 10, 0));
        filaEnemigos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(160, 40, 40)),
            "Enemigos", 0, 0, new Font("Serif", Font.BOLD, 14), new Color(200, 60, 60)));
        filaEnemigos.setBackground(new Color(25, 15, 15));

        filaPersonajes = new JPanel(new GridLayout(1, 0, 10, 0));
        filaPersonajes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(40, 100, 160)),
            "Party", 0, 0, new Font("Serif", Font.BOLD, 14), new Color(60, 140, 200)));
        filaPersonajes.setBackground(new Color(15, 20, 30));

        JPanel centro = new JPanel(new GridLayout(2, 1, 0, 12));
        centro.setOpaque(false);
        centro.add(filaEnemigos);
        centro.add(filaPersonajes);

        panelRegistro = new PanelRegistro();
        panelInferior = new JPanel(new BorderLayout(10, 0));
        panelInferior.setOpaque(false);
        panelInferior.add(panelRegistro, BorderLayout.CENTER);

        contenido.add(encabezado, BorderLayout.NORTH);
        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(panelInferior, BorderLayout.SOUTH);
    }

    public void conectarControlador(ControladorBatalla controlador) {
        panelAcciones = new PanelAcciones(controlador);
        panelInferior.add(panelAcciones, BorderLayout.EAST);
    }

    public void actualizar(EstadoBatallaDTO estado) {
        lblTitulo.setText("Batalla " + estado.getNumeroBatalla());
        lblTurno.setText("Turno de: " + estado.getNombreEnTurno());
        dibujarFila(filaEnemigos, panelesEnemigos, estado.getEnemigos(), estado.getNombreEnTurno());
        dibujarFila(filaPersonajes, panelesPersonajes, estado.getPersonajes(), estado.getNombreEnTurno());
        panelRegistro.actualizar(estado.getMensaje());
        if (panelAcciones != null) {
            panelAcciones.setHabilitado(!estado.isBatallaTerminada());
        }
        revalidate();
        repaint();
    }

    private void dibujarFila(JPanel fila, List<PanelEstadoEntidad> paneles,
            List<EntidadDTO> entidades, String nombreEnTurno) {
        while (paneles.size() < entidades.size()) {
            PanelEstadoEntidad nuevo = new PanelEstadoEntidad();
            paneles.add(nuevo);
            fila.add(nuevo);
        }
        for (int i = 0; i < paneles.size(); i++) {
            PanelEstadoEntidad panel = paneles.get(i);
            if (i >= entidades.size()) { panel.setVisible(false); continue; }
            EntidadDTO entidad = entidades.get(i);
            panel.setVisible(true);
            panel.actualizar(entidad);
            panel.marcarEnTurno(entidad.getNombre().equals(nombreEnTurno));
        }
    }
}