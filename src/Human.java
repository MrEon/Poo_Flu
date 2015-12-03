import java.util.Random;

/**
 * Created by user on 26/11/15.
 */
public class Human extends Entity {
    private static final int MAX_AGE = 50;

    @Override
    protected void dieFromSickness() {
        if (state.equals(State.Contagious) && sicknessCounter >= 3) {
            Random rnd = new Random();
            if (rnd.nextInt(3) > 1)
                state = State.Recovering;
            else
                state = State.Dead;

            sicknessCounter = 0;
        }
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
