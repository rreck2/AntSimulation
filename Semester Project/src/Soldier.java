//Ryan Reck
//CSC 385
//Semester Project

public class Soldier extends Ant {
    public Soldier(){
        super();
    }
    
    @Override
    public void doTurn(Tile[][] environment, int x, int y) {
        //aging
        if(age == 365 * 10) {
            kill(); //death by old age
            return; //no need to continue the turn.
        }
        
        if(environment[x][y].getBalaCount() > 0){
            //ATTACK!!
            for(Ant a : environment[x][y].getAnts()) {
                if(a instanceof Bala) {
                    //found one!
                    if(Simulation.random.nextDouble() < 0.5) {
                        //killed it!
                        a.kill();
                    }
                    break;
                }
            }
        } else {
            //scout mode.
            
            //first step: look for adjacent bala ants.
            int[][] diffs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            boolean balaFound = false;
            for(int[] diff : diffs) {
                int x_ = x+diff[0];
                int y_ = y+diff[1];
                if(x_ < 0 || x_ >= 27 || y_ < 0 || y_ >= 27) continue;
                Tile next = environment[x_][y_];
                if(next.getBalaCount() == 0) continue; //no Bala there.
                if(next.getExplored()) {
                    balaFound = true;
                    environment[x][y].removeAnt(this);
                    next.addAnt(this);
                    break;
                }
            }
            if(!balaFound) {
                //wander
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
                    }
                }
            }
        }
        
        age++;
    }
}