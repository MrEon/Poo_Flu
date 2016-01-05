package creatures;

import graph.Field;
import graph.Location;

import java.util.Random;

/**
 * Human
 *
 * @author SI3 G3 - Groupe numero 3 : Boucher-Thouveny & Lafaurie & Monier
 * @version 2015
 */
public class Human extends Being {

    public Human(Field field, Location location) {
        super(field, location);
    }

    @Override
    protected void actContagious() {
        Random rnd = new Random();
        int death = rnd.nextInt(daysSinceInfected);
        if (death > virus.getMortalityRate()) {
            state = State.Dead;
            daysSinceInfected = 0;
            setDead();
        } else {
            death = rnd.nextInt(100);
            if (death < 10) {
                state = State.Recovering;
                daysSinceInfected = 0;
            }
        }
    }
}
