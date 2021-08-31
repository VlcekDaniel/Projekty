package kolekce;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinSeznam<T> implements Seznam<T>,Serializable{

    private Prvek prvni;
    private Prvek posledni;
    private Prvek aktualni;
    private int mohutnost;

    public LinSeznam() {
        this.prvni = null;
        this.posledni = null;
        this.aktualni = null;
        this.mohutnost = 0;
    }

    public class Prvek  implements Serializable,Cloneable{

        T obsah;
        Prvek dalsi;

        public Prvek(T obsah) {
            this.obsah = obsah;
            this.dalsi = null;
        }
    }
    
    @Override
    public void nastavPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        aktualni = prvni;
    }

    @Override
    public void nastavPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        aktualni = posledni;
    }

    @Override
    public boolean dalsi() throws KolekceException {
       if(aktualni==null){
           throw new KolekceException();
       }   
        if(jePrazdny()||prvni==posledni){
            return false;
        }
        aktualni = aktualni.dalsi;
        if(aktualni!=null){
            return true;
        }
        return false;
    }
    
    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        aktualni = null;
        mohutnost = 0;
    }

    @Override
    public boolean jePrazdny() {
        boolean jePrazdny = false;
        if (mohutnost == 0) {
            jePrazdny = true;
        }
        return jePrazdny;
    }

    @Override
    public int size() {
        return mohutnost;
    }

    @Override
    public void vlozPrvni(T data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("No data");
        }
        Prvek novy = new Prvek(data);
        if (jePrazdny()) {
            posledni = novy;
            prvni = novy;
            mohutnost++;
        } else {
            novy.dalsi = prvni;
            prvni = novy;
            mohutnost++;
        }
    }

    @Override
    public void vlozNaKonec(T data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("No data");
        }
        Prvek novy = new Prvek(data);
        if (jePrazdny()) {
            posledni = novy;
            prvni = novy;
            mohutnost++;
        } else {
            posledni.dalsi = novy;
            novy.dalsi = null;
            posledni = novy;
            mohutnost++;
        }
    }

    @Override
    public void vlozZaAktualni(T data) throws KolekceException, NullPointerException {
        if (data == null) {
            throw new NullPointerException("No data");
        }
        if (aktualni == null) {
            throw new KolekceException("Musi být určen aktuální prvek");
        }
        Prvek novy = new Prvek(data);
        if (aktualni == posledni) {
            posledni.dalsi = novy;
            posledni = novy;
            mohutnost++;
        } else {
            novy.dalsi = aktualni.dalsi;
            aktualni.dalsi = novy;
            mohutnost++;
        }
    }

//    @Override
//    public void vlozPredAktualni(T data) throws KolekceException{
//         if (aktualni == null) {
//            throw new KolekceException("Musi být určen aktuální prvek");
//         }
//        
//        Prvek novy = new Prvek(data);
//        if (aktualni == prvni) {
//            novy.dalsi = prvni;
//            prvni = novy;
//            mohutnost++;
//        } else {
//         Prvek aktualniTemp = aktualni;
//            aktualni = prvni;
//            while(aktualni.dalsi!=aktualniTemp){
//                aktualni = aktualni.dalsi;
//            }       
//            aktualni.dalsi = novy;
//            novy.dalsi = aktualniTemp;
//        }
//    }

    @Override
    public T dejAktualni() throws NoSuchElementException, KolekceException {
        if (jePrazdny()) {
            throw new NoSuchElementException("Seznam je prázdný");
        }
        if (aktualni == null) {
            throw new KolekceException("Musi být určen aktuální prvek");
        }
        return aktualni.obsah;
    }

    @Override
    public T dejPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prázdný");
        }
        aktualni = prvni;
        return prvni.obsah;
    }

    @Override
    public T dejPosledni() throws NoSuchElementException {
        if (jePrazdny()) {
            throw new NoSuchElementException("Seznam je prázdný");
        }
        aktualni = posledni;
        return posledni.obsah;
    }

    @Override
    public T dejZaAktualnim() throws NoSuchElementException, KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Musi být určen aktuální prvek");
        }
        if (jePrazdny() || aktualni.dalsi == null) {
            throw new NoSuchElementException("Není následník");
        }
        aktualni = aktualni.dalsi;
        return aktualni.obsah;
    }

    @Override
    public T dejPredAktualnim() throws NoSuchElementException, KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Musi být určen aktuální prvek");
        }
        Prvek aktualniTemp = aktualni;
        aktualni = prvni;
            while(aktualni.dalsi!=aktualniTemp){
                aktualni = aktualni.dalsi;
            }       
            aktualniTemp = aktualni;
            aktualni = aktualni.dalsi;
        return aktualniTemp.obsah;
    }

    @Override
    public T odeberAktualni() throws KolekceException, NoSuchElementException {
        if (jePrazdny()) {
            throw new NoSuchElementException("Seznam je prázdný");
        }
        if (aktualni == null) {
            throw new KolekceException("Musi být určen aktuální prvek");
        }
        
        Prvek aktualniTemp = aktualni;
        T data = aktualni.obsah;
        
        //pokud aktualni bude prvni
        if (aktualni == prvni) {
            prvni = prvni.dalsi;
            aktualni = prvni;
        } else {
            //pokud aktualni bude uprostred, nebo poslední, musíme najít předchozí prvek a změnit jeho "dalsi" na prvek co byl za aktualnim
            aktualni = prvni;
            while(aktualni.dalsi!=aktualniTemp){
                aktualni = aktualni.dalsi;
            }
            aktualni.dalsi = aktualniTemp.dalsi;
        }
        mohutnost--;
        return data;
    }

    @Override
    public T odeberZaAktualnim() throws KolekceException, NoSuchElementException {
        if (jePrazdny()) {
            throw new NoSuchElementException("Seznam je prázdný");
        }
        if (aktualni.dalsi == null) {
            throw new KolekceException("Musi být určen aktuální prvek");
        }
        if (aktualni.dalsi == posledni) {

            T data = aktualni.dalsi.obsah;
            return data;
        }
        T data = aktualni.dalsi.obsah;
        aktualni.dalsi = aktualni.dalsi.dalsi;
        
        mohutnost--;
        return data;
    }

    @Override
    public T odeberPredAktualnim() throws KolekceException, NoSuchElementException {
        if (jePrazdny()) {
            throw new NoSuchElementException("Seznam je prázdný");
        }
        if (aktualni == null) {
            throw new KolekceException("Musi být určen aktuální prvek");
        }
        
        Prvek aktualniTemp = aktualni;
        aktualni = prvni;
            while(aktualni.dalsi!=aktualniTemp){
                aktualni = aktualni.dalsi;
            }       
            Prvek aktualniTemp2 = aktualni;
            while(aktualni.dalsi!=aktualniTemp2){
                aktualni = aktualni.dalsi;
            }
        aktualni.dalsi=aktualniTemp;
        mohutnost--;
        return aktualniTemp2.obsah;

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Prvek index = prvni;

            @Override
            public boolean hasNext() {
                return index != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T data = index.obsah;
                    index = index.dalsi;
                    return data;
                }
                throw new NoSuchElementException();
            }

            public Prvek getIndex() {
                return index;
            }
        };
    }
    
//    @Override
//    public void vypisSeznam() throws KolekceException{
//        nastavPrvni();
//        for (int i = 0; i < mohutnost; i++) {
//            if(iterator().hasNext()){
//                System.out.println(aktualni.obsah);
//                dalsi();
//            }
//        }
//    }
}
