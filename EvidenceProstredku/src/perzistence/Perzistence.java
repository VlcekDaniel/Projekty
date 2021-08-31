package perzistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import kolekce.LinSeznam;
import kolekce.Seznam;
import util.Klon;

/**
 *
 * @author karel@simerda.cz
 */
public final class Perzistence implements Serializable {

    private Perzistence() {

    }

    public static <T extends Klon> void uloz(String jmenoSouboru, Seznam<T> seznam)
            throws IOException {
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(jmenoSouboru));
//        out.writeInt(seznam.size());
//        Iterator<T> it = seznam.iterator();
//        while (it.hasNext()) {
//            out.writeObject(it.next());           
//        }
         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(jmenoSouboru));
         out.writeObject(seznam);
         out.close();
    }

    public static <T extends Klon> LinSeznam<T> nacti(String jmenoSouboru, Seznam<T> seznam) throws FileNotFoundException, IOException, ClassNotFoundException {
          
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(jmenoSouboru));
        return (LinSeznam<T>)in.readObject();

//        ObjectInputStream in = new ObjectInputStream(new FileInputStream(jmenoSouboru));
//        int pocet = in.readInt();
//        for (int i = 0; i < pocet; i++) {
//            seznam.vlozNaKonec((T) in.readObject());            
//        }
//        return seznam;
    } 
}
