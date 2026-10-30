package vista;

import java.awt.*;
import javax.swing.*;

public class PantallaDerrota extends JFrame {
    public PantallaDerrota(int numeroBatalla, Runnable alReiniciar, Runnable alSalir) {
        setTitle("Derrota");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(new Color(15, 10, 25));
        setContentPane(contenido);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Derrota");
        titulo.setFont(new Font("Monospaced", Font.BOLD, 28));
        titulo.setForeground(new Color(200, 40, 40));
        gbc.gridy = 0;
        contenido.add(titulo, gbc);

        JLabel mensaje = new JLabel("Tu party fue derrotada en la batalla " + numeroBatalla);
        mensaje.setFont(new Font("Monospaced", Font.PLAIN, 12));
        mensaje.setForeground(new Color(180, 160, 150));
        gbc.gridy = 1;
        contenido.add(mensaje, gbc);

        gbc.insets = new Insets(20, 15, 5, 15);
        JButton btnReiniciar = crearBoton("Reiniciar partida");
        gbc.gridy = 2;
        contenido.add(btnReiniciar, gbc);
        btnReiniciar.addActionListener(e -> { dispose(); alReiniciar.run(); });

        gbc.insets = new Insets(5, 15, 15, 15);
        JButton btnSalir = crearBoton("Salir");
        gbc.gridy = 3;
        contenido.add(btnSalir, gbc);
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(200, 38));
        btn.setBackground(new Color(40, 30, 60));
        btn.setForeground(new Color(230, 210, 170));
        btn.setFont(new Font("Monospaced", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 80, 140), 2));
        return btn;
    }
}