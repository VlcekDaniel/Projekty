package prostredky;

/**
 *
 * @author karel@simerda.cz
 */
public abstract class Automobil extends DopravniProstredek {

    public Automobil(ProstredekTyp typ, float hmotnost, String spz) {
        super(typ, spz, hmotnost);
    }

    public Automobil(ProstredekTyp typ) {
        super(typ);
    }

}
