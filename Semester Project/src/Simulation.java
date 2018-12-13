//Ryan Reck
//CSC 385
//Semester Project

import java.util.Random;

import javax.swing.Timer;

public class Simulation implements SimulationEventListener {
    public final static Random random = new Random();
    public static Simulation sim;
    private Tile[][] environment;
    private Timer runTimer;
    //gui stuff
    private AntSimGUI gui;
    private ColonyView cv;
    private ColonyNodeView[][] environmentN;
    
    public static void main(String[] args) {
        sim = new Simulation();
    }
    
    public Simulation() {
        //initiate blank environment.
        environment = new Tile[27][27];
        for(int y = 0; y<27; y++) {
            for(int x = 0; x<27; x++) {
                environment[x][y] = new Tile();
                environment[x][y].setExplored(true);
            }
        }
        
        //initiate timer
        runTimer = new Timer(100, e->step());
        
        //initiate gui
        cv = new ColonyView(27, 27);
        environmentN = new ColonyNodeView[27][27];
        for(int y = 0; y<27; y++) {
            for(int x = 0; x<27; x++) {
                environmentN[x][y] = new ColonyNodeView();
                cv.addColonyNodeView(environmentN[x][y], x, y);
            }
        }
        gui = new AntSimGUI();
        gui.initGUI(cv);
        gui.addSimulationEventListener(this);
    }
    
    public void updateGUI() {
        for(int y = 0; y < 27; y++) {
            for(int x = 0; x < 27; x++) {
                updateTileGUI(x, y);
            }
        }
    }
    
    public void updateTileGUI(int x, int y) {
        Tile tile = environment[x][y];
        ColonyNodeView node = environmentN[x][y];
        
        if(tile.getExplored()) node.showNode();
        else node.hideNode();
        
        node.setID(x+","+y);
        
        if(tile.getQueenPresent()) {
            node.setQueen(true);
            node.showQueenIcon();   
        } else {
            node.setQueen(false);
            node.hideQueenIcon();
        }
        
        if(tile.getBalaCount() > 0) {
            node.showBalaIcon();
        } else {
            node.hideBalaIcon();
        }
        
        if(tile.getScoutCount() > 0) {
            node.showScoutIcon();
        } else {
            node.hideScoutIcon();
        }
        
        if(tile.getSoldierCount() > 0) {
            node.showSoldierIcon();
        } else {
            node.hideSoldierIcon();
        }
        
        if(tile.getForagerCount() > 0) {
            node.showForagerIcon();
        } else {
            node.hideForagerIcon();
        }
        
        node.setForagerCount(tile.getForagerCount());
        node.setScoutCount(tile.getScoutCount());
        node.setSoldierCount(tile.getSoldierCount());
        node.setBalaCount(tile.getBalaCount());
        node.setFoodAmount(tile.getFood());
        node.setPheromoneLevel(tile.getPheromone());
        
    }

	public void simulationEventOccurred(SimulationEvent simEvent) {
	    switch(simEvent.getEventType()) {
	        case SimulationEvent.NORMAL_SETUP_EVENT: {
	            //start with blank environment
	            turn = 0;
	            environment = new Tile[27][27];
                for(int y = 0; y < 27; y++) {
                    for(int x = 0; x < 27; x++) {
                        environment[x][y] = new Tile();
                    }
                }
                //set beginning zone
                for(int y = 12; y<=14; y++) {
                    for(int x = 12; x<=14; x++) {
                        environment[x][y].setExplored(true);
                    }
                }
                //ant time
                environment[13][13].setFoodCount(1000);
                environment[13][13].addAnt(new Queen());
                for(int i = 0; i<10; i++) {
                    environment[13][13].addAnt(new Soldier());
                }
                for(int i= 0; i<50; i++){
                    environment[13][13].addAnt(new Forager());
                }
                for(int i= 0; i<4; i++){
                    environment[13][13].addAnt(new Scout());
                }
                updateGUI();
	            break;
	        }
	        case SimulationEvent.RUN_EVENT: {
	            if(runTimer.isRunning()) {
	                runTimer.stop();
	            } else runTimer.start();
	            break;
	        }
	        case SimulationEvent.STEP_EVENT: {
	            step();
	            break;
	        }
	    }
	}
	
	public void step() {
	    doTurn();
	    if(!environment[13][13].getQueenPresent()) runTimer.stop();
	    gui.setTime(turn+" turns ("+(turn/10)+" days)");
	    updateGUI();
	}
	
    private int turn = 0;
	public void doTurn() {
	    //pheromone decay
        if(turn%10 == 0) {
            for(int y = 0; y < 27; y++) {
                for(int x = 0; x < 27; x++) {
                    Tile tile = environment[x][y];
                    tile.setPheromoneCount(tile.getPheromone() / 2);
                }
            }
        }
        
        //bala spawning
        if(Simulation.random.nextDouble() < 0.03) {
            Ant newBala = new Bala();
            environment[0][0].addAnt(newBala);
        }
        
        //update all ants
        Ant[][][] antsToUpdate = new Ant[27][27][];
        for(int y = 0; y < 27; y++) {
            for(int x = 0; x < 27; x++) {
                antsToUpdate[x][y] = new Ant[environment[x][y].getAnts().size()];
                int i = 0;
                for(Ant a : environment[x][y].getAnts()) {
                    antsToUpdate[x][y][i] = a;
                    i++;
                }
            }
        }
        //now, we will NOT iterate over getAnts(),
        //to avoid Concurrent Modification Exceptions
        for(int y = 0; y < 27; y++) {
            for(int x = 0; x < 27; x++) {
                for(int i = 0; i < antsToUpdate[x][y].length; i++) {
                    Ant a = antsToUpdate[x][y][i];
                    if(a.isDead()) {
                        environment[x][y].removeAnt(a);
                    } else {
                        a.doTurn(environment, x, y);
                    }
                }
            }
        }
        
        turn++;
	}
}