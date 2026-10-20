package vista.paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.CargadorImagenes;

public class PanelSprite extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JLabel lblSprite;

    public PanelSprite() {

        setLayout(new BorderLayout());

        setPreferredSize(
            new Dimension(140, 160)
        );

        setBorder(
            BorderFactory.createEtchedBorder()
        );

        lblSprite =
            new JLabel(
                "-",
                SwingConstants.CENTER
            );

        add(lblSprite, BorderLayout.CENTER);
    }

    public void actualizar(
            String nombreArchivo,
            String textoAlternativo) {

        ImageIcon icono =
            CargadorImagenes.cargar(nombreArchivo);

        if (icono == null) {
            lblSprite.setIcon(null);
            lblSprite.setText(textoAlternativo);
            return;
        }

        Image escalada =
            icono.getImage().getScaledInstance(
                120,
                140,
                Image.SCALE_SMOOTH
            );

        lblSprite.setText(null);
        lblSprite.setIcon(new ImageIcon(escalada));
    }
}
