package virus;

/**
 * Created by user on 03/12/15.
 */
public enum Virus {
    H5N1(20, 10, 5), H1N1(16, 4, 10), NONE(1, 1, 1);

    int contagionRate;//Risque de contagion  (1/contagionRate)
    int incubation;//Periode d'incubation (le passage de Sick à  Contagious)
    int mortalityRate;//Nombre de jours minimum à vivre avant d'avoir la malchance de mourir


    Virus(int c, int i, int m) {
        contagionRate = c;
        incubation = i;
        mortalityRate = m;
    }

    public int getMortalityRate() {
        return mortalityRate; //Utile pour random.nextint(...);
    }

    public int getContagionRate() {
        return contagionRate; //Utile pour random.nextint(...);
    }

    public int getIncubation() {
        return incubation;
    }
}
