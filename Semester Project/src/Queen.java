//Ryan Reck
//CSC 385
//Semester Project

public class Queen extends Ant {
    public Queen() {
        super();
        
    }
    
    private final double PROB_FORAGER = 0.5;
    private final double PROB_SCOUT = 0.25;
    private final double PROB_SOLDIER = 0.25;
    
    @Override
    public void doTurn(Tile[][] environment, int x, int y) {
        if(age == 20 * 365 * 10) {
            kill(); //death by old age
            return; //no need to continue the turn.
        }
        
        //consume food
        int food = environment[x][y].getFood();
        if(food>0) {
            environment[x][y].setFoodCount(food-1);
        } else {
            kill(); //death by starvation
        }
        
        //ant hatching
        if(age%10 == 0) {
            double rand = Simulation.random.nextDouble();
            Ant newAnt; //to be filled
            if(rand < PROB_FORAGER) {
               newAnt = new Forager();
            } else if(rand < PROB_FORAGER + PROB_SCOUT) {
                newAnt = new Scout();
            } else {
                newAnt = new Soldier();
            }
            environment[x][y].addAnt(newAnt);
        }
        age++;
    }
}