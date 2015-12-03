


/**
 * Created by user on 26/11/15.
 */
public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(5);
        int days = 0;
        while(!grid.isItOver()){
            for(int i  = 0; i<grid.getStep()*grid.getStep(); i++){
                grid.check(i);
                System.out.println("Day "+days);
                grid.print();
                days++;
            }
        }

        for(int i  = 0; i<grid.getStep()*grid.getStep(); i++){

        }

    }
}
