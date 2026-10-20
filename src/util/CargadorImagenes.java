package util;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * Carga sprites desde src/recursos/sprites.
 * Si el archivo no existe devuelve null y la vista muestra un texto.
 */
public final class CargadorImagenes {

    private CargadorImagenes() {
    }

    public static ImageIcon cargar(
            String nombreArchivo) {

        String[] rutas = {
            "src/recursos/sprites/" + nombreArchivo,
            "RPG_Egea/src/recursos/sprites/" + nombreArchivo
        };

        for (String ruta : rutas) {

            File archivo =
                new File(ruta);

            if (archivo.exists()) {
                return new ImageIcon(ruta);
            }
        }

        return null;
    }
}
