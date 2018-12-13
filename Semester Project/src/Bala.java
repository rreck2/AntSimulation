//Ryan Reck
//CSC 385
//Semester Project


public class Bala extends Ant{
    public Bala() {
        super();
    }
    
    @Override
    public void doTurn(Tile[][] environment, int x, int y) {
        //aging
        if(age == 365 * 10) {
            kill(); //death by old age
            return; //no need to continue the turn.
        }
        
        Tile current = environment[x][y];
        if(current.getScoutCount() > 0 ||
           current.getSoldierCount() > 0 ||
           current.getForagerCount() > 0 ||
           current.getQueenPresent()) {
            //ATTACK!
            for(Ant a : current.getAnts()) {
                if(!(a instanceof Bala)) {
                    if(Simulation.random.nextDouble() < 0.5) {
                        a.kill();
                    }
                    break;
                }
            }
        } else {
            //movement.
            
            int[][] diffs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            while(true) {
                int[] diff = diffs[Simulation.random.nextInt(4)];
                int x_ = x+diff[0];
                int y_ = y+diff[1];
                if(x_ < 0 || x_ >= 27 || y_ < 0 || y_ >= 27) continue;
                environment[x][y].removeAnt(this);
                environment[x_][y_].addAnt(this);
                break;
            }
        }
        
        age++;
    }
}