package vista.paneles;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import dto.EntidadDTO;

/**
 * Ficha de una entidad: nombre, nivel, barras de vida y mana,
 * estadisticas y efectos de estado activos.
 *
 * Se usa tanto para personajes como para enemigos, porque recibe
 * un EntidadDTO y no le importa de que clase del modelo provino.
 */
public class PanelEstadoEntidad extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JLabel lblNombre;
    private final JProgressBar barraVida;
    private final JProgressBar barraMana;
    private final JLabel lblEstadisticas;
    private final JLabel lblEstados;

    public PanelEstadoEntidad() {

        setLayout(new BorderLayout(4, 4));

        setBorder(
            BorderFactory.createEtchedBorder()
        );

        lblNombre =
            new JLabel("-", SwingConstants.CENTER);

        lblNombre.setFont(
            new Font("SansSerif", Font.BOLD, 14)
        );

        barraVida = new JProgressBar();
        barraVida.setStringPainted(true);

        barraMana = new JProgressBar();
        barraMana.setStringPainted(true);

        lblEstadisticas =
            new JLabel("-", SwingConstants.CENTER);

        lblEstados =
            new JLabel(" ", SwingConstants.CENTER);

        JPanel datos =
            new JPanel(new GridLayout(5, 1, 2, 2));

        datos.add(lblNombre);
        datos.add(barraVida);
        datos.add(barraMana);
        datos.add(lblEstadisticas);
        datos.add(lblEstados);

        add(datos, BorderLayout.NORTH);
    }

    public void actualizar(
            EntidadDTO entidad) {

        lblNombre.setText(
            entidad.getNombre()
            + " Nv."
            + entidad.getNivel()
        );

        barraVida.setMaximum(entidad.getVidaMaxima());
        barraVida.setValue(entidad.getVidaActual());

        barraVida.setString(
            "HP "
            + entidad.getVidaActual()
            + " / "
            + entidad.getVidaMaxima()
        );

        barraMana.setMaximum(entidad.getManaMaximo());
        barraMana.setValue(entidad.getManaActual());

        barraMana.setString(
            "MP "
            + entidad.getManaActual()
            + " / "
            + entidad.getManaMaximo()
        );

        lblEstadisticas.setText(
            "ATQ " + entidad.getAtaque()
            + " | DEF " + entidad.getDefensa()
            + " | VEL " + entidad.getVelocidad()
        );

        if (entidad.getEstados() == null
                || entidad.getEstados().isEmpty()) {

            lblEstados.setText(" ");

        } else {

            lblEstados.setText(
                String.join(", ", entidad.getEstados())
            );
        }
    }

    public void marcarEnTurno(
            boolean enTurno) {

        if (enTurno) {
            setBorder(
                BorderFactory.createLineBorder(
                    java.awt.Color.ORANGE,
                    2
                )
            );
        } else {
            setBorder(
                BorderFactory.createEtchedBorder()
            );
        }
    }
}
