package creatures;

import graph.Field;
import graph.Location;

/**
 * Animal
 *
 * @author Plague Inc.
 * @version 2015
 */
public abstract class Animal extends Being {

    public Animal(Field field, Location location) {
        super(field, location);
    }

}
