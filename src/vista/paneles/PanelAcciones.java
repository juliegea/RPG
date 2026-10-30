package vista.paneles;

import java.awt.*;
import javax.swing.*;
import controlador.ControladorBatalla;

public class PanelAcciones extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JButton btnAtacar;
    private final JButton btnDefender;
    private final JButton btnHabilidad;
    private final JButton btnItem;

    public PanelAcciones(ControladorBatalla controlador) {
        setBackground(new Color(15, 10, 25));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 60, 120)),
            "Acciones", 0, 0, new Font("Serif", Font.BOLD, 13), new Color(180, 160, 120)));
        setLayout(new GridLayout(2, 2, 6, 6));

        btnAtacar = crearBoton("Atacar");
        btnDefender = crearBoton("Defender");
        btnHabilidad = crearBoton("Habilidad");
        btnItem = crearBoton("Item");

        btnAtacar.addActionListener(e -> controlador.atacar());
        btnDefender.addActionListener(e -> controlador.defender());
        btnHabilidad.addActionListener(e -> controlador.usarHabilidad());
        btnItem.addActionListener(e -> controlador.usarItem());

        add(btnAtacar);
        add(btnDefender);
        add(btnHabilidad);
        add(btnItem);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(40, 30, 60));
        btn.setForeground(new Color(230, 210, 170));
        btn.setFont(new Font("Serif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 80, 140)));
        return btn;
    }

    public void setHabilitado(boolean habilitado) {
        btnAtacar.setEnabled(habilitado);
        btnDefender.setEnabled(habilitado);
        btnHabilidad.setEnabled(habilitado);
        btnItem.setEnabled(habilitado);
    }
}