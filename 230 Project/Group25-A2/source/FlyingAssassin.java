/**
 * Handles the movement and stores information for the Flying Assassin NPC.
 * @author Eveleen McGovern
 */

public class FlyingAssassin extends Moveable{
	private int speed = 4;
	public static final boolean RULE_FOLLOWING = false;
    private  int[] forwardDirection = new int[2];
    private final int DANGER_LEVEL = 1;
    private Boolean isPlayer = false;
	private String image = "FinalAssets/FlyingAssassin.png";
	private int skippedTicks;
    
	/**
	 * Constructor for the Flying Assassin
	 * @param forwardDirection The direction that the Flying Assassin will move in on a given turn
	 */
	public FlyingAssassin(String forwardDirection) {
        this.forwardDirection = convertDirectionToVector(forwardDirection);
	}

	/**
	 * Turns the Flying Assassin 180 degrees if the last move was invalid
	 * @param lastMoveSuccess returns true if the last move was valid
	 * @return The next direction to move in as a vector
	 */
	 public int[] move(boolean lastMoveSuccess) {
		if (!canMove()){
            return invalidMove;
        }
		if (lastMoveSuccess){
			return forwardDirection;
		} else {
			int[] newDirection = new int[2];
			newDirection[0] = -forwardDirection[0];
			newDirection[1] = -forwardDirection[1];
			forwardDirection = newDirection;
			return newDirection;
        }
		
	}
	/**
	 * Sets the new forwardDirection
	 * @param direction The direction in which the assassin will move on the next turn
	 */
	public void changeForwardDirection(int[] direction ){
		this.forwardDirection = direction;
	}

	/**
	 * Returns the danger level of the assassin
	 * @return The danger level
	 */
	@Override
    public int getDangerLevel(){
        return this.DANGER_LEVEL;
    }
	
	/**
	 * Returns the sprite of the assassin
	 * @return The assassin's sprite
	 */
	@Override
	public String getSprite(){
		return this.image;
	}
	
	/**
	 * Returns true if the object is a player object
	 * @return A check if the entity is a player
	 */
	public Boolean checkIsPlayer(){
		return this.isPlayer;
	}

	/**
	 * Implements speed by deciding how many ticks have passed since the last move
	 * @param canMove returns if the object can move or not
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
	 * Returns the name of the class formatted as a string
	 * @return the name of the class as a string
	 */
	@Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
	
}
