
/**
 * The class that represents the player, keyboard input is directed here.
 */
public class Player extends Moveable{
    private int speed = 2;
    private final boolean RULE_FOLLOWING = true;
    private  int[] forwardDirection = new int[2];
    private final int DANGER_LEVEL = 0;
    private Boolean isPlayer = true;
    private String image ="FinalAssets/player.png";
    private int skippedTicks;
    /**
     * Constructor of a player instance.
     * @param forwardDirection the direction to change the forward direction too.
     */
    
    @Override
    public String getSprite(){
		return this.image;
	}

    @Override
    public int[] move(boolean lastMoveSuccess){
        if (canMove()){
            return forwardDirection;
        } else{
            return invalidMove;
        }
         
    }
    @Override
    public Boolean checkIsPlayer(){
        return this.isPlayer;
    }
    @Override
    public boolean canMove(){
        if (skippedTicks >=speed){
            return true;
        } else{
            skippedTicks+=1;
            return false;
        }
    }
    /**
     * String variation of changeForwardDirection.
     * @param direction
     */
    public void setForwardDirection(String direction){
        this.forwardDirection = convertDirectionToVector(direction);
    }
    @Override
    public int getDangerLevel(){
        return this.DANGER_LEVEL;
    }
    @Override
    public boolean followsRules(){
        return this.RULE_FOLLOWING;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
