package vista.paneles;

import java.awt.*;
import javax.swing.*;

public class PanelRegistro extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JTextArea txtRegistro;

    public PanelRegistro() {
        setLayout(new BorderLayout());
        setBackground(new Color(15, 10, 25));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 60, 120)),
            "Registro", 0, 0, new Font("Serif", Font.BOLD, 13), new Color(180, 160, 120)));

        txtRegistro = new JTextArea(5, 40);
        txtRegistro.setEditable(false);
        txtRegistro.setLineWrap(true);
        txtRegistro.setWrapStyleWord(true);
        txtRegistro.setBackground(new Color(20, 15, 30));
        txtRegistro.setForeground(new Color(200, 190, 170));
        txtRegistro.setFont(new Font("Serif", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(txtRegistro);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
    }

    public void actualizar(String mensaje) {
        txtRegistro.setText(mensaje);
    }
}