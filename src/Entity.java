/**
 * Created by user on 26/11/15.
 */
public abstract class Entity {

    protected State state = State.Healthy;
    protected float contagionRate = 1 / 2;
    protected int sicknessCounter = 0;//Compte le nombre de jours dans un
    protected int age = 0;

    public abstract int getMaxAge();

    public State getState() {
        return state;
    }

    public void getSick() {
        state = State.Sick;
        sicknessCounter = 0;
    }

    public int getContagionRate() {
        return (int) ((int) 1 / contagionRate);
    }

    public void age() {
        age++;
        sicknessCounter++;
        changeState();
    }

    protected void changeState() {
        if (getMaxAge() < age) {
            state = State.Dead;
            return;
        }
        changeToContagious();

        dieFromSickness();

        recoverFromSickness();
    }

    protected void changeToContagious() {
        if (state.equals(State.Sick) && sicknessCounter >= 2) {
            state = State.Contagious;
            sicknessCounter = 0;
        }
    }


    protected void dieFromSickness() {
        if (state.equals(State.Contagious) && sicknessCounter >= 3) {
            state = State.Dead;
            sicknessCounter = 0;
        }
    }

    protected void recoverFromSickness() {
        if (state.equals(State.Recovering) && sicknessCounter >= 3) {
            state = State.Healthy;
            sicknessCounter = 0;
        }
    }
}
