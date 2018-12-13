//Ryan Reck
//CSC 385
//Semester Project
public abstract class Ant {
    private static int id_ = 0; //id counter
    private int id; //ant's id
    protected int age = 0; //in turns
    private boolean dead = false;
    
    public Ant() {
        id = id_;
        id_++;
    }
    
    public void kill() {
        dead = true;
    }
    
    public boolean isDead() {
        return dead;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Ant)) {
            return false; //not equal if it's not even an ant
        }
        Ant o_ = (Ant) o; //cast it up now that we know o is an ant
        return id == o_.id;
    }
    
    public abstract void doTurn(Tile[][] environment, int x, int y);
}