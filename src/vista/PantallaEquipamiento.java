package vista;

import java.awt.*;
import javax.swing.*;
import modelo.combate.Party;
import modelo.entidades.Personaje;
import modelo.items.Equipable;
import modelo.items.Item;
import modelo.items.ParteDelCuerpo;
import util.CargadorImagenes;

public class PantallaEquipamiento extends JFrame {
    public PantallaEquipamiento(Party party) {
        setTitle("Equipamiento");
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBackground(new Color(15, 10, 25));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(contenido);

        JLabel lblTitulo = new JLabel("Equipamiento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(255, 215, 0));
        contenido.add(lblTitulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setOpaque(false);

        // Lista de personajes
        DefaultListModel<String> modeloPersonajes = new DefaultListModel<>();
        for (Personaje p : party.getIntegrantes()) {
            modeloPersonajes.addElement(p.getNombre() + " Nv." + p.getNivel());
        }
        JList<String> listaPersonajes = new JList<>(modeloPersonajes);
        listaPersonajes.setSelectedIndex(0);
        listaPersonajes.setBackground(new Color(25, 20, 35));
        listaPersonajes.setForeground(new Color(200, 190, 170));
        listaPersonajes.setSelectionBackground(new Color(80, 60, 120));
        listaPersonajes.setSelectionForeground(new Color(255, 230, 180));
        listaPersonajes.setFont(new Font("Monospaced", Font.PLAIN, 12));
        listaPersonajes.setFixedCellHeight(36);

        listaPersonajes.setCellRenderer(new DefaultListCellRenderer() {
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
                Personaje p = party.getIntegrantes().get(index);
                ImageIcon ic = CargadorImagenes.cargar(p.getNombre().toLowerCase() + ".png");
                if (ic != null) {
                    Image img = ic.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));
                }
                return label;
            }
        });

        JScrollPane scrollPj = new JScrollPane(listaPersonajes);
        scrollPj.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 60, 120)),
            "Personajes", 0, 0, new Font("Monospaced", Font.BOLD, 11), new Color(180, 160, 120)));
        scrollPj.setPreferredSize(new Dimension(200, 0));
        scrollPj.getViewport().setBackground(new Color(25, 20, 35));

        // Panel de equipo del seleccionado
        JTextArea txtEquipo = new JTextArea();
        txtEquipo.setEditable(false);
        txtEquipo.setBackground(new Color(25, 20, 35));
        txtEquipo.setForeground(new Color(200, 190, 170));
        txtEquipo.setFont(new Font("Monospaced", Font.PLAIN, 11));

        JScrollPane scrollEquipo = new JScrollPane(txtEquipo);
        scrollEquipo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 60, 120)),
            "Equipo actual", 0, 0, new Font("Monospaced", Font.BOLD, 11), new Color(180, 160, 120)));
        scrollEquipo.getViewport().setBackground(new Color(25, 20, 35));

        centro.add(scrollPj, BorderLayout.WEST);
        centro.add(scrollEquipo, BorderLayout.CENTER);
        contenido.add(centro, BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botones.setOpaque(false);
        JButton btnEquipar = crearBoton("Equipar");
        JButton btnDesequipar = crearBoton("Desequipar");
        JButton btnCerrar = crearBoton("Cerrar");
        botones.add(btnEquipar);
        botones.add(btnDesequipar);
        botones.add(btnCerrar);
        contenido.add(botones, BorderLayout.SOUTH);

        // Actualizar equipo al seleccionar personaje
        Runnable actualizarEquipo = () -> {
            int idx = listaPersonajes.getSelectedIndex();
            if (idx < 0) return;
            Personaje p = party.getIntegrantes().get(idx);
            
            int bonoAtq = 0, bonoDef = 0, bonoVel = 0, bonoVid = 0;
            for (Equipable eq : p.getEquipo()) {
                bonoAtq += eq.getBonoAtaque();
                bonoDef += eq.getBonoDefensa();
                bonoVel += eq.getBonoVelocidad();
                bonoVid += eq.getBonoVida();
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("ATQ: ").append(p.getAtaque());
            if (bonoAtq != 0) sb.append(" ").append(bonoAtq > 0 ? "+" : "").append(bonoAtq);
            sb.append("\n");
            
            sb.append("DEF: ").append(p.getDefensa());
            if (bonoDef != 0) sb.append(" ").append(bonoDef > 0 ? "+" : "").append(bonoDef);
            sb.append("\n");
            
            sb.append("VEL: ").append(p.getVelocidad());
            if (bonoVel != 0) sb.append(" ").append(bonoVel > 0 ? "+" : "").append(bonoVel);
            sb.append("\n");
            
            sb.append("VID: ").append(p.getVidaMaxima());
            if (bonoVid != 0) sb.append(" ").append(bonoVid > 0 ? "+" : "").append(bonoVid);
            sb.append("\n\n");
            
            if (p.getEquipo().isEmpty()) {
                sb.append("Sin equipamiento");
            } else {
                for (Equipable eq : p.getEquipo()) {
                    sb.append(eq.getParteDelCuerpo()).append(": ");
                    sb.append(eq.getNombre()).append("\n");
                }
            }
            txtEquipo.setText(sb.toString());
        };

        listaPersonajes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) actualizarEquipo.run();
        });
        actualizarEquipo.run();

        btnEquipar.addActionListener(e -> {
            int idx = listaPersonajes.getSelectedIndex();
            if (idx < 0) return;
            Personaje p = party.getIntegrantes().get(idx);

            // Filtrar equipables del inventario
            java.util.List<Equipable> equipables = new java.util.ArrayList<>();
            for (Item item : party.getInventario().verInventario()) {
                if (!item.esConsumible()) {
                    equipables.add((Equipable) item);
                }
            }
            if (equipables.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay equipables en el inventario");
                return;
            }

            String[] nombres = new String[equipables.size()];
            String[] sprites = new String[equipables.size()];
            for (int i = 0; i < equipables.size(); i++) {
                Equipable eq = equipables.get(i);
                nombres[i] = eq.getNombre() + " (" + eq.getParteDelCuerpo() + ")";
                sprites[i] = eq.getParteDelCuerpo() == ParteDelCuerpo.ARMADURA ? "armadura.png" : "espada.png";
            }

            Object elegido = DialogoEleccion.mostrar(this, "Equipar",
                "¿Qué le equipás a " + p.getNombre() + "?", nombres, sprites);
            if (elegido == null) return;

            for (int i = 0; i < nombres.length; i++) {
                if (nombres[i].equals(elegido)) {
                    Equipable eq = equipables.get(i);
                    for (Equipable actual : new java.util.ArrayList<>(p.getEquipo())) {
                        if (actual.getParteDelCuerpo() == eq.getParteDelCuerpo()) {
                            p.desequiparObjeto(actual);
                            party.getInventario().agregarItem(actual);
                            break;
                        }
                    }
                    p.usarObjeto(eq);
                    party.getInventario().quitarItem(eq);
                    JOptionPane.showMessageDialog(this,
                        p.getNombre() + " se equipó " + eq.getNombre());
                    break;
                }
            }
            actualizarEquipo.run();
        });

        btnDesequipar.addActionListener(e -> {
            int idx = listaPersonajes.getSelectedIndex();
            if (idx < 0) return;
            Personaje p = party.getIntegrantes().get(idx);

            if (p.getEquipo().isEmpty()) {
                JOptionPane.showMessageDialog(this, p.getNombre() + " no tiene nada equipado");
                return;
            }

            String[] nombres = new String[p.getEquipo().size()];
            for (int i = 0; i < p.getEquipo().size(); i++) {
                nombres[i] = p.getEquipo().get(i).getNombre();
            }

            Object elegido = DialogoEleccion.mostrar(this, "Desequipar",
                "¿Qué le sacás a " + p.getNombre() + "?", nombres);
            if (elegido == null) return;

            for (int i = 0; i < nombres.length; i++) {
                if (nombres[i].equals(elegido)) {
                    Equipable eq = p.getEquipo().get(i);
                    p.desequiparObjeto(eq);
                    party.getInventario().agregarItem(eq);
                    JOptionPane.showMessageDialog(this,
                        p.getNombre() + " se desequipó " + eq.getNombre());
                    break;
                }
            }
            actualizarEquipo.run();
        });

        btnCerrar.addActionListener(e -> dispose());
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(130, 35));
        btn.setBackground(new Color(40, 30, 60));
        btn.setForeground(new Color(230, 210, 170));
        btn.setFont(new Font("Monospaced", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 80, 140), 2));
        return btn;
    }
}