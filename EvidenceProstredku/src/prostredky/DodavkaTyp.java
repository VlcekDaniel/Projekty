package prostredky;

import java.util.Locale;

/**
 *
 * @author karel@simerda.cz
 */
public enum DodavkaTyp {
    DVOJ_KABINA("dvojkabina"), NASTAVBA("nástavba"), VALNIK("valník");

    private final String nazev;

    private DodavkaTyp(String nazev) {
        this.nazev = nazev;
    }

    public String getNazev() {
        return nazev;
    }

    public static DodavkaTyp decode(String text) {
        DodavkaTyp typ = null;
        switch (text.trim().toLowerCase(new Locale("cs", "CZ"))) {
            case "dvojkabina":
            case "dvojkab":
                typ = DVOJ_KABINA;
                break;
            case "nastavba":
            case "nástavba":
                typ = NASTAVBA;
                break;
            case "valnik":
            case "valník":
                typ = VALNIK;
                break;
            default:
        }
        return typ;
    }

    @Override
    public String toString() {
        return nazev;
    }

}
