import javafx.scene.paint.Color;
/**
 * Utility class to be used by others
 */
public final class Misc {

    /**
     * converts a tileColour to its string format.
    */
    public static String tileColourToString(TileColour colour) {
        switch (colour) {
            case RED:
                return "RED";
            case YELLOW:
                return "YELLOW";
            case GREEN:
                return "GREEN";
            case BLUE:
                return "BLUE";
            case CYAN:
                return "CYAN";
            case MAGENTA:
                return "MAGENTA";
            default:
                throw new IllegalArgumentException("Non colour passed");
        }
    }
    /**
     * converts a tileColour to its initial as a String
    */
    public static String tileColourToStringAbbreviated(TileColour colour) {
        switch (colour) {
            case RED:
                return "R";
            case YELLOW:
                return "Y";
            case GREEN:
                return "G";
            case BLUE:
                return "B";
            case CYAN:
                return "C";
            case MAGENTA:
                return "M";
            default:
                throw new IllegalArgumentException("Non colour passed");
        }
    }
    /**
     * converts a tileColour to a java inbuilt colour
     * @param tileC tileColour to convert.
     * @returntileColour in colour form
     */
    public static Color tileColorTColor(TileColour tileC){
        switch (tileC) {
            case RED:
                return Color.RED;
            case YELLOW:
                return Color.YELLOW;
            case GREEN:
                return Color.GREEN;
            case BLUE:
                return Color.BLUE;
            case CYAN:
                return Color.CYAN;
            case MAGENTA:
                return Color.MAGENTA;
            default:
                throw new IllegalArgumentException("Non Tilecolour passed");
        }
    }

    /** 
     * takes a string input of a colour and returns the correct enum
     * @param TileColour to convert
     * @return colour in String  form
     */
    public static TileColour tileColourFromString(String colourChar) {
        switch (colourChar) {
            case "R":
            case "RED":
                return TileColour.RED;
            case "Y":
            case "YELLOW":
                return TileColour.YELLOW;
            case "G":
            case "GREEN":
                return TileColour.GREEN;
            case "B":
            case "BLUE":
                return TileColour.BLUE;
            case "C":
            case "CYAN":
                return TileColour.CYAN;
            case "M":
            case "MAGENTA":
                return TileColour.MAGENTA;
            default:
                throw new IllegalArgumentException("String passed has no corresponding TileColour");
        }
    }
    /**
     * checks if two arrays of TileColours have at least 1 matching colour.
     * @param arrayA first array 
     * @param arrayB second array
     * @return if there were any matches
     */
    public static boolean hadMatchingColour(TileColour[] arrayA, TileColour[] arrayB){
        //for the purpose of this project length will always be 4
        boolean matching = false;
        for (int i=0; i< arrayA.length;i++){
            for (int j=0; j< arrayB.length;j++){
                if (arrayA[i] == arrayB[j]){
                    matching = true;
                }
            }
        }
    return matching;
    }
    /**
     * Converts an xy vector into 2D array format 
     * @param vector vector to convert.
     * @return the converted vector.
     */
    public static int[] vectorToBoardVector(int[] vector){
        int[]newVector = new int[2];
        int temp = vector[0];
        newVector[0] = -vector[1];
        newVector[1] = temp;
        return newVector;
    }
    /**
     * whether it is possible to move  to the desired square
     * @param board cell array
     * @param boardWidth width of grid
     * @param boardHeight height of grid
     * @param startLocation coordinates of the moveable trying to move.
     * @param direction vector direction to move in
     * @param ruleFollowing whether the moveable is conscerned with colours
     * @return if the move can be made
     */
    public static boolean validate(Cell[][] board, int boardWidth, int boardHeight, int[] startLocation, int[] direction,boolean ruleFollowing){
        if (direction[0]==0 && direction[1]==0){
            return false;
        }
        int xCoordinate = startLocation[0]+direction[0];
        int yCoordinate = startLocation[1]+direction[1];
        boolean valid = false;
        TileColour[] startcellColours = board[startLocation[0]][startLocation[1]].getTileColours();
        Cell newCell;
        if (xCoordinate>=0 && xCoordinate< boardWidth && yCoordinate >=0 && yCoordinate< boardHeight && !valid){
            newCell = board[xCoordinate][yCoordinate];
            if (Misc.hadMatchingColour(startcellColours,newCell.getTileColours()) && newCell.isUsable()){
                valid = true;
            } else if(!ruleFollowing){
                valid = true;
            }
        }
        return valid;
    }
    /**
     * Variation of method for floor following thief.
     * @param board cell array
     * @param boardWidth width of grid
     * @param boardHeight height of grid
     * @param startLocation coordinates of the moveable trying to move.
     * @param direction vector direction to move in
     * @param ruleFollowing whether the moveable is conscerned with colours
     * @return if the move can be made
     */
    public static boolean validate(Cell[][] board, int boardWidth, int boardHeight, int[] startLocation, int[] direction,TileColour colour){
        if (direction[0]==0 && direction[1]==0){
            return false;
        }
        int xCoordinate = startLocation[0]+direction[0];
        int yCoordinate = startLocation[1]+direction[1];
        boolean valid = false;
        Cell newCell;
        TileColour[] duplicatedColours = new TileColour[] {colour,colour,colour,colour};
        if (xCoordinate>=0 && xCoordinate< boardWidth && yCoordinate >=0 && yCoordinate< boardHeight && !valid){
            newCell = board[xCoordinate][yCoordinate];
            if (Misc.hadMatchingColour(duplicatedColours,newCell.getTileColours()) && newCell.isUsable()){
                valid = true;
            }
        }
        return valid;
    }
    /**
     * Variation of method that allows for jumping to avaliable cell.
     * @param board cell array
     * @param boardWidth width of grid
     * @param boardHeight height of grid
     * @param startLocation coordinates of the moveable trying to move.
     * @param direction vector direction to move in
     * @param ruleFollowing whether the moveable is conscerned with colours
     * @return if the move can be made
     */
    public static int[] validateJump(Cell[][] board, int boardWidth, int boardHeight, int[] startLocation, int[] direction,boolean ruleFollowing){
        if (direction[0]==0 && direction[1]==0){
            return startLocation;
        }
        int xCoordinate = startLocation[0]+direction[0];
        int yCoordinate = startLocation[1]+direction[1];
        boolean valid = false;
        TileColour[] startcellColours = board[startLocation[0]][startLocation[1]].getTileColours();
        Cell newCell;
        int[] validSquare = startLocation;
        while(xCoordinate>=0 && xCoordinate< boardWidth && yCoordinate >=0 && yCoordinate< boardHeight && !valid){
            newCell = board[xCoordinate][yCoordinate];
            if (Misc.hadMatchingColour(startcellColours,newCell.getTileColours()) && newCell.isUsable()){
                valid = true;
                validSquare[0] =xCoordinate;
                validSquare[1] = yCoordinate;
            } else if(!ruleFollowing){
                valid = true;
                startLocation[0] +=direction[0];
                startLocation[1] +=direction[1];
            }
            xCoordinate += direction[0];
            yCoordinate += direction[1];
        }
        return validSquare;
    }
    /**
     * Variation of method for floor following thief.
     * @param board cell array
     * @param boardWidth width of grid
     * @param boardHeight height of grid
     * @param startLocation coordinates of the moveable trying to move.
     * @param direction vector direction to move in
     * @param ruleFollowing whether the moveable is conscerned with colours
     * @return if the move can be made
     */
    public static int[] validateJump(Cell[][] board, int boardWidth, int boardHeight, int[] startLocation, int[] direction,TileColour colour){
        if (direction[0]==0 && direction[1]==0){
            return startLocation;
        }
        int xCoordinate = startLocation[0]+direction[0];
        int yCoordinate = startLocation[1]+direction[1];
        boolean valid = false;
        Cell newCell;
        TileColour[] duplicatedColours = new TileColour[] {colour,colour,colour,colour};
        int[] validSquare=startLocation;
        while(xCoordinate>=0 && xCoordinate< boardWidth && yCoordinate >=0 && yCoordinate< boardHeight && !valid){
            newCell = board[xCoordinate][yCoordinate];
            if (Misc.hadMatchingColour(duplicatedColours,newCell.getTileColours()) && newCell.isUsable()){
                valid = true;
                validSquare[0] =xCoordinate;
                validSquare[1] = yCoordinate;
            }
            xCoordinate += direction[0];
            yCoordinate += direction[1];
        }
        return validSquare;
    }
}


