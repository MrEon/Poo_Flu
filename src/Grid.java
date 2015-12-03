import entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 30/11/15.
 */
public class Grid {
    public static final int nbrOfSick = 64;//Number of sick people at the start of the simulation
    private List<Entity> list = new ArrayList<>(1024);//1024 = 32*32
    private int step = 32;
    private Random rnd = new Random();

    public Grid() {
        new Grid(32);
    }

    public Grid(int size) {
        list = new ArrayList<>(size * size);
        step = size;
        for (int i = 0; i < step * step; i++) {//On rempli la liste
            switch (rnd.nextInt(4)) {
                case 0:
                    list.add(new Human());
                    break;
                case 1:
                    list.add(new Pig());
                    break;
                case 2:
                    list.add(new Duck());
                    break;
                case 3:
                    list.add(new Chicken());
                    break;
                default:
                    list.add(new Human());
            }
        }
        for (int i = nbrOfSick; i > 0; i--)
            list.get(rnd.nextInt(list.size() - 1)).getSick();
    }
    //--------------------------------------------------------------------

    public int getStep() {
        return step;
    }

    //--------------------------------------------------------------------
    public void check(int coord) {
        if (coord < 0 && coord > list.size())//Validité de la coord
            return;
        list.get(coord).age();
        if (!(coord % (step - 1) == 0)) {//Voisin de droite
            if (list.get(coord + 1).getState().equals(State.Contagious)
                    && rnd.nextInt(list.get(coord).getContagionRate()) > 1
                    && checkForSpecies(1, coord)) {

                list.get(coord).getSick();
                return;
            }

        }
        if (!(coord % step == 0)) {//Voisin de gauche
            if (list.get(coord - 1).getState().equals(State.Contagious)
                    && rnd.nextInt(list.get(coord).getContagionRate()) > 1
                    && checkForSpecies(-1, coord)) {


                list.get(coord).getSick();
                return;
            }
        }

        if (coord >= step) {//Voisin du dessus
            if (list.get(coord - step).getState().equals(State.Contagious)
                    && rnd.nextInt(list.get(coord).getContagionRate()) > 1
                    && checkForSpecies(-step, coord)) {

                list.get(coord).getSick();
                return;
            }
        }

        if (coord <= (list.size() - step)) {//Voisin du dessous
            if (list.get(coord + step - 1).getState().equals(State.Contagious)
                    && rnd.nextInt(list.get(coord).getContagionRate()) > 1
                    && checkForSpecies(step, coord)) {

                list.get(coord).getSick();
            }
        }

    }

    private boolean checkForSpecies(int voisin, int coord) {
        return list.get(Math.max(0, coord + voisin - 1)).getMaxAge() == list.get(coord).getMaxAge();
    }

    public void print() {
        for (int i = 0; i < list.size() - 1; i++) {
            System.out.println(list.get(i).getClass() + "\n\t" + " entities.State: " + list.get(i).getState());
        }
    }

    public boolean isItOver() {
        return checkFor(State.Dead) || checkFor(State.Healthy);
    }

    private boolean checkFor(State state) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (!(list.get(i).getState().equals(state)))
                return false;
        }
        return true;
    }
}
