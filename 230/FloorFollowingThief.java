/**
 * Handles the movement and stores information for the Floor Following Thief NPC.
 * @author Eveleen McGovern
 */

public class FloorFollowingThief extends Moveable {
	private int speed = 3; // Currently same as smart thief, none specified in specification. Change?
	private final boolean RULE_FOLLOWING = true;
	private int[] forwardDirection = new int[2];
	private final int DANGER_LEVEL = 0;
	private Boolean isPlayer = false;
	public TileColour permittedColour;
	private String image ="FinalAssets/FloorFollowingThief.png";
	private int skippedTicks;

	/**
	 * Constructor for the Floor Following Thief
	 * @param forwardDirection The direction that the thief will move in on a given turn
	 */
	public FloorFollowingThief(String forwardDirection,TileColour colourFollowing) {
		this.forwardDirection = convertDirectionToVector(forwardDirection);
		this.permittedColour = colourFollowing;
	}

	/**
	 * Finds what is closest to the leftmost direction that is valid for the thief to turn to
	 * @param lastMoveSuccess returns true if the last move was valid
	 * @return The next direction to move in as a vector
	 */
	public int[] move(boolean lastMoveSuccess) {
		if (!canMove()){
            return invalidMove;
        }
		if (lastMoveSuccess) {
			return forwardDirection;
		} else {
			int[]tryRight = TurnNinetyDegrees(forwardDirection);
			changeForwardDirection(tryRight);
			return tryRight;
		}
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
	 * Turns the thief 90 degrees to the right
	 * @param direction the last direction that was moved in
	 * @return The 90 degree right turn from the previous direction
	 */
	public int[] TurnNinetyDegrees(int[] direction) {
		int[] rightTurn = new int[2];
		int rightTurnY = direction[0];
		rightTurn[0] = direction[1];
		rightTurn[1] = -rightTurnY;
		
	    return rightTurn;
	}

	/**
	 * Returns the thief's designated colour
	 * @return the colour that the thief is set to follow
	 */
	public TileColour getColour() {
		return this.permittedColour;
	}

	/**
	 * Sets the new forwardDirection
	 * @param direction The direction in which the thief will move on the next turn
	 */
	public void changeForwardDirection(int[] direction) {
		this.forwardDirection = direction;
	}

	/**
	 * Returns the danger level of the thief
	 * @return The danger level
	 */
	@Override
    public int getDangerLevel(){
        return this.DANGER_LEVEL;
    }

	/**
	 * Returns true if the object is a player object
	 * @return A check if the entity is a player
	 */
	public Boolean checkIsPlayer() {
		return this.isPlayer;
	}

	/**
	 * Returns the sprite of the thief
	 * @return The thief's sprite
	 */
	@Override
	public String getSprite(){
		return this.image;
	}

	/**
	 * Returns true if the object in the class is rule-following
	 * @return true if the object is rule following
	 */
	@Override
	public boolean followsRules(){
		return this.RULE_FOLLOWING;
	}

	/**
	 * Returns the name of the class formatted as a string
	 * @return the name of the class as a string
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
