package creatures;

import graph.Field;
import graph.Location;

/**
 * Human
 *
 * @author Plague Inc.
 * @version 2015
 */
public class Human extends Being {

    public Human(Field field, Location location) {
        super(field, location);
    }

    @Override
    protected void contagious(){
        //TODO
    }
}
