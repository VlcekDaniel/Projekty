package util;

/**
 *
 * @author karel@simerda.cz
 * @param <E>
 */
public interface Klon<E> extends Cloneable {
    E clone() throws CloneNotSupportedException;
}
