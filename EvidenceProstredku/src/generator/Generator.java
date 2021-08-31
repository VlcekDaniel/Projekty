package generator;

import java.util.Random;
import kolekce.LinSeznam;
import kolekce.Seznam;
import prostredky.Dodavka;
import prostredky.DodavkaTyp;
import prostredky.DopravniProstredek;
import prostredky.NakladniAutomobil;
import prostredky.OsobniAutomobil;
import prostredky.ProstredekTyp;
import prostredky.Traktor;
import prostredky.ZnackaTraktoru;
import util.Barva;

/**
 *
 * @author kasi0004
 */
public final class Generator {

    private static final Random random = new Random();

   
    private Generator() {
    }

    public static Seznam<DopravniProstredek> generuj(int pocetProstredku) {
        Seznam seznam = new LinSeznam();
        for (int i = 0; i < pocetProstredku; i++) {      
          seznam.vlozNaKonec(generujProstredek());
        }
        return seznam;
    }

    private static DopravniProstredek generujProstredek() {
        DopravniProstredek prostredek = null;
        int typ = random.nextInt(ProstredekTyp.getProstredky().length);
        switch (ProstredekTyp.values()[typ]) {
            case OSOBNI_AUTOMOBIL:
                return new OsobniAutomobil(novaSPZ(), novaBarva(), novyPocetSedadel(), novaHmotnost(2000),novyVykon());
            case DODAVKA:
                return new Dodavka(novaSPZ(), novaHmotnost(typ), novyTypDodavky(),novaVyska());
            case NAKLADNI_AUTMOBIL:
                return new NakladniAutomobil(novaSPZ(),1,novaHmotnost(typ),novaNosnost(),novyRokVyroby());
            case TRAKTOR:
                return new Traktor(novaSPZ(), novaHmotnost(typ), novyTahTraktoru(),novaZnackaTraktoru());
        }
        return prostredek;
    }

    public static String novaSPZ() {
        StringBuilder spz = new StringBuilder();
        spz.append(random.nextInt(10))
                .append((char) ('A' + random.nextInt(27)))
                .append((char) ('0' + random.nextInt(10)))
                //  .append(" ")
                .append((char) ('0' + random.nextInt(10)))
                .append((char) ('0' + random.nextInt(10)))
                //                .append("-")
                .append((char) ('0' + random.nextInt(10)))
                .append((char) ('0' + random.nextInt(10)));

        return spz.toString();
    }

    public static Barva novaBarva() {
        int barva = random.nextInt(Barva.values().length);
        return Barva.values()[barva];
    }

    public static int novyPocetSedadel() {
        return random.nextInt((8 - 2) + 1) + 2;
    }

    public static float novaHmotnost(int max) {
         return random.nextInt((2000 - 200) + 1) + 200;
    }

    public static DodavkaTyp novyTypDodavky() {
        int dodavka = random.nextInt(DodavkaTyp.values().length);
        return DodavkaTyp.values()[dodavka];
    }

    public static float novaNosnost() {               
         return random.nextInt((4000 - 1000) + 1) + 1000;
    }

    public static long novyTahTraktoru() {
         return random.nextInt((1000000 - 10000) + 1) + 10000;
    }

     private static int novyVykon() {
        return random.nextInt((200 - 20) + 1) + 20;
    }
     
    private static int novyRokVyroby() {
         return random.nextInt((2020 - 1950) + 1) + 1950;
    }
    
     public static ZnackaTraktoru novaZnackaTraktoru() {
        int znacka = random.nextInt(ZnackaTraktoru.values().length);
        return ZnackaTraktoru.values()[znacka];
    }    
     
    private static int novaVyska() {
         return random.nextInt((250 - 200) + 1) + 200;
    } 
}
