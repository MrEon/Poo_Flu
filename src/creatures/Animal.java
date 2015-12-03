package creatures;
import graph.*;
/**
 * A class representing shared characteristics of animals.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Animal extends Being{

    public Animal(Field field, Location location) {
        super(field, location);
    }
}
