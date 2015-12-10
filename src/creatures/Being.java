package creatures;
import graph.Field;
import graph.Location;
import virus.Virus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Being
 *
 * @author Plague Inc.
 * @version 2015
 */
public abstract class Being {
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    //the virus infection that plagues the creatures
    private Virus virus  = null;
    //Advancement of the malady
    private State state;
    private int daysOfInfection = 0;

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

    public void infect(Being being){
        if(!virus.equals(null))
            return;
        if(!being.getVirus().equals(null)){
            Random rnd = new Random();
            int infected  = rnd.nextInt(10);
            if(being.getVirus().getContagionRate()>= infected){
                this.virus =being.getVirus();
                daysOfInfection = 0;
            }
        }

    }

    public void age(){
        switch (state){
            case Sick:
                sick();
                break;
            case Contagious:
                contagious();
                break;
        }
        daysOfInfection++;
    }
    protected void sick(){
        if( daysOfInfection>virus.getIncubation()){
            state = State.Contagious;
            daysOfInfection = 0;
        }
    }

    protected void contagious(){
        Random rnd = new Random();
        int death = rnd.nextInt(daysOfInfection);
        if(death>virus.getMortalityRate()){
            state = State.Dead;
            daysOfInfection =0;
        }
    }
}
