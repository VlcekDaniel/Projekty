/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prostredky;

import java.util.Locale;

/**
 *
 * @author Dan
 */
public enum ZnackaTraktoru {
    JOHN_DEER("JohnDeer"),
    ZETOR("Zetor"),
    FENDT("Fendt"),
    LKT("LKT"),
    SVOBODA("Svoboda");

    private final String nazev;

    private ZnackaTraktoru(String nazev) {
        this.nazev = nazev;
    }

    public String getNazev() {
        return nazev;
    }

    public static Enum[] getZnackyTraktoru() {
        Enum[] vycet = {JOHN_DEER, ZETOR, FENDT, LKT, SVOBODA};
        return vycet;
    }

    public static ZnackaTraktoru decode(String text) {
        ZnackaTraktoru typ = null;
        switch (text.trim().toLowerCase(new Locale("cs", "CZ"))) {
            case "johndeer":
            case "john deer":
                typ = JOHN_DEER;
                break;
            case "zetor":
                typ = ZETOR;
                break;
            case "fendt":
                typ = FENDT;
                break;
            case "lkt":
                typ = LKT;
                break;
            case "svoboda":
                typ = SVOBODA;
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
