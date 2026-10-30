package vista;

import java.awt.*;
import javax.swing.*;
import util.IconoOro;

public class PantallaVictoria extends JFrame {
    public PantallaVictoria(int numeroBatalla, int oro, boolean esFinal,
            int enemigosDerrota, int muertesParty, int oroGanado, int oroGastado,
            Runnable alSiguiente, Runnable alMercado, Runnable alEquipo, Runnable alSalir) {

        setTitle(esFinal ? "Juego Completado" : "Victoria");
        setSize(480, 550);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(new Color(15, 10, 25));
        setContentPane(contenido);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 15, 6, 15);
        gbc.gridx = 0;

        JLabel titulo = new JLabel(esFinal ? "Juego Completado" : "Batalla " + numeroBatalla + " superada");
        titulo.setFont(new Font("Monospaced", Font.BOLD, 22));
        titulo.setForeground(new Color(255, 215, 0));
        gbc.gridy = 0;
        contenido.add(titulo, gbc);

        JLabel separador = new JLabel("─────────────────────────");
        separador.setForeground(new Color(80, 60, 120));
        gbc.gridy = 1;
        contenido.add(separador, gbc);

        JPanel panelOro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelOro.setOpaque(false);
        ImageIcon moneda = IconoOro.get(18);
        JLabel lblOro;
        if (moneda != null) {
            lblOro = new JLabel(" " + oro, moneda, SwingConstants.CENTER);
        } else {
            lblOro = new JLabel("Oro actual: " + oro);
        }
        lblOro.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblOro.setForeground(new Color(230, 210, 170));
        panelOro.add(lblOro);
        gbc.gridy = 2;
        contenido.add(panelOro, gbc);

        if (esFinal) {
            String[] stats = {
                "Enemigos derrotados: " + enemigosDerrota,
                "Muertes de la party: " + muertesParty,
                "Oro total ganado: " + oroGanado,
                "Oro total gastado: " + oroGastado
            };
            for (String s : stats) {
                gbc.gridy++;
                JLabel lbl = new JLabel(s);
                lbl.setForeground(new Color(170, 160, 150));
                lbl.setFont(new Font("Monospaced", Font.PLAIN, 12));
                contenido.add(lbl, gbc);
            }
        }

        gbc.insets = new Insets(15, 15, 5, 15);
        if (!esFinal) {
            JButton btnSiguiente = crearBoton("Próxima batalla");
            gbc.gridy++;
            contenido.add(btnSiguiente, gbc);
            btnSiguiente.addActionListener(e -> { dispose(); alSiguiente.run(); });

            gbc.insets = new Insets(5, 15, 5, 15);
            JButton btnMercado = crearBoton("Mercado");
            gbc.gridy++;
            contenido.add(btnMercado, gbc);
            btnMercado.addActionListener(e -> alMercado.run());

            JButton btnEquipo = crearBoton("Equipamiento");
            gbc.gridy++;
            contenido.add(btnEquipo, gbc);
            btnEquipo.addActionListener(e -> alEquipo.run());
        }

        gbc.insets = new Insets(5, 15, 15, 15);
        JButton btnSalir = crearBoton(esFinal ? "Cerrar juego" : "Salir");
        gbc.gridy++;
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