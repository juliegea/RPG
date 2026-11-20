package modelo.combate;

import java.io.*;

public class GestorDeGuardado {
    private static final String ARCHIVO = "partida_guardada.txt";

    public void guardar(Partida partida) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            pw.println(partida.getNumeroBatalla());
            pw.println(partida.getOroTotal());
            pw.println(partida.getOroGanado());
            pw.println(partida.getOroGastado());
            pw.println(partida.getEnemigosDerrota());
            pw.println(partida.getMuertesParty());
            for (int n : partida.getNiveles()) {
                pw.print(n + ",");
            }
            pw.println();
            for (int e : partida.getExperiencias()) {
                pw.print(e + ",");
            }
            pw.println();
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public Partida cargar() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            int numeroBatalla = Integer.parseInt(br.readLine().trim());
            int oroTotal = Integer.parseInt(br.readLine().trim());
            int oroGanado = Integer.parseInt(br.readLine().trim());
            int oroGastado = Integer.parseInt(br.readLine().trim());
            int enemigosDerrota = Integer.parseInt(br.readLine().trim());
            int muertesParty = Integer.parseInt(br.readLine().trim());

            String[] nivelesStr = br.readLine().trim().split(",");
            int[] niveles = new int[4];
            for (int i = 0; i < 4 && i < nivelesStr.length; i++) {
                if (!nivelesStr[i].isEmpty()) niveles[i] = Integer.parseInt(nivelesStr[i]);
            }

            String[] expStr = br.readLine().trim().split(",");
            int[] experiencias = new int[4];
            for (int i = 0; i < 4 && i < expStr.length; i++) {
                if (!expStr[i].isEmpty()) experiencias[i] = Integer.parseInt(expStr[i]);
            }

            return new Partida(numeroBatalla, oroTotal, niveles, experiencias,
                    oroGanado, oroGastado, enemigosDerrota, muertesParty);
        } catch (Exception e) {
            System.out.println("Error al cargar: " + e.getMessage());
            return null;
        }
    }

    public boolean existeGuardado() {
        return new File(ARCHIVO).exists();
    }
}