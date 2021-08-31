
package kolekce;

/**
 * 
 * 
 *
 * @author karel@simerda.cz
 */
public class KolekceException extends Exception {

    /**
     * Creates a new instance of <code>NewException</code> without detail
     * message.
     */
    public KolekceException() {
    }

    /**
     * Constructs an instance of <code>NewException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public KolekceException(String msg) {
        super(msg);
    }
}
