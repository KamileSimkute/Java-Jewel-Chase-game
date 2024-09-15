/**
 * Explosion controls the 4 moveables that spawn after the bomb's detonation.
 * @author Eveleen McGovern
 */
public class Explosion extends Moveable {
	public static final boolean RULE_FOLLOWING = false;
	private int speed = 2;
	private int[] forwardDirection = new int[2];
	private Boolean isPlayer = false;
	private final int DANGER_LEVEL = 2;
	private String upImage="FinalAssets/Explosion(up).png";
	private String downImage="FinalAssets/Explosion(down).png";
	private String leftImage="FinalAssets/Explosion(left).png";
	private String rightImage="FinalAssets/Explosion(right).png";
	private String image;
	private int skippedTicks;

	

	/**
	 * Constructor for Explosion
	 * @param forwardDirection The direction that the explosion will solely move in
	 */
	public Explosion(String forwardDirection) {
		switch(forwardDirection){
            case "UP":
			    this.image = upImage;
				break;
			case "DOWN":
			    this.image = downImage;
				break;
			case "LEFT":
			    this.image = leftImage;
				break;
			case "RIGHT":
			    this.image =rightImage;
				break;
			default:
                throw new IllegalArgumentException("Non direction passed");
			    
		}
		this.forwardDirection = convertDirectionToVector(forwardDirection);
    }

	/**
	 * Decides how explosion should move based on if its last move worked
	 * @param lastMoveSuccess returns true if the last move was valid
	 * @return What to add to move to the current location to move in the correct next direction
	 */
	public int[] move(boolean lastMoveSuccess) {
		if (!canMove()){
            return invalidMove;
        }
		if (lastMoveSuccess){ 
			return forwardDirection;
		} else{
			int[] invalidMove = {-1, -1};
			return invalidMove;
		}
		
	}

	/**
	 * Returns the danger level of the explosion
	 * @return The danger level
	 */
	@Override
    public int getDangerLevel(){
        return this.DANGER_LEVEL;
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
	 * Returns true if the object is a player object
	 * @return A check if the entity is a player
	 */
	public Boolean checkIsPlayer() {
		return this.isPlayer;
	}

	/**
	 * Returns the sprite of the explosion
	 * @return The explosion sprite
	 */
	@Override
    public String getSprite(){
		return this.image;
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
