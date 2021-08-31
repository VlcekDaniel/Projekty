package command;

import java.io.IOException;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import kolekce.KolekceException;
import sprava.SpravaProstredku;
import prostredky.DopravniProstredek;
import kolekce.KolekceException;
import kolekce.LinSeznam;
import prostredky.Automobil;
import prostredky.Dodavka;
import prostredky.DodavkaTyp;
import prostredky.DopravniProstredekKlic;
import prostredky.Editor;
import prostredky.NakladniAutomobil;
import util.Barva;
import prostredky.OsobniAutomobil;
import prostredky.ProstredekTyp;
import prostredky.Traktor;
import prostredky.ZnackaTraktoru;

/**
 * Cílem je a) využívat Java 8 lambda výrazy a datové streams b) návrhový vzor
 * Fasada (viz. popis v rozhraní Ovladani)
 *
 * 1) Použití třídy LinSeznam z předchozího cvičení do package LinSeznam 2)
 * Vytvoř třídu SpravaProstredku implementující rozhraní Ovladani v package
 * sprava - co nejvíce využívat (java 8) lambda výrazy a datové streams 3)
 * Použití třídy Generator z předchozího cvičení v package generator 4) Použití
 * třídy Perzistence z předchozího cviření v package perzistence 5) Můžete
 * využít i třídy Keyboard z minulého cvičení v package command 6)
 *
 * Rozšíření/úprava třídy CommandLineMain z předchozího cvičení
 *
 * a) bude využívat třídy SpravaProstredku nikoli LinSeznam
 *
 * b) v rámci konzolového rozhraní bude umožněno provádět následující příkazy
 *
 * help, h - výpis příkazů novy,no - vytvoř novou instanci a vlož dopravního
 * prostředku za aktuální najdi,na,n - najdi v seznamu dopravní prostředek podle
 * státní poznávací značky dej - zobraz aktuální dopravní prostředk v seznamu
 * edituj,edit - edituj aktuální dopravní prostředek v seznamu vyjmi - vyjmi
 * aktuální dopravní prostředek ze seznamu prvni,pr - nastav jako aktuální první
 * dopravní prostředek v seznamu dalsi,da - přejdi na další dopravni prostředek
 * posledni,po - přejdi na poslední dopravní prostředek pocet - zobraz počet
 * položek v seznamu obnov - obnov seznam dopravních prostředků z binárního
 * souboru zalohuj - zalohuj seznam dopravních prostředků do binárního souboru
 * vypis - zobraz seznam dopravních prostředků nactitext,nt- nacti seznam
 * dopravních prostředků z textového souboru uloztext,ut - ulož seznam
 * dopravních prostředků do textového souboru generuj,g - generuj náhodně
 * dopravní prostředky pro testování zrus - zruš všechny dopravní prostředky v
 * seznamu exit - ukončení programu
 *
 * c) příkaz novy bude dále umožňovat uživateli vybrat jaký dopravní prostředek
 * chce vytvorit oa - osobní auto na - nákladní auto do - dodávka tr - traktor
 *
 * d) příkaz najdi bude dále umožňovat uživateli vybrat komparátor id -
 * porovnání podle ID dopravního prostředku spz - porovnání podle státní
 * poznávací značky (SPZ)
 *
 *
 * @author karel@simerda.cz
 */
public class CommandLineMain {

    private static final String ZALOHA_FILE_NAME = "zaloha.bin";
    private static final String ZALOHA_TEXTFILE_NAME = "zaloha.txt";
    private static final String ZALOHA_TEXTFILETEST_NAME = "zaloha.txt";
    private static final Function<DopravniProstredek, DopravniProstredek> editor = new Editor();
    private static CommandLineMain instance;
    private SpravaProstredku<DopravniProstredek> spravce;

    private final Comparator<DopravniProstredek> komparatorSPZ = (o1, o2) -> {
        return o1.getSpz().compareTo(o2.getSpz());
    };
    private final Comparator<DopravniProstredek> komparatorID = (o1, o2) -> {
        return o1.getId() - o2.getId();
    };

    public CommandLineMain() {
        spravce = new SpravaProstredku();
        spravce.vytvorSeznam(LinSeznam::new);
        spravce.nastavKomparator(komparatorSPZ);
        run();
    }

    public static void main(String[] args) {
        instance = new CommandLineMain();
        instance.run();
        System.out.println("Konec programu");
    }

    private void run() {
        String cmd;
        do {

            cmd = Keyboard.getStringItem("Zadej příkaz: ");
            switch (cmd) {
                case "help":
                case "h":
                    vypisHelp();
                    break;
                case "novy":
                case "no":
                    DopravniProstredek dp = vytvorNovy();
                    spravce.vlozPolozku(dp);
                    break;
                case "najdi":
                case "na":
                case "n":
                    String spz = Keyboard.getStringItem("Zadejte SPZ: ");
                    DopravniProstredek klic = new DopravniProstredekKlic(spz);
                    System.out.println((spravce.najdiPolozku(klic).toString()));
                    break;
                case "dej":
                case "d":
                    System.out.println(spravce.dejKopiiAktualniPolozky().toString());
                    break;
                case "edituj":
                case "edit":
                    spravce.editujAktualniPolozku(editor);
                    break;
                case "generuj":
                case "g":
                    System.out.println("Generování dat...");
                    spravce.generujData(10);
                    break;
                case "vyjmi":
                    spravce.vyjmiAktualniPolozku();
                    break;
                case "prvni":
                case "pr":
                    spravce.prejdiNaPrvniPolozku();
                    break;
                case "dalsi":
                case "da":
                    spravce.prejdiNaDalsiPolozku();
                    break;
                case "posledni":
                case "po":
                    spravce.prejdiNaPosledniPolozku();
                    break;
                case "pocet":
                    System.out.println(spravce.dejAktualniPocetPolozek());
                    break;
                case "zalohuj":
                    spravce.ulozDoSouboru(ZALOHA_FILE_NAME);
                    break;
                case "obnov":
                    spravce.nactiZeSouboru(ZALOHA_FILE_NAME);
                    break;
                case "vypis":
                case "v":
                    spravce.stream().forEach((t) -> {
                        System.out.println(t);
                    });
                    break;
                case "nactitext":
                case "nt":
                    spravce.nactiTextSoubor(ZALOHA_TEXTFILETEST_NAME, mapperInput);
                    break;
                case "uloztext":
                case "ut":
                    spravce.ulozTextSoubor(ZALOHA_TEXTFILETEST_NAME, mapperOutput);
                    break;
                case "zrus":
                    spravce.zrus();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Neznamy prikaz:" + cmd.length());
            }
        } while (true);
    }

    private static void vypisHelp() {
        System.out.println(""
                + "help, h     - výpis příkazů\n"
                + "novy,no     - vytvoř novou instanci a vlož dopravního prostředku za aktuální\n"
                + "najdi,na,n  - najdi v seznamu dopravní prostředek podle státní poznávací značky\n"
                + "dej,de      - zobraz aktuální dopravní prostředk v seznamu\n"
                + "edituj,edit - edituj aktuální dopravní prostředek v seznamu\n"
                + "vyjmi       - vyjmi aktuální dopravní prostředek ze seznamu\n"
                + "prvni,pr    - nastav jako aktuální první dopravní prostředek v seznamu\n"
                + "dalsi,da    - přejdi na další dopravni prostředek\n"
                + "posledni,po - přejdi na poslední dopravní prostředek\n"
                + "pocet       - zobraz počet položek v seznamu\n"
                + "obnov       - obnov seznam dopravních prostředků z binárního souboru\n"
                + "zalohuj     - zalohuj seznam dopravních prostředků do binárního souboru\n"
                + "vypis,v     - zobraz seznam dopravních prostředků\n"
                + "nactitext,nt- nacti seznam dopravních prostředků z textového souboru\n"
                + "uloztext,ut - ulož seznam dopravních prostředků do textového souboru\n"
                + "generuj,g   - generuj náhodně dopravní prostředky pro testování\n"
                + "zrus        - zruš všechny dopravní prostředky v seznamu\n"
                + "exit        - ukončení programu"
        );
    }

    private static final Function<String, DopravniProstredek> mapperInput = (line) -> {
        DopravniProstredek prostredek = null;
        if (line.length() == 0) {
            return prostredek;
        }
        String[] items = line.split(",");
        String spz = items[1].trim();
        float hmotnost = Float.valueOf(items[2]);
        switch (items[0].trim().toLowerCase(Locale.US)) {
            case "oa":
                int pocetSedadel = Integer.valueOf(items[3].trim());
                Barva barva = Barva.decode(items[4].trim());
                int vykon = Integer.valueOf(items[5].trim());
                prostredek = new OsobniAutomobil(spz, barva, pocetSedadel, hmotnost, vykon);
                break;
            case "do":
                DodavkaTyp typ = DodavkaTyp.decode(items[3].trim());
                int vyska = Integer.valueOf(items[4].trim());
                prostredek = new Dodavka(spz, hmotnost, typ,vyska);
                break;
            case "na":
                float nosnost = Float.valueOf(items[4].trim());
                int pocetNaprav = Integer.valueOf(items[2].trim());
                hmotnost = Float.valueOf(items[3]);
                int rokVyroby = Integer.valueOf(items[5].trim());
                prostredek = new NakladniAutomobil(spz, pocetNaprav, hmotnost, nosnost,rokVyroby);
                break;
            case "tr":
                int tah = Integer.valueOf(items[3].trim());
                ZnackaTraktoru znacka = ZnackaTraktoru.decode(items[4].trim());
                prostredek = new Traktor(spz, hmotnost, tah,znacka);
                break;
        }
        return prostredek;
    };

