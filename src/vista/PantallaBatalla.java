package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controlador.ControladorBatalla;
import dto.EntidadDTO;
import dto.EstadoBatallaDTO;
import vista.paneles.PanelAcciones;
import vista.paneles.PanelEstadoEntidad;
import vista.paneles.PanelRegistro;

/**
 * Pantalla de batalla.
 *
 * La vista solo muestra: recibe un EstadoBatallaDTO y se redibuja.
 * No conoce ninguna clase del modelo y no calcula nada.
 */
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

        setTitle("RPG - Batalla");
        setSize(1000, 680);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelesEnemigos = new ArrayList<>();
        panelesPersonajes = new ArrayList<>();

        JPanel contenido =
            new JPanel(new BorderLayout(10, 10));

        contenido.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        setContentPane(contenido);

        lblTitulo =
            new JLabel("Batalla", SwingConstants.LEFT);

        lblTitulo.setFont(
            new Font("SansSerif", Font.BOLD, 20)
        );

        lblTurno =
            new JLabel("Turno de: -", SwingConstants.RIGHT);

        JPanel encabezado =
            new JPanel(new BorderLayout());

        encabezado.add(lblTitulo, BorderLayout.WEST);
        encabezado.add(lblTurno, BorderLayout.EAST);

        filaEnemigos =
            new JPanel(new GridLayout(1, 0, 8, 0));

        filaEnemigos.setBorder(
            BorderFactory.createTitledBorder("Enemigos")
        );

        filaPersonajes =
            new JPanel(new GridLayout(1, 0, 8, 0));

        filaPersonajes.setBorder(
            BorderFactory.createTitledBorder("Party")
        );

        JPanel centro =
            new JPanel(new GridLayout(2, 1, 0, 10));

        centro.add(filaEnemigos);
        centro.add(filaPersonajes);

        panelRegistro = new PanelRegistro();

        panelInferior =
            new JPanel(new BorderLayout(10, 0));

        panelInferior.add(
            panelRegistro,
            BorderLayout.CENTER
        );

        contenido.add(encabezado, BorderLayout.NORTH);
        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Se llama una sola vez, despues de crear el controlador.
     * Resuelve que la vista y el controlador se necesiten mutuamente.
     */
    public void conectarControlador(
            ControladorBatalla controlador) {

        panelAcciones =
            new PanelAcciones(controlador);

        panelInferior.add(
            panelAcciones,
            BorderLayout.EAST
        );
    }

    /**
     * Unico punto de entrada de datos a la vista.
     */
    public void actualizar(
            EstadoBatallaDTO estado) {

        lblTitulo.setText(
            "Batalla " + estado.getNumeroBatalla()
        );

        lblTurno.setText(
            "Turno de: " + estado.getNombreEnTurno()
        );

        dibujarFila(
            filaEnemigos,
            panelesEnemigos,
            estado.getEnemigos(),
            estado.getNombreEnTurno()
        );

        dibujarFila(
            filaPersonajes,
            panelesPersonajes,
            estado.getPersonajes(),
            estado.getNombreEnTurno()
        );

        panelRegistro.actualizar(estado.getMensaje());

        if (panelAcciones != null) {

            panelAcciones.setHabilitado(
                !estado.isBatallaTerminada()
            );
        }

        revalidate();
        repaint();
    }

    private void dibujarFila(
            JPanel fila,
            List<PanelEstadoEntidad> paneles,
            List<EntidadDTO> entidades,
            String nombreEnTurno) {

        while (paneles.size() < entidades.size()) {

            PanelEstadoEntidad nuevo =
                new PanelEstadoEntidad();

            paneles.add(nuevo);
            fila.add(nuevo);
        }

        for (int i = 0; i < paneles.size(); i++) {

            PanelEstadoEntidad panel = paneles.get(i);

            if (i >= entidades.size()) {
                panel.setVisible(false);
                continue;
            }

            EntidadDTO entidad = entidades.get(i);

            panel.setVisible(true);
            panel.actualizar(entidad);

            panel.marcarEnTurno(
                entidad.getNombre().equals(nombreEnTurno)
            );
        }
    }
}
