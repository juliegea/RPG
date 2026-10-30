package util;

import java.awt.Image;
import javax.swing.ImageIcon;

public final class IconoOro {
    private static ImageIcon icono;

    private IconoOro() {}

    public static ImageIcon get(int size) {
        if (icono == null) {
            icono = CargadorImagenes.cargar("oro.png");
        }
        if (icono == null) return null;
        Image img = icono.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
