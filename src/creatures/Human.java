package creatures;

import graph.Field;
import graph.Location;

import java.util.Random;

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
        Random rnd = new Random();
        int death = rnd.nextInt(daysOfInfection);
        if(death>virus.getMortalityRate()){
            state = State.Dead;
            daysOfInfection =0;
            setDead();
        }else{
            death = rnd.nextInt(100);
            System.out.println(death);
            if(death<10){
                state = State.Recovering;
                daysOfInfection = 0;
            }
        }
    }
}
