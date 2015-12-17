package graph;

import creatures.*;
import virus.Virus;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simulator
 *
 * @author Plague Inc.
 * @version 2015
 */
public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 130;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 90;
    // The probability that a human will be created in any given grid position.
    private static final double HUMAN_CREATION_PROBABILITY = 0.23;
    // The probability that a pig will be created in any given grid position.
    private static final double PIG_CREATION_PROBABILITY = 0.1;
    // The probability that a duck will be created in any given grid position.
    private static final double DUCK_CREATION_PROBABILITY = 0.1;
    // The probability that a chicken will be created in any given grid position.
    private static final double CHICKEN_CREATION_PROBABILITY = 0.1;

    // List of beings in the field.
    private List<Being> beings;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private List<SimulatorView> views;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        beings = new ArrayList<>();
        field = new Field(depth, width);

        views = new ArrayList<>();

        SimulatorView view = new GridView(depth, width);
        view.setColor(Human.class, Color.BLUE);
        view.setColor(Pig.class, Color.PINK);
        view.setColor(Duck.class, Color.GREEN);
        view.setColor(Chicken.class, Color.RED);
        views.add(view);

        view = new GraphView(500, 150, 500);
        view.setColor(Human.class, Color.BLUE);
        view.setColor(Pig.class, Color.PINK);
        view.setColor(Duck.class, Color.GREEN);
        view.setColor(Chicken.class, Color.RED);
        views.add(view);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Runs the simulation with the given number of steps
     */
    public void runSimulation(int i) {
        simulate(i);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     *
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && views.get(0).isViable(field); step++) {
            simulateOneStep();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isItOver()) {
                System.out.println("Simulation Over!");
                break;
            }
        }
    }

    /**
     * Run the simulation from its current state for a single step. Iterate over
     * the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep() {
        step++;
        for (Being b : beings) {
            if (b.isAlive()) {
                for (Being n : getNeighbours(b)) {
                    b.infect(n);
                }
                b.age();
                b.move();
            }
        }
        updateViews();
    }

    public List<Being> getNeighbours(Being being) {
        List<Being> neighbours = new ArrayList<Being>();
        Location location = being.getLocation();
        for (Location otherLocation : field.adjacentLocations(location)) {
            Being b = (Being) field.getObjectAt(otherLocation);
            if (b != null) {
                neighbours.add(b);
            }
        }
        return neighbours;
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        beings.clear();

        for (SimulatorView view : views) {
            view.reset();
        }

        populate();
        insertVirus();
        updateViews();
    }

    /**
     * Update all existing views.
     */
    private void updateViews() {
        for (SimulatorView view : views) {
            view.showStatus(step, field);
        }
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                if (rand.nextDouble() <= HUMAN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Human human = new Human(field, location);
                    beings.add(human);
                } else if (rand.nextDouble() <= PIG_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Pig pig = new Pig(field, location);
                    beings.add(pig);
                } else if (rand.nextDouble() <= DUCK_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Duck duck = new Duck(field, location);
                    beings.add(duck);
                } else if (rand.nextDouble() <= CHICKEN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Chicken chicken = new Chicken(field, location);
                    beings.add(chicken);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Allows to insert various viruses at the beginning of the simulation
     */
    private void insertVirus() {
        Random rnd = new Random();
        int infected = rnd.nextInt(DEFAULT_WIDTH);//Nombre arbitraire d'animaux inféctés au départ
        for (int i = 0; i < infected; i++) {
            int randomBeing = rnd.nextInt(beings.size() - 1);
            if (beings.get(randomBeing) instanceof Duck || beings.get(randomBeing) instanceof Chicken) {
                beings.get(randomBeing).setVirus(Virus.H1N1);
            } else if (beings.get(randomBeing) instanceof Pig) {
                beings.get(randomBeing).setVirus(Virus.H5N1);
            } else if (beings.get(randomBeing) instanceof Human) {//All humans shall be healthy
                i--;
            }
        }
    }

    private boolean isItOver() {
        boolean end = true;
        for (Being b : beings) {
            if (b.getState() != State.Healthy && b.getState() != State.Dead) {
                end = false;
                if (step > 500) {
                    System.out.println(b.getState());
                }
                break;
            }
        }
        return end;
    }
}
