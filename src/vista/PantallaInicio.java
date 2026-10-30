package vista;

import java.awt.*;
import javax.swing.*;

public class PantallaInicio extends JFrame {
    private JButton btnNueva;
    private JButton btnSalir;

    public PantallaInicio() {
        setTitle("RPG por turnos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(new Color(15, 10, 25));
        setContentPane(contenido);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("RPG por turnos");
        titulo.setFont(new Font("Serif", Font.BOLD, 42));
        titulo.setForeground(new Color(255, 215, 0));
        gbc.gridy = 0;
        contenido.add(titulo, gbc);

        JLabel subtitulo = new JLabel("Una aventura de fantasía");
        subtitulo.setFont(new Font("Serif", Font.ITALIC, 16));
        subtitulo.setForeground(new Color(180, 160, 120));
        gbc.gridy = 1;
        contenido.add(subtitulo, gbc);

        gbc.insets = new Insets(20, 10, 5, 10);
        btnNueva = crearBoton("Nueva Partida");
        gbc.gridy = 2;
        contenido.add(btnNueva, gbc);

        gbc.insets = new Insets(5, 10, 10, 10);
        btnSalir = crearBoton("Salir");
        gbc.gridy = 3;
        contenido.add(btnSalir, gbc);

        btnSalir.addActionListener(e -> System.exit(0));
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setBackground(new Color(40, 30, 60));
        btn.setForeground(new Color(230, 210, 170));
        btn.setFont(new Font("Serif", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 80, 140), 2));
        return btn;
    }

    public void conectarAccion(Runnable alIniciar) {
        btnNueva.addActionListener(e -> {
            dispose();
            alIniciar.run();
        });
    }
}