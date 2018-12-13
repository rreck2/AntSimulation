//Ryan Reck
//CSC 385
//Semester Project

import java.util.ArrayList;

public class Tile {
    private int scoutCount = 0;
    private int foragerCount = 0;
    private int soldierCount = 0;
    private int balaCount    = 0;
    private int food = 0;  
    private int pheromone = 0;
    private boolean explored = false;
    private boolean queenPresent = false;
    
    private ArrayList<Ant> ants = new ArrayList<>();
    
    // getters
    
    public int getScoutCount() {
        return scoutCount;
    }
    public int getForagerCount() {
        return foragerCount;
    }
    public int getSoldierCount() {
        return soldierCount;
    }   
    public int getBalaCount() {
        return balaCount;
    }   
    public int getFood() {
        return food;
    }   
    public int getPheromone() {
        return pheromone;
    }   
    public boolean getExplored() {
        return explored;
    }   
    public boolean getQueenPresent() {
        return queenPresent;
    }
    public ArrayList<Ant> getAnts() {
        return ants;
    }
    
    // setters
    
    public void setScoutCount(int scoutCount) {
        this.scoutCount = scoutCount;
    }
    public void setForagerCount(int foragerCount) {
        this.foragerCount = foragerCount;
    }
     public void setSoldierCount(int soldierCount) {
        this.soldierCount = soldierCount;
    }
     public void setBalaCount(int balaCount) {
        this.balaCount = balaCount;
    }
     public void setFoodCount(int food) {
        this.food = food;
    }
     public void setPheromoneCount(int pheromone) {
        this.pheromone = pheromone;
    }
     public void setExplored(boolean explored) {
        this.explored = explored;
    }
     public void setQueenPresent(boolean queenPresent) {
        this.queenPresent = queenPresent;
    }
    public void addAnt(Ant a) {
        ants.add(a);
        if(a instanceof Queen) {
            queenPresent = true;
        } else if(a instanceof Soldier) {
            soldierCount ++;
        } else if(a instanceof Forager) {
            foragerCount ++;
        } else if(a instanceof Scout) {
            scoutCount ++;
        } else if(a instanceof Bala) {
            balaCount ++;
        }
    }
    public boolean removeAnt(Ant a) {
        boolean success = ants.remove(a);
        if(success) {
            if(a instanceof Queen) {
                queenPresent = false;
            } else if(a instanceof Soldier) {
                soldierCount --;
            } else if(a instanceof Forager) {
                foragerCount --;
            } else if(a instanceof Scout) {
                scoutCount --;
            } else if(a instanceof Bala) {
                balaCount --;
            }
        }
        return success;
    }
    
    
}