    private static final Function<DopravniProstredek, String> mapperOutput = (dp) -> {
        String text = "";
        switch (dp.getType().toString()) {
            case "osobní automobil":
                OsobniAutomobil oa = (OsobniAutomobil) dp;
                text = "oa" + ", " + oa.getSpz() + ", " + oa.getHmotnost() + ", " + oa.getPocetSedadel() + ", " + oa.getBarva()+ ", " + oa.getVykon();
                break;
            case "dodávka":
                Dodavka d = (Dodavka) dp;
                text = "do" + ", " + d.getSpz() + ", " + d.getHmotnost() + ", " + d.getTyp()+ ", " + d.getVyska();
                break;
            case "nákladní automobil":
                NakladniAutomobil na = (NakladniAutomobil) dp;
                text = "na" + ", " + dp.getSpz() + ", " + na.getPocetNaprav() + ", " + dp.getHmotnost() + ", " + na.getNosnost()+ ", " + na.getRokVyroby();
                break;
            case "traktor":
                Traktor tr = (Traktor) dp;
                text = "tr" + ", " + dp.getSpz() + ", " + dp.getHmotnost() + ", " + tr.getTah() + ", " + tr.getZnacka();
                break;
        }
        return text;
    };

    private DopravniProstredek vytvorNovy() {
        DopravniProstredek dp = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte Typ dopravního prostředku(oa,do,na,tr):");
        String typ = scanner.nextLine();
        String spz;
        Float hmotnost;
        Float nosnost;
        int pocetSedadel;
        int vykon;
        int rokVyroby;
        int vyska;
        switch (typ) {
            case "oa":
                spz = Keyboard.getStringItem("Zadejte SPZ(-------): ");
                String barvaString = Keyboard.getStringItem("Zadejte barvu(bílá, černá, modrá, zelená, červená): ");
                Barva barva;
                switch (barvaString) {
                    case "bílá":
                        barva = Barva.BILA;
                        break;
                    case "černá":
                        barva = Barva.CERNA;
                        break;
                    case "modrá":
                        barva = Barva.MODRA;
                        break;
                    case "zelená":
                        barva = Barva.ZELENA;
                        break;
                    case "červená":
                        barva = Barva.CEVENA;
                        break;
                    default:
                        barva = Barva.BILA;
                }

                pocetSedadel = Keyboard.getIntItem("Zadejte pocet sedadel: ", 0);
                hmotnost = Keyboard.getFloatItem("Zadejte hmotnost: ", 0);
                vykon = Keyboard.getIntItem("Zadejte vykon: ", 0);
                dp = new OsobniAutomobil(spz, barva, pocetSedadel, hmotnost, vykon);
                break;
            case "do":
                spz = Keyboard.getStringItem("Zadejte SPZ(A2A4567): ");
                hmotnost = Keyboard.getFloatItem("Zadejte hmotnost: ", 0);
                String dodavkaString = Keyboard.getStringItem("Zadejte typ Dodavky(dvojkabina,nástavba,valník): ");
                vyska = Keyboard.getIntItem("Zadejte vysku: ", 0);
                DodavkaTyp dodavkaTyp;
                switch (dodavkaString) {
                    case "dvojkabina":
                        dodavkaTyp = DodavkaTyp.DVOJ_KABINA;
                        break;
                    case "nástavba":
                        dodavkaTyp = DodavkaTyp.NASTAVBA;
                        break;
                    case "valník":
                        dodavkaTyp = DodavkaTyp.VALNIK;
                        break;
                    default:
                        dodavkaTyp = DodavkaTyp.VALNIK;
                        break;
                }
                dp = new Dodavka(spz, hmotnost, dodavkaTyp,vyska);
                break;
            case "na":
                spz = Keyboard.getStringItem("Zadejte SPZ(A2A4567): ");
                pocetSedadel = Keyboard.getIntItem("Zadejte pocet sedadel: ", 0);
                hmotnost = Keyboard.getFloatItem("Zadejte hmotnost:", 0);
                nosnost = Keyboard.getFloatItem("Zadejte nosnost:", 0);
                rokVyroby = Keyboard.getIntItem("Zadejte rok vyroby:", 0);
                dp = new NakladniAutomobil(spz, pocetSedadel, hmotnost, nosnost, rokVyroby);
                break;
            case "tr":
                spz = Keyboard.getStringItem("Zadejte SPZ(A2A4567): ");
                hmotnost = Keyboard.getFloatItem("Zadejte hmotnost: ", 0);
                pocetSedadel = Keyboard.getIntItem("Zadejte pocet sedadel: ", 0);
                String znackaString = Keyboard.getStringItem("Zadejte znacku traktoru(JohnDeer,Zetor,Fendt,LKT,Svoboda): ");
                ZnackaTraktoru znacka;
                switch (znackaString) {
                    case "JohnDeer":
                        znacka = ZnackaTraktoru.JOHN_DEER;
                        break;
                    case "Zetor":
                        znacka = ZnackaTraktoru.ZETOR;
                        break;
                    case "Fendt":
                        znacka = ZnackaTraktoru.FENDT;
                        break;
                    case "LKT":
                        znacka = ZnackaTraktoru.LKT;
                        break;
                    case "Svoboda":
                        znacka = ZnackaTraktoru.SVOBODA;
                        break;
                    default:
                        znacka = ZnackaTraktoru.JOHN_DEER;
                }
                dp = new Traktor(spz, hmotnost, pocetSedadel, znacka);
                break;
        }
        return dp;
    }
}
