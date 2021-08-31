package prostredky;

/**
 *
 * @author karel@simerda.cz
 */
public class Traktor extends DopravniProstredek{

    private long tah;
    private ZnackaTraktoru znacka;

    public Traktor() {
        super(ProstredekTyp.TRAKTOR);
    }

    public Traktor(String spz, float hmotnost, long tah,ZnackaTraktoru znacka) {
        super(ProstredekTyp.TRAKTOR, spz, hmotnost);
        this.tah = tah;
        this.znacka = znacka;
    }

    public long getTah() {
        return tah;
    }

    public void setTah(long tah) {
        this.tah = tah;
    }

    public ZnackaTraktoru getZnacka() {
        return znacka;
    }

    public void setZnacka(ZnackaTraktoru znacka) {
        this.znacka = znacka;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", tah=" + tah+ ", znacka=" + znacka;
    }

}
