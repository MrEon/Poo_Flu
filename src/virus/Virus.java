package virus;

/**
 * Created by user on 03/12/15.
 */
public enum Virus {
    H5N1(1/2, 10, 1/5), H1N1(1/6, 4, 1/10);

    float contagionRate;//Risque de contagion
    int incubation;//Periode d'incubation (le passage de Sick à  Contagious)
    float mortalityRate;//Probabibilité de mourrir une fois Conatgious


    Virus(float c, int i, float m) {

    }

    public int getMortalityRate() {
        return (int) (1/mortalityRate); //Utile pour random.nextint(...);
    }

    public int getContagionRate() {
        return 1/ (int) contagionRate; //Utile pour random.nextint(...);
    }

    public int getIncubation() {
        return incubation;
    }
}
