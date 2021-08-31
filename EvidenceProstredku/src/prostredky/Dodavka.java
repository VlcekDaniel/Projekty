package prostredky;

/**
 *
 * @author karel@simerda.cz
 */
public class Dodavka extends DopravniProstredek{

    private DodavkaTyp typ;
    private int vyska;

    public Dodavka() {
        super(ProstredekTyp.DODAVKA);
    }

    public Dodavka(String spz, float hmotnost, DodavkaTyp typ,int vyska) {
        super(ProstredekTyp.DODAVKA, spz, hmotnost);
        this.typ = typ;
        this.vyska = vyska;
    }
   
    public DodavkaTyp getTyp() {
        return typ;
    }

    public void setTyp(DodavkaTyp typ) {
        this.typ = typ;
    }

    public int getVyska() {
        return vyska;
    }

    public void setVyska(int vyska) {
        this.vyska = vyska;
    }

    @Override
    public String toString() {
        return super.toString() + ", typ=" + typ.getNazev()+ ", vyska=" + getVyska();
    }
}
