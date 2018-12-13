//Ryan Reck
//CSC 385
//Semester Project

import java.util.ArrayList;
import java.util.Stack;

public class Forager extends Ant {
    public Forager() {
        super();
    }
    
    private boolean holdingFood = false;
    private Stack<Integer> movement = new Stack<>();
    
    public void doTurn(Tile[][] environment, int x, int y) {
        //aging
        if(age == 365 * 10) {
            kill(); //death by old age
            return; //no need to continue the turn.
        }
        
        int[][] diffs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        if(!holdingFood) {
            //foraging mode
            if(!(x==13 && y==13) && environment[x][y].getFood() > 0){
                //found food!
                environment[x][y].setFoodCount(environment[x][y].getFood() - 1);
                holdingFood = true;
            } else {
                //DO MOVEMENT PAST HERE
                //look for max pheromone
                int maxPheromone = -1;
                ArrayList<Integer> maxPheromoneDirections = new ArrayList<>();
                ArrayList<Integer> viableDirections = new ArrayList<>();
                for(int i = 0; i < 4; i++) {
                    int[] diff = diffs[i];
                    int x_ = x+diff[0];
                    int y_ = y+diff[1];
                    if(x_ < 0 || x_ >= 27 || y_ < 0 || y_ >= 27) continue;
                    Tile next = environment[x_][y_];
                    if(next.getExplored()) {
                        viableDirections.add(i);
                        int pheromoneLevel = next.getPheromone();
                        if(pheromoneLevel > maxPheromone) {
                            maxPheromone = pheromoneLevel;
                            maxPheromoneDirections = new ArrayList<>();
                            maxPheromoneDirections.add(i);
                        } else if(pheromoneLevel == maxPheromone) {
                            maxPheromoneDirections.add(i);
                        }
                    }
                }
                if(maxPheromoneDirections.size() == 1) { //only one option!
                    int i = maxPheromoneDirections.get(0);
                    int[] diff = diffs[i];
                    if(movement.size() > 0 && i == (movement.peek()^1)) {
                        //don't wanna do this.
                        if(viableDirections.size() == 1) {
                            //no other option..
                            int x_ = x + diff[0];
                            int y_ = y + diff[1];
                            movement.push(i);
                            environment[x][y].removeAnt(this);
                            environment[x_][y_].addAnt(this);
                        } else {
                            while(true) {
                                int j = viableDirections.get(Simulation.random.nextInt(viableDirections.size()));
                                int[] diff2 = diffs[j];
                                if(j == (movement.peek()^1)) continue;
                                //found a different direction
                                int x_ = x + diff2[0];
                                int y_ = y + diff2[1];
                                movement.push(j);
                                environment[x][y].removeAnt(this);
                                environment[x_][y_].addAnt(this);
                                break;
                            }
                        }
                    } else {
                        int x_ = x + diff[0];
                        int y_ = y + diff[1];
                        movement.push(i);
                        environment[x][y].removeAnt(this);
                        environment[x_][y_].addAnt(this);
                    }
                } else { //more than one option!
                    while(true) {
                        int i = maxPheromoneDirections.get(Simulation.random.nextInt(maxPheromoneDirections.size()));
                        int[] diff = diffs[i];
                        if(movement.size() > 0 && i == (movement.peek()^1)) continue;
                        //found a direction to go that's not backwards.
                        int x_ = x + diff[0];
                        int y_ = y + diff[1];
                        movement.push(i);
                        environment[x][y].removeAnt(this);
                        environment[x_][y_].addAnt(this);
                        break;
                    }
                }
                //END MOVEMENT STUFF
            }
            
            
        } else {
            //return to nest mode
            int pheromoneLevel = environment[x][y].getPheromone();
            if(!(x==13 && y==13) && pheromoneLevel < 1000)
                environment[x][y].setPheromoneCount(10 + pheromoneLevel);
            //do movement stuff
            
            int lastMove = movement.pop();
            int[] diff = diffs[lastMove^1];
            int x_ = x + diff[0];
            int y_ = y + diff[1];
            environment[x][y].removeAnt(this);
            environment[x_][y_].addAnt(this);
            
            if(movement.size() == 0) { //back at the entrance
                //drop the food
                holdingFood = false;
                environment[x_][y_].setFoodCount(environment[x_][y_].getFood() + 1);
            }
        }
        age++;
    }
}