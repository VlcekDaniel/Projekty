package util;


import java.util.Locale;

public enum Barva {

    BILA("bílá"),
    CERNA("černá"),
    MODRA("modrá"),
    ZELENA("zelená"),
    CEVENA("červená");
    private final String nazev;

    private Barva(String nazev) {
        this.nazev = nazev;
    }

    public String getNazev() {
        return nazev;
    }

    @Override
    public String toString() {
        return nazev;
    }

    public static Barva decode(String textBarva) {
        Barva barva = null;
        switch (textBarva.trim().toLowerCase(new Locale("cs", "CZ"))) {
            case "cerna":
            case "černá":
                barva = Barva.CERNA;
                break;
            case "cervena":
            case "červená":
                barva = Barva.CEVENA;
                break;
            case "bila":
            case "bílá":
                barva = Barva.BILA;
                break;
            case "modra":
            case "modrá":
                barva = Barva.MODRA;
                break;
            case "zelena":
            case "zelená":
                barva = Barva.ZELENA;
                break;
            default:

        }
        return barva;
    }
}
