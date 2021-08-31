/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prostredky;

/**
 *
 * @author Dan
 */
    public enum KomparatorTyp {
    KOMPARATOR_SPZ("Podle SPZ"),
    KOMPARATOR_ID("Podle ID");
    
    private final String nazev;

    private KomparatorTyp(String nazev) {
        this.nazev = nazev;
    }

    public String getNazev() {
        return nazev;
    }

    public static Enum[] getKomparatory() {
        Enum[] vycet = {KOMPARATOR_SPZ,KOMPARATOR_ID};
        return vycet;
    }

    @Override
    public String toString() {
        return nazev;
    }
}
