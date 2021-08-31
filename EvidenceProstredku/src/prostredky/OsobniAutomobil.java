package prostredky;

import util.Barva;

/**
 *
 * @author karel@simerda.cz
 */
public class OsobniAutomobil extends DopravniProstredek{

    private Barva barva;
    private int pocetSedadel;
    private int vykon;

    public OsobniAutomobil() {
        this(0, "");
    }

    public OsobniAutomobil(float hmotnost, String spz) {
        super(ProstredekTyp.OSOBNI_AUTOMOBIL, spz, hmotnost);
    }

    public OsobniAutomobil(String spz, Barva barva, int pocetSedadel, float hmotnost, int vykon) {
        this(hmotnost, spz);
        this.barva = barva;
        this.pocetSedadel = pocetSedadel;
        this.vykon = vykon;
    }

    public Barva getBarva() {
        return barva;
    }

    public void setBarva(Barva barva) {
        this.barva = barva;
    }

    public int getPocetSedadel() {
        return pocetSedadel;
    }

    public void setPocetSedadel(int pocetSedadel) {
        this.pocetSedadel = pocetSedadel;
    }

    public int getVykon() {
        return vykon;
    }

    public void setVykon(int vykon) {
        this.vykon = vykon;
    }

    @Override
    public String toString() {
         return super.toString() + ", barva=" + barva.getNazev() + ", pocetSedadel=" + pocetSedadel +  ", vykon=" + vykon + "KW";
    }   
}
