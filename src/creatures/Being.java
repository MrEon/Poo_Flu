package creatures;
import graph.*;
import virus.Virus;

import java.util.List;

/**
 * Created by user on 03/12/15.
 */
public abstract class Being {
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;


    //The virus an animal carries
    private Virus virus = null;

    /**
     * Create a new animal at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Being(Field field, Location location) {
        alive = true;
        this.field = field;
        setLocation(location);
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     *
     * @param newBeings A list to receive newly born animals.
     */
    abstract public void act(List<Being> newBeings);

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }


    /**
     * Return the animal's location.
     *
     * @return The animal's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     *
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the animal's field.
     *
     * @return The animal's field.
     */
    protected Field getField() {
        return field;
    }

    /**
     * Return's the animal's virus
     * @return the animal's virus, null if not infected
     */
    public Virus getVirus() {
        return virus;
    }
}
