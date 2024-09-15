/**
 * The abstract parent of all moveable classes.
 * @author Joseph
 * @version 1.0
 */
public abstract class Moveable {
    //The number of ticks between each movement
    private int speed;
    private int skippedTicks=0;
    //The default direction to attempt to move in.
    private  int[] forwardDirection;
    //Tracks whether the current moveable is of type Player.
    private Boolean isPlayer;
    private final int DANGER_LEVEL = -1;
    private final boolean RULE_FOLLOWING = false;
    private String image;
    protected int[] invalidMove = new int[]{0,0};

    /**
     * Abstract method, to be implemented in subclasses
     * @return direction The direction to attempt to move in.
     */
    public  int[] move(boolean lastMoveSuccess){
        return forwardDirection;
    }
    /**
     * Compares the number of ticks passed since last move to the tick speed.
     * @return whether the moveable can move on this tick.
     */
    public boolean canMove(){
        if (skippedTicks >=speed){
            return true;
        } else{
            skippedTicks+=1;
            return false;
        }
    }
    /**
     * Changes the forward direction value.
     * @param forwardDirection The direction to change the forward direction too.
     */
    public void changeForwardDirection(int[] direction ){
        this.forwardDirection = direction;
    }
    /**
     * getter for the forwatd direction
     * @return forward direction as a 2d vector
     */
    public int[] getForwardDirection(){
        return this.forwardDirection;
    }
    /**
     * Variation of the forward direction getter.
     * @return forward direction as a 2d vector
     */
    public String getForwardDirectionAsString(){
        return directionToString(this.forwardDirection);
    }
    /**
     * Getter for the danger level.
     * @return danger level
     */
    public int getDangerLevel(){
        return this.DANGER_LEVEL;
    }
    /**
     * Return whether the movable is conscerned with colours.
     * @return if moveable follows rules.
     */
    public boolean followsRules(){
        return this.RULE_FOLLOWING;
    }
    /**
     * Returns true if the current moveable is an instance of the Player class.
     * @return whether moveable is a player.
     */
    public Boolean checkIsPlayer(){
        return this.isPlayer;
    }
    /**
     * Getter for the moveables's image filepath
     * @return image path
     */
    public String getSprite(){
        return image;
	}
    /**
     * Converts a vectorin string format to a vector direction.
     * @param Direction Direction in string form, allcaps.
     * @return direction in vector form.
     */
    public static int[] convertDirectionToVector(String Direction){
        int[] newDirection= new int[2];
        switch (Direction){
            case ("UP"): 
                newDirection[0] = 0;
                newDirection[1] = 1;
                break;
            case ("DOWN"): 
                newDirection[0] = 0;
                newDirection[1] = -1;
                break;
            case ("LEFT"): 
                newDirection[0] = -1;
                newDirection[1] = 0;
                break;
            case ("RIGHT"): 
                newDirection[0] = 1;
                newDirection[1] = 0;
                break;

            default:
                newDirection[0] = 0;
                newDirection[1] = 0;
        }
        return newDirection;
    }
    /**
     * Convert a vector direction to the corresponding String.
     * @param direction a vector
     * @return direction equivalent to vector
     */
    public String directionToString(int[] direction){
        String newDirection;
        if (direction[0]==0&&direction[1]==1){
            newDirection = "UP";
        } else if (direction[0]==0&&direction[1]==-1){
            newDirection = "DOWN";
        } else if (direction[0]==1&&direction[1]==0){
            newDirection = "RIGHT";
        } else if (direction[0]==-1&&direction[1]==0){
            newDirection = "UP";
        } else{
            newDirection = "NONE";
        }
        return newDirection;
    }
}