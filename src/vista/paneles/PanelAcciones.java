package vista.paneles;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import controlador.ControladorBatalla;

/**
 * Las cuatro acciones que la consigna pide para cada turno.
 *
 * El panel no sabe que hace cada accion: solo avisa al controlador.
 * Toda la logica vive en el modelo.
 */
public class PanelAcciones extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JButton btnAtacar;
    private final JButton btnDefender;
    private final JButton btnHabilidad;
    private final JButton btnItem;

    public PanelAcciones(
            ControladorBatalla controlador) {

        setBorder(
            BorderFactory.createTitledBorder("Acciones")
        );

        setLayout(new GridLayout(2, 2, 8, 8));

        btnAtacar = new JButton("Atacar");
        btnDefender = new JButton("Defender");
        btnHabilidad = new JButton("Habilidad");
        btnItem = new JButton("Item");

        btnAtacar.addActionListener(
            e -> controlador.atacar()
        );

        btnDefender.addActionListener(
            e -> controlador.defender()
        );

        btnHabilidad.addActionListener(
            e -> controlador.usarHabilidad()
        );

        btnItem.addActionListener(
            e -> controlador.usarItem()
        );

        add(btnAtacar);
        add(btnDefender);
        add(btnHabilidad);
        add(btnItem);
    }

    public void setHabilitado(
            boolean habilitado) {

        btnAtacar.setEnabled(habilitado);
        btnDefender.setEnabled(habilitado);
        btnHabilidad.setEnabled(habilitado);
        btnItem.setEnabled(habilitado);
    }
}
