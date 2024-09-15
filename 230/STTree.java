/**
 * The tree to store the paths of possible movement for the Smart Thief to find an item
 * @author Eveleen McGovern
 */

import java.util.*;

public class STTree{
    int[] currentCoords = new int[2];
    List<STTree> children = new LinkedList<>();
    boolean valid = false;
    int[][] allPossibleDirection = {{1 ,0}, {-1, 0}, {0, 1}, {0 ,-1}};
    static ArrayList<STTree> nodesByBreadth = new ArrayList<>();
    STTree parent;
    int[] directionAdd = new int[2];
    int[] nodeCoords = new int[2];
    ArrayList<STTree> itemPathList = new ArrayList<>();
    static ArrayList<int[]> itemPathDirectionList = new ArrayList<>();
    boolean itemFound = false;
    int xDirection;
    int yDirection;
    int[] directionToAdd = new int[2];

    Cell[][] gameBoard;
    int boardWidth;
    int boardHeight;
    int[] direction;

    /**
	 * Constructor for the root node of the tree
	 * @param xCoord the x Coordinate the thief is on
     * @param yCoord the y Coordinate the thief is on
     * @param grid the gameboard currently being played on
	 * @param height the height of the board
	 * @param width the width of the board
	 * @param coords the current coordinates that the thief is on
	 */
    STTree (int xCoord, int yCoord, Cell[][] grid, int height, int width, int[] coords){
        currentCoords[0] = xCoord;
        currentCoords[1] = yCoord;
        this.gameBoard = grid;
        this.boardHeight = height;
        this.boardWidth = width;
        this.direction = coords;
    }

    /**
	 * Constructor for the child nodes of the tree
	 * @param xCoord the x Coordinate the thief is on
     * @param yCoord the y Coordinate the thief is on
     * @param parent the node's parent in the tree
     * @param xDirection the x direction of the vector that the thief will have moved in on this path
     * @param yDirection the y direction of the vector that the thief will have moved in on this path
     * @param grid the gameboard currently being played on
	 * @param height the height of the board
	 * @param width the width of the board
	 * @param coords the current coordinates that the thief is on
	 */
     STTree (int xCoord, int yCoord, STTree parent, int xDirection, int yDirection, Cell[][] grid, int height, int width, int[] coords){
        currentCoords[0] = xCoord;
        currentCoords[1] = yCoord;
        this.parent = parent;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.gameBoard = grid;
        this.boardHeight = height;
        this.boardWidth = width;
        this.direction = coords;
    }

    /**
	 * Returns the X coordinate of the node
	 * @return the X coordinate of the node
	 */
    public int getX(){
        return this.currentCoords[0];
    }

    /**
	 * Returns the Y coordinate of the node
	 * @return the Y coordinate of the node
	 */
    public int getY(){
        return this.currentCoords[1];
    }

    /**
	 * Returns the X coordinate of the vector that the thief will have moved in on this path
	 * @return the X coordinate of the vector that the thief will have moved in on this path
	 */
    public int getXDirection(){
        return this.xDirection;
    }

    /**
	 * Returns the Y coordinate of the vector that the thief will have moved in on this path
	 * @return the Y coordinate of the vector that the thief will have moved in on this path
	 */
    public int getYDirection(){
        return this.yDirection;
    }

    /**
	 * Returns the parent of the node
	 * @return the node's parent
	 */
    public STTree getParent(){
        return this.parent;
    }

    /**
	 * Sets the root of the node, and the child nodes that allow for valid movements in all 4 directions from it
	 * @return the valid path to the closest item
	 */
    public ArrayList<int[]> setRoot(){
        nodesByBreadth.add(this);
            for (int x = 0; x < nodesByBreadth.size(); x++){
                nodesByBreadth.get(x).setChildren();
                if (itemFound || x > 500) {break;}
            }

        if (!itemFound){
            int[] noItemCoords = {2, 2};
            itemPathDirectionList.add(noItemCoords);
        }
        nodesByBreadth.clear();
        return itemPathDirectionList;
    }

    /**
	 * Sets the root of the node, and the child nodes that allow for valid movements in all 4 directions from it
	 * @param leafWithItem the leaf node which had the coordinates of the item
	 */
    public void makePath(STTree leafWithItem){
        if (leafWithItem.getParent() != null){
            itemPathList.add(leafWithItem);
            makePath(leafWithItem.getParent());
        }
        itemPathList.add(leafWithItem);
        Collections.reverse(itemPathList);
    }

    /**
	 * Creates the child nodes and checks if they have an item on them
	 */
    public void setChildren(){
        if (null != gameBoard[currentCoords[0]][currentCoords[1]].getItem()) {
            makePath(this);
            for (int x = 0; x < itemPathList.size(); x++){
                directionToAdd[0] = xDirection;
                directionToAdd[1] = yDirection;
                itemPathDirectionList.add(directionToAdd);
                itemFound = true;
            }
        }  else {
                for (int x = 0; x < 4; x++){
                    directionAdd[0] = allPossibleDirection[x][0];
                    directionAdd[1] = allPossibleDirection[x][1];
                    boolean ruleFollowing = true;
                
                    valid = Misc.validate(gameBoard, boardWidth, boardHeight, currentCoords, directionAdd, ruleFollowing);
                    if (valid) {
                        STTree newNode = new STTree(currentCoords[0] + directionAdd[0], currentCoords[1] + directionAdd[1], this, directionAdd[0], directionAdd[1], gameBoard, boardWidth, boardHeight, direction);
                        this.children.add(newNode);
                        nodesByBreadth.add(newNode);
                     }
                }
    }
}

}
