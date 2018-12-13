//Ryan Reck
//CSC 385
//Semester Project

public class Scout extends Ant {
    public Scout() {
        super();
    }
    
    @Override
    public void doTurn(Tile[][] environment, int x, int y) {
        //aging
        if(age == 365 * 10) {
            kill(); //death by old age
            return; //no need to continue the turn.
        }
        //movement!
        int[][] diffs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while(true) {
            int[] diff = diffs[Simulation.random.nextInt(4)];
            int x_ = x+diff[0];
            int y_ = y+diff[1];
            if(x_ < 0 || x_ >= 27 || y_ < 0 || y_ >= 27) continue;
            Tile next = environment[x_][y_];
            if(next.getExplored()) {
                environment[x][y].removeAnt(this);
                next.addAnt(this);
                break;
            } else {
                //discover it!
                next.setExplored(true);
                if(Simulation.random.nextDouble() < 0.25) {
                    //food!
                    int foodAmt = 500 + Simulation.random.nextInt(501);
                    next.setFoodCount(foodAmt);
                }
            }
            break;
        }
        
        
        age++;
    }
}
