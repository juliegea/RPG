package modelo.combate;

public class Partida {
    private int numeroBatalla;
    private int oroTotal;
    private int[] niveles;
    private int[] experiencias;
    private int oroGanado;
    private int oroGastado;
    private int enemigosDerrota;
    private int muertesParty;

    public Partida(int numeroBatalla, int oroTotal, int[] niveles, int[] experiencias,
            int oroGanado, int oroGastado, int enemigosDerrota, int muertesParty) {
        this.numeroBatalla = numeroBatalla;
        this.oroTotal = oroTotal;
        this.niveles = niveles;
        this.experiencias = experiencias;
        this.oroGanado = oroGanado;
        this.oroGastado = oroGastado;
        this.enemigosDerrota = enemigosDerrota;
        this.muertesParty = muertesParty;
    }

    public int getNumeroBatalla() { return numeroBatalla; }
    public int getOroTotal() { return oroTotal; }
    public int[] getNiveles() { return niveles; }
    public int[] getExperiencias() { return experiencias; }
    public int getOroGanado() { return oroGanado; }
    public int getOroGastado() { return oroGastado; }
    public int getEnemigosDerrota() { return enemigosDerrota; }
    public int getMuertesParty() { return muertesParty; }
}