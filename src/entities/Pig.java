package entities;

/**
 * Created by user on 26/11/15.
 */
public class Pig extends Entity {
    private static final int MAX_AGE = 30;

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
