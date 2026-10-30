package vista.paneles;

import java.awt.*;
import javax.swing.*;
import dto.EntidadDTO;
import util.CargadorImagenes;

public class PanelEstadoEntidad extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JLabel lblNombre;
    private final JProgressBar barraVida;
    private final JProgressBar barraMana;
    private final JLabel lblEstadisticas;
    private final JLabel lblEstados;
    private final JLabel lblSprite;

    public PanelEstadoEntidad() {
        setLayout(new BorderLayout(6, 4));
        setBackground(new Color(20, 15, 30));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 60, 120), 2),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));

        lblSprite = new JLabel("", SwingConstants.CENTER);
        lblSprite.setPreferredSize(new Dimension(70, 70));
        lblSprite.setFont(new Font("Monospaced", Font.BOLD, 28));
        lblSprite.setForeground(new Color(120, 100, 160));

        lblNombre = new JLabel("-", SwingConstants.CENTER);
        lblNombre.setFont(new Font("Monospaced", Font.BOLD, 11));
        lblNombre.setForeground(new Color(230, 210, 170));

        barraVida = new JProgressBar();
        barraVida.setStringPainted(true);
        barraVida.setForeground(new Color(180, 30, 30));
        barraVida.setBackground(new Color(40, 20, 20));
        barraVida.setFont(new Font("Monospaced", Font.BOLD, 9));

        barraMana = new JProgressBar();
        barraMana.setStringPainted(true);
        barraMana.setForeground(new Color(30, 80, 180));
        barraMana.setBackground(new Color(20, 20, 40));
        barraMana.setFont(new Font("Monospaced", Font.BOLD, 9));

        lblEstadisticas = new JLabel("-", SwingConstants.CENTER);
        lblEstadisticas.setForeground(new Color(170, 170, 190));
        lblEstadisticas.setFont(new Font("Monospaced", Font.PLAIN, 9));

        lblEstados = new JLabel(" ", SwingConstants.CENTER);
        lblEstados.setForeground(new Color(255, 180, 50));
        lblEstados.setFont(new Font("Monospaced", Font.ITALIC, 9));

        JPanel datos = new JPanel(new GridLayout(6, 1, 2, 2));
        datos.setOpaque(false);
        datos.add(lblNombre);
        datos.add(barraVida);
        datos.add(barraMana);
        datos.add(lblEstadisticas);
        datos.add(lblEstados);
        datos.add(new JLabel(""));

        add(lblSprite, BorderLayout.WEST);
        add(datos, BorderLayout.CENTER);
    }

    public void actualizar(EntidadDTO entidad) {
        lblNombre.setText(entidad.getNombre() + " Nv." + entidad.getNivel());

        int vidaMostrar = Math.max(0, entidad.getVidaActual());
        barraVida.setMaximum(Math.max(1, entidad.getVidaMaxima()));
        barraVida.setValue(vidaMostrar);
        barraVida.setString(vidaMostrar + "Hp/" + entidad.getVidaMaxima() + "Hp");

        barraMana.setMaximum(Math.max(1, entidad.getManaMaximo()));
        barraMana.setValue(Math.max(0, entidad.getManaActual()));
        barraMana.setString(entidad.getManaActual() + "Ma/" + entidad.getManaMaximo() + "Ma");

        String atq = "ATQ " + entidad.getAtaque();
        if (entidad.getBonoAtaque() != 0) atq += (entidad.getBonoAtaque() > 0 ? "+" : "") + entidad.getBonoAtaque();

        String def = "DEF " + entidad.getDefensa();
        if (entidad.getBonoDefensa() != 0) def += (entidad.getBonoDefensa() > 0 ? "+" : "") + entidad.getBonoDefensa();

        String vel = "VEL " + entidad.getVelocidad();
        if (entidad.getBonoVelocidad() != 0) vel += (entidad.getBonoVelocidad() > 0 ? "+" : "") + entidad.getBonoVelocidad();

        lblEstadisticas.setText(atq + " " + def + " " + vel);

        if (entidad.getEstados() == null || entidad.getEstados().isEmpty()) {
            lblEstados.setText(" ");
        } else {
            lblEstados.setText(String.join(", ", entidad.getEstados()));
        }

        ImageIcon icono = CargadorImagenes.cargar(entidad.getSprite());
        if (icono != null) {
            Image img = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            lblSprite.setIcon(new ImageIcon(img));
            lblSprite.setText(null);
        } else {
            lblSprite.setIcon(null);
            lblSprite.setText(entidad.getNombre().substring(0, 2));
        }
    }

    public void marcarEnTurno(boolean enTurno) {
        if (enTurno) {
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 200, 0), 3),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        } else {
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 60, 120), 2),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
            ));
        }
    }
}