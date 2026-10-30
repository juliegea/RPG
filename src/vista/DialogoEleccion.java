package vista;

import java.awt.*;
import javax.swing.*;
import util.CargadorImagenes;

public class DialogoEleccion {

    public static Object mostrar(Component padre, String titulo, String mensaje, Object[] opciones) {
        return mostrar(padre, titulo, mensaje, opciones, null);
    }

    public static Object mostrar(Component padre, String titulo, String mensaje, 
            Object[] opciones, String[] sprites) {
        if (opciones == null || opciones.length == 0) return null;

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(20, 15, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMensaje = new JLabel(mensaje);
        lblMensaje.setFont(new Font("Monospaced", Font.BOLD, 12));
        lblMensaje.setForeground(new Color(230, 210, 170));
        panel.add(lblMensaje, BorderLayout.NORTH);

        DefaultListModel<Object> modelo = new DefaultListModel<>();
        for (Object o : opciones) modelo.addElement(o);

        JList<Object> lista = new JList<>(modelo);
        lista.setSelectedIndex(0);
        lista.setBackground(new Color(25, 20, 35));
        lista.setForeground(new Color(200, 190, 170));
        lista.setSelectionBackground(new Color(80, 60, 120));
        lista.setSelectionForeground(new Color(255, 230, 180));
        lista.setFont(new Font("Monospaced", Font.PLAIN, 12));
        lista.setFixedCellHeight(36);

        if (sprites != null) {
            lista.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                    label.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    if (!isSelected) {
                        label.setBackground(new Color(25, 20, 35));
                        label.setForeground(new Color(200, 190, 170));
                    } else {
                        label.setBackground(new Color(80, 60, 120));
                        label.setForeground(new Color(255, 230, 180));
                    }
                    if (index < sprites.length && sprites[index] != null) {
                        ImageIcon ic = CargadorImagenes.cargar(sprites[index]);
                        if (ic != null) {
                            Image img = ic.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
                            label.setIcon(new ImageIcon(img));
                        }
                    }
                    return label;
                }
            });
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setPreferredSize(new Dimension(320, 180));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(80, 60, 120)));
        scroll.getViewport().setBackground(new Color(25, 20, 35));
        panel.add(scroll, BorderLayout.CENTER);

        UIManager.put("OptionPane.background", new Color(20, 15, 30));
        UIManager.put("Panel.background", new Color(20, 15, 30));

        int resultado = JOptionPane.showConfirmDialog(
            padre, panel, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        if (resultado == JOptionPane.OK_OPTION && lista.getSelectedValue() != null) {
            return lista.getSelectedValue();
        }
        return null;
    }
}