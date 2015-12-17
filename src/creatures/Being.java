package creatures;

import graph.Field;
import graph.Location;
import virus.Virus;

import java.util.Random;

/**
 * Being
 *
 * @author Plague Inc.
 * @version 2015
 */
public abstract class Being {
    // The animal's field.
    protected Field field;
    // The animal's position in the field.
    protected Location location;
    //the virus infection that plagues the creatures
    protected Virus virus;
    //Advancement of the malady
    protected State state;
    protected int daysSinceInfected = 0;

    /**
     * Create a new animal at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Being(Field field, Location location) {
        state = State.Healthy;
        virus = Virus.NONE;
        this.field = field;
        setLocation(location);
    }

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    public boolean isAlive() {
        return state != State.Dead;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        state = State.Dead;
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
    public Location getLocation() {
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

    public Virus getVirus() {
        return virus;
    }

    /**
     * Return the animal's field.
     *
     * @return The animal's field.
     */

    protected Field getField() {
        return field;
    }

    public void infect(Being neighbour) {
        if (virus.equals(Virus.NONE)) {
            if (!neighbour.getVirus().equals(Virus.NONE) && this.canByInfectedBy(neighbour)) {
                Random rnd = new Random();
                int infected = rnd.nextInt(20);
                if (neighbour.getVirus().getContagionRate() >= infected) {
                    this.virus = neighbour.getVirus();
                    state = State.Sick;
                    daysSinceInfected = 0;
                }
            }
        }
    }

    public void age() {
        switch (state) {
            case Sick:
                actSick();
                daysSinceInfected++;
                break;
            case Contagious:
                actContagious();
                daysSinceInfected++;
                break;
            case Recovering:
                actRecovering();
                daysSinceInfected++;
                break;
            default:
                daysSinceInfected++;
                break;
        }

    }

    protected void actSick() {
        if (daysSinceInfected > virus.getIncubation()) {
            state = State.Contagious;
            daysSinceInfected = 0;
        }
    }

    protected void actContagious() {
        Random rnd = new Random();
        int death = rnd.nextInt(daysSinceInfected);
        if (death > virus.getMortalityRate()) {
            state = State.Dead;
            daysSinceInfected = 0;
            setDead();
        }
    }

    protected void actRecovering() {
        if (daysSinceInfected >= 3) {
            state = State.Healthy;
            daysSinceInfected = 0;
        }
    }

    public void setVirus(Virus virus) {
        this.virus = virus;
    }

    /**
     * Beware of this class, to be reviewed first before submition
     *
     * @param b : another being
     * @return true if two beings can infect each other
     */
    private boolean canByInfectedBy(Being b) {
        if (this.getClass().equals(b.getClass())) {
            return true;
        } else if (this instanceof Duck && b instanceof Chicken) {
            return true;
        } else if (this instanceof Chicken && b instanceof Duck) {
            return true;
        } else if (this instanceof Human) {
            return true;
        }
        return false;
    }

    public State getState() {
        return state;
    }

    public void move() {
        Location oldLocation = getLocation();
        if (field != null) {
            Location newLocation = field.freeAdjacentLocation(oldLocation);
            if (newLocation != null) {
                setLocation(newLocation);
            }
        }
    }

}
