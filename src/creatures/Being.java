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
    protected boolean alive;
    // The animal's field.
    protected Field field;
    // The animal's position in the field.
    protected Location location;
    //the virus infection that plagues the creatures
    protected Virus virus  = Virus.NONE;
    //Advancement of the malady
    protected State state = State.Healthy;
    protected int daysOfInfection = 0;

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

    public void infect(Being neighbour){
        if(!virus.equals(Virus.NONE))
            return;
        if(!neighbour.getVirus().equals(Virus.NONE) && this.virusMatch(neighbour)){
            Random rnd = new Random();
            int infected  = rnd.nextInt(20);
            if(neighbour.getVirus().getContagionRate()>= infected){
                this.virus =neighbour.getVirus();
                state = State.Sick;
                daysOfInfection = 0;
            }
        }

    }

    public void age(){
        switch (state){
            case Sick:
                sick();
                daysOfInfection++;
                break;
            case Contagious:
                contagious();
                daysOfInfection++;
                break;
            case Recovering:
                recovering();
                daysOfInfection++;
                break;
            default:
                daysOfInfection++;
                break;
        }

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
            setDead();
        }
    }

    protected void recovering(){
        if(daysOfInfection>=3){
            state = State.Healthy;
            daysOfInfection = 0;
        }
    }

    public void setVirus(Virus virus) {
        this.virus = virus;
    }

    /**
     * Beware of this class, to be reviewed first before submition
     * @param b
     * @return true if two beings can infect each other
     */
    private boolean virusMatch(Being b){
        if(this.getClass().equals(b.getClass()))
            return true;
        if(this instanceof Duck && b instanceof Chicken)
            return true;
        if(this instanceof Chicken && b instanceof Duck)
            return true;
        if(this instanceof Human)
            return true;

        return false;
    }

    public State getState() {
        return state;
    }
}
