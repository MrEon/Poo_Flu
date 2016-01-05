package virus;

/**
 * Virus
 *
 * @author SI3 G3 - Groupe numero 3 : Boucher-Thouveny & Lafaurie & Monier
 * @version 2015
 */
public enum Virus {
    H5N1(20, 10, 5), H1N1(16, 4, 10), NONE(1, 1, 1);

    int contagionRate;
    int incubation;
    int mortalityRate;


    Virus(int c, int i, int m) {
        contagionRate = c;
        incubation = i;
        mortalityRate = m;
    }

    public int getMortalityRate() {
        return mortalityRate;
    }

    public int getContagionRate() {
        return contagionRate;
    }

    public int getIncubation() {
        return incubation;
    }
}
