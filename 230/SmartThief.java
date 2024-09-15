/**
 * Handles the movement and stores information for the Smart Thief NPC.
 * @author Eveleen McGovern
 */

import java.util.*;

public class SmartThief extends Moveable{
	private int speed = 5;
	private final boolean RULE_FOLLOWING = true;
    private  int[] forwardDirection = new int[2];
    private final  int DANGER_LEVEL = 0;
	private ArrayList<int[]> itemPathDirectionList =  new ArrayList<>();
	int[] changeForwardDirection = new int[2];
	int pathCounter = 0;
	int[][] allPossibleDirection = {{1 ,0}, {-1, 0}, {0, 1}, {0 ,-1}};
	Random randomDirection = new Random();
	int setRandom;
	private int skippedTicks;
	private String image ="FinalAssets/SmartThief.png";
    

	/**
	 * Constructor for the Smart Thief
	 * @param forwardDirection The direction that the thief will move in on a given turn
	 */
	public SmartThief(String forwardDirection) {
        this.forwardDirection = convertDirectionToVector(forwardDirection);
    }
	
	/**
	 * Takes the calculated path towards the closest item and sends back the directions to follow that path
	 * @param lastMoveSuccess returns true if the last move was valid
	 * @param grid the gameboard currently being played on
	 * @param height the height of the board
	 * @param width the width of the board
	 * @param coords the current coordinates that the thief is on
	 * @return The next direction to move in as a vector
	 */
	public int[] move(boolean lastMoveSuccess, Cell[][] grid, int height, int width, int[] coords) {
		if (!canMove()){
            return invalidMove;
        }
		if (pathCounter < itemPathDirectionList.size()){
			handlePathGiven(pathCounter);
			pathCounter +=1;
		} else{
			pathCounter = 0;
			STTree root = new STTree(coords[0], coords[1], grid, height, width, coords);
			itemPathDirectionList = root.setRoot();
		}
		if (itemPathDirectionList.get(0)[0] == 2){
			setRandom = randomDirection.nextInt(4);
			forwardDirection[0] = allPossibleDirection[setRandom][0];
			forwardDirection[1] = allPossibleDirection[setRandom][1];
		}
		return forwardDirection;
	}
	
	/**
	 * Sets the correct next forward direction given the instructions layed out by the path
	 * @param counter keeps track of how many moves in the path have been made
	 */
	public void handlePathGiven(int counter){
		changeForwardDirection[0] = itemPathDirectionList.get(counter)[0];
		changeForwardDirection[1] = itemPathDirectionList.get(counter)[1];
		forwardDirection = changeForwardDirection;
	}

	/**
	 * Implements speed by deciding how many ticks have passed since the last move
	 * @param canMove returns if the object can move or not
	 */
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
	 * Returns the danger level of the thief
	 * @return The danger level
	 */
	@Override
    public int getDangerLevel(){
        return this.DANGER_LEVEL;
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
	 * Returns the sprite of the thief
	 * @return The thief's sprite
	 */
	@Override
    public String getSprite(){
		return this.image;
	}
	@Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
	
}