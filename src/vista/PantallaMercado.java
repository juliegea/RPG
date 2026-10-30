package vista;

import java.awt.*;
import javax.swing.*;
import modelo.combate.Mercado;
import modelo.combate.Party;
import modelo.items.Equipable;
import modelo.items.Item;
import modelo.items.ParteDelCuerpo;
import util.CargadorImagenes;
import util.IconoOro;

public class PantallaMercado extends JFrame {
    public PantallaMercado(Mercado mercado, Party party) {
        setTitle("Mercado");
        setSize(550, 450);
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBackground(new Color(15, 10, 25));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(contenido);

        JLabel lblTitulo = new JLabel("Mercado", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(255, 215, 0));

        JPanel panelOro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelOro.setOpaque(false);
        ImageIcon moneda = IconoOro.get(20);
        JLabel lblOro;
        if (moneda != null) {
            lblOro = new JLabel(" " + party.getOro(), moneda, SwingConstants.CENTER);
        } else {
            lblOro = new JLabel("Oro: " + party.getOro());
        }
        lblOro.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblOro.setForeground(new Color(230, 210, 170));
        panelOro.add(lblOro);

        JPanel encabezado = new JPanel(new GridLayout(2, 1));
        encabezado.setOpaque(false);
        encabezado.add(lblTitulo);
        encabezado.add(panelOro);
        contenido.add(encabezado, BorderLayout.NORTH);

        DefaultListModel<String> modeloCatalogo = new DefaultListModel<>();
        for (Item item : mercado.verCatalogo()) {
            modeloCatalogo.addElement(item.getNombre() + "  -  " + item.getPrecio() + " oro");
        }
        JList<String> listaCatalogo = new JList<>(modeloCatalogo);
        listaCatalogo.setBackground(new Color(25, 20, 35));
        listaCatalogo.setForeground(new Color(200, 190, 170));
        listaCatalogo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        listaCatalogo.setSelectionBackground(new Color(80, 60, 120));
        listaCatalogo.setSelectionForeground(new Color(255, 230, 180));
        listaCatalogo.setFixedCellHeight(32);

        listaCatalogo.setCellRenderer(new DefaultListCellRenderer() {
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
                Item item = mercado.verCatalogo().get(index);
                String sprite;
                if (item.esConsumible()) {
                    sprite = "pocion_vida.png";
                } else {
                    Equipable eq = (Equipable) item;
                    if (eq.getParteDelCuerpo() == ParteDelCuerpo.ARMADURA) {
                        sprite = "armadura.png";
                    } else {
                        sprite = "espada.png";
                    }
                }
                ImageIcon ic = CargadorImagenes.cargar(sprite);
                if (ic != null) {
                    Image img = ic.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));
                }
                return label;
            }
        });

        JScrollPane scrollCatalogo = new JScrollPane(listaCatalogo);
        scrollCatalogo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 60, 120)),
            "Catálogo", 0, 0, new Font("Monospaced", Font.BOLD, 11), new Color(180, 160, 120)));
        scrollCatalogo.getViewport().setBackground(new Color(25, 20, 35));
        contenido.add(scrollCatalogo, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setOpaque(false);
        JButton btnComprar = crearBoton("Comprar");
        JButton btnVender = crearBoton("Vender");
        JButton btnCerrar = crearBoton("Cerrar");
        botones.add(btnComprar);
        botones.add(btnVender);
        botones.add(btnCerrar);
        contenido.add(botones, BorderLayout.SOUTH);

        btnComprar.addActionListener(e -> {
            int idx = listaCatalogo.getSelectedIndex();
            if (idx < 0) {
                JOptionPane.showMessageDialog(this, "Seleccioná un ítem del catálogo");
                return;
            }
            Item item = mercado.verCatalogo().get(idx);
            if (mercado.comprarItem(item, party)) {
                JOptionPane.showMessageDialog(this, "Compraste " + item.getNombre());
            } else {
                JOptionPane.showMessageDialog(this, "No te alcanza el oro");
            }
            actualizarOro(lblOro, party);
        });

        btnVender.addActionListener(e -> {
            var inv = party.getInventario().verInventario();
            if (inv.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tenés ítems para vender");
                return;
            }
            String[] nombres = new String[inv.size()];
            String[] sprites = new String[inv.size()];
            for (int i = 0; i < inv.size(); i++) {
                nombres[i] = inv.get(i).getNombre() + "  -  " + inv.get(i).precioDeVenta() + " oro";
                if (inv.get(i).esConsumible()) {
                    sprites[i] = "pocion_vida.png";
                } else {
                    Equipable eq = (Equipable) inv.get(i);
                    if (eq.getParteDelCuerpo() == ParteDelCuerpo.ARMADURA) {
                        sprites[i] = "armadura.png";
                    } else {
                        sprites[i] = "espada.png";
                    }
                }
            }
            Object elegido = DialogoEleccion.mostrar(this, "Vender",
                "¿Qué vendés?", nombres, sprites);
            if (elegido == null) return;
            for (int i = 0; i < nombres.length; i++) {
                if (nombres[i].equals(elegido)) {
                    Item item = inv.get(i);
                    mercado.venderItem(item, party);
                    JOptionPane.showMessageDialog(this,
                        "Vendiste " + item.getNombre() + " por " + item.precioDeVenta() + " oro");
                    break;
                }
            }
            actualizarOro(lblOro, party);
        });

        btnCerrar.addActionListener(e -> dispose());
    }

    private void actualizarOro(JLabel lblOro, Party party) {
        ImageIcon moneda = IconoOro.get(20);
        if (moneda != null) {
            lblOro.setText(" " + party.getOro());
            lblOro.setIcon(moneda);
        } else {
            lblOro.setText("Oro: " + party.getOro());
        }
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBackground(new Color(40, 30, 60));
        btn.setForeground(new Color(230, 210, 170));
        btn.setFont(new Font("Monospaced", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 80, 140), 2));
        return btn;
    }
}