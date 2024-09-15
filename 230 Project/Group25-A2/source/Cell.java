/**
 * Cell is the individual pieces in the game grid.
 * @author James Wheeler
 */
public class Cell {
    
    private TileColour[] tileColours;
    private Item item;
    private Moveable[] moveables = new Moveable[2]; // if there are ever 2 moveables they are the same general type, behaves like a queue
    private boolean useableCell = true;
    private boolean flagged = false;
    private int[] flaggedBy;//location of flagging bomb

    /**
     *  Constructor
     * @param tileColours array of 4 enum colours for the cell
     */
    public Cell(TileColour[] tileColours) {
        this.tileColours = tileColours;
    }

    /**
     *
     * @return returns the first movable in the array of possible 2 movables
     */
    public Moveable getFirstMoveable(){
        Moveable returnedMoveable = this.moveables[0];
        if (moveables[1] != null){// if holding 2
            this.moveables[0] = this.moveables[1];
            this.moveables[1] = null;
        }
        return returnedMoveable;
    }
    /**
     *
     * @return returns the all moveables stored
     */
    public Moveable[] getAllMoveables(){
        return this.moveables;
    }



    /**
     * removes the first movable in the array of two.
     */
    public void removeFirstMoveable(){
        this.moveables[0] = null;
        if (moveables[1] != null){// if holding 2
            this.moveables[0] = this.moveables[1];
            this.moveables[1] = null;
        }
    }

    /**
     *
      * @return the item held in cell
     */
    public Item getItem(){
        return this.item;
    }

    /**
     *
     * @return returns array of four enum tile colours
     */
    public TileColour[] getTileColours() {
        return this.tileColours;
    }

    /**
     *
     * @return returns true if the item in the cell is a class equality of bomb
     */
    public boolean hasBomb() {
        return (item.getClass() == Bomb.class);
    }

    /**
     * remove the item and set it to null
     */
    public void removeItem() {
        this.item = null;
    }


    /**
     * Takes a movable, if it exists in the movable array of the cell, it is removed.
     * @param moveable the movable we want to remove from the cell
     */
    public void removeMoveable(Moveable moveable) {
        if (this.moveables[0] == moveable){
            this.moveables[0] = null;
        } else{
            this.moveables[0] = this.moveables[1];
            this.moveables[1] = null;
        }
    }


    /**
     * remove all the movables in the array of two
     */
    public void removeAllMoveables() {
        this.moveables[0] = null;
        this.moveables[1] = null;
    }

    /**
     * Set item in the cell
     * @param item item wanting to be set into cell
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Sets a cell has having bomb on or adjacent to it
     * @param bombLocation int[2] x and y axis of a bomb
     */
    public void setFlagged(int[] bombLocation){
        this.flagged = true;
        this.flaggedBy = bombLocation;

    }

    /**
     *
     * @return returns boolean if it is flagged
     */
    public boolean getFlagged(){
        return this.flagged;
    }

    /**
     * Returns what cell flagged this cell and a bomb nearby.
     * @return coordinates of what cell flagged it
     */
    public int[] getFlaggedBy(){
        return this.flaggedBy;
    }
    /**
     * 
     * @return return the number of moveables held
     */
    public int getNumMoveables(){
        int count = 0;
        for (Moveable moveable : moveables) {
            if (null != moveable) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * removes a bomb flag
     */
    public void removeFlag(){
        this.flagged =false;
        this.flaggedBy = null;
    }

    /**
     * Sets a movable into the cell
     * @param moveable the movable we want to set into cell
     */
    public void setMoveable(Moveable moveable) {
        if (moveables[0] == null){
            this.moveables[0] = moveable;
        } else{
            this.moveables[1] = moveable;
        }
    }

    /**
     *
     * @return returns true if there is a movable in the cell
     */
    public boolean hasMoveable() {
        return this.moveables.length > 0;
    }

    /**
     *
     * @return returns true if there is an item in the cell
     */
    public boolean hasItem() {
        return this.getItem() != null;
    }

    /**
     *
     * @return returns true if there is item and the item is a collectable, and it is loot
     */
    public boolean isItemLoot() {
        boolean isLoot = false;
        String itemName =  this.getItem().toString();
        switch (itemName) {
            case "SILVER_CENT_COIN_VALUE", "diamond", "ruby", "goldCoin" -> isLoot = true;
        }

        return isLoot;
    }

    /**
     * Mark the cell as not able to be moved onto.
     */
    public void setUnusable() {
        this.useableCell = false;
    }


    /**
     *
     * @return returns true if useableCell is true
     */
    public boolean isUsable(){
        return this.useableCell;
    }
    /**
     * Sets a squares to useable
     */
    public void setUseable(){
        this.useableCell=true;
    }

    /**
     *
     * @return returns data about variables within it
     */
    public String toString(){
        
        StringBuilder returnString = new StringBuilder("(");
        //for (int i=0;i<4;i++ ){
          //  returnString.append(Misc.tileColourToStringAbbreviated(tileColours[i]));
        //}
        if(this.flagged){
         returnString.append(this.flaggedBy[0]+" "+this.flaggedBy[1]);
        }
        returnString.append(")");
        if (this.item != null){
            returnString.append(", ").append(this.item);
        }
        if (this.moveables[0] != null){
            returnString.append(", ").append(this.moveables[0]);
        }
        
        return returnString.toString();
    }
    

}
