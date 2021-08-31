/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprava;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import kolekce.KolekceException;
import kolekce.LinSeznam;
import kolekce.Seznam;
import prostredky.DopravniProstredek;
import prostredky.Klon;

/**
 *
 * @author Dan
 * @param <E>
 */
public class SpravaProstredku<E extends Klon> implements Ovladani<E>,Serializable {

    private LinSeznam seznam;
    private Comparator komparator;

    @Override
    public void vytvorSeznam(Supplier<Seznam<E>> supplier) {
        seznam = (LinSeznam<E>) supplier.get();
    }

    @Override
    public void vytvorSeznam(Function<Integer, Seznam<E>> funtion, int size) throws KolekceException {
        seznam = (LinSeznam<E>) funtion.apply(size);
    }

    @Override
    public void nastavKomparator(Comparator<? super E> comparator) {
        Objects.requireNonNull(comparator);
        this.komparator = comparator;
    }

    @Override
    public void vlozPolozku(E data) throws NullPointerException {
        seznam.vlozNaKonec(data);
    }

    @Override
    public E najdiPolozku(E klic) {
        return (E) seznam.stream().filter(o1 -> komparator.compare(o1, klic) == 0).findFirst().get();
    }

    @Override
    public void prejdiNaPrvniPolozku() {
        try {
            seznam.nastavPrvni();
        } catch (KolekceException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void prejdiNaPosledniPolozku() {
        try {
            seznam.nastavPosledni();
        } catch (KolekceException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void prejdiNaDalsiPolozku() {
        try {
            seznam.dalsi();
        } catch (KolekceException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void prejdiNaPredchoziPolozku() {
        try {
            seznam.dejPredAktualnim();
        } catch (NoSuchElementException | KolekceException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public E nastavAktualniPolozku(E klic) {
        if (!seznam.jePrazdny()) {
            try {
                seznam.nastavPrvni();
                while (true) {
                    E prvek = (E) seznam.dejAktualni();
                    if (komparator.compare(prvek, klic) == 0) {
                        return prvek;
                    }
                    seznam.dalsi();
                }
            } catch (KolekceException ex) {
                Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public E vyjmiAktualniPolozku() {
        try {
            return (E) seznam.odeberAktualni();
        } catch (KolekceException | NoSuchElementException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public E dejKopiiAktualniPolozky() {
        try {
            return (E) ((E) seznam.dejAktualni()).clone();
        } catch (NoSuchElementException | KolekceException | CloneNotSupportedException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void editujAktualniPolozku(Function<E, E> editor) {
        try {
            editor.apply((E) seznam.dejAktualni());
        } catch (NoSuchElementException | KolekceException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ulozDoSouboru(String soubor) {
        try {
            perzistence.Perzistence.uloz(soubor, seznam);
        } catch (IOException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nactiZeSouboru(String soubor) {
        try {
            seznam = perzistence.Perzistence.nacti(soubor, seznam);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void vypis(Consumer<E> writer) {
        seznam.stream().forEach(writer);
    }

    @Override
    public void nactiTextSoubor(String soubor, Function<String, E> mapper) {
        seznam.zrus();
        try {
            Files.lines(Paths.get(soubor), StandardCharsets.UTF_8)
                    .filter(t -> t != null).map(mapper)
                    .forEach((E t) -> seznam.vlozNaKonec(t));
        } catch (IOException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ulozTextSoubor(String soubor, Function<E, String> mapper) {
        Path file = Paths.get(soubor);
        ArrayList<String> list = new ArrayList<>();
        seznam.stream().map(mapper).forEach(dp -> list.add(dp.toString()));

        try {
            Files.write(file, list, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(SpravaProstredku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void generujData(int pocetProstredku) {
        seznam = (LinSeznam) generator.Generator.generuj(pocetProstredku);
    }

    @Override
    public int dejAktualniPocetPolozek() {
        return seznam.size();
    }

    @Override
    public void zrus() {
        seznam.zrus();
    }

    @Override
    public Iterator<E> iterator() {
        return seznam.iterator();
    }

}
