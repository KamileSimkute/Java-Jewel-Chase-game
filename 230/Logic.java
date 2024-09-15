/**
 * Class dedicated to determining the state of a cell.
 */
public class Logic {
    private Item returnedItem;
    private Moveable returnedMoveable;
    private Moveable[] deletetedMoveables;
    
    /**
     * Determine outcome of a cell
     * @param cell the cell being interacted with
     * @param moveA the moveable moving into the cell
     * @return the new state of the cell
     */
    public Cell resolve(Cell cell, Moveable moveA){
        cell.setMoveable(moveA);
        return cell;
    }
    /**
     * Determine outcome of a cell
     * @param cell the cell being interacted with
     * @param moveA the moveable moving into the cell
     * @param moveB the moveable already in the cell
     * @return the new state of the cell
     */
    public Cell resolve(Cell cell, Moveable moveA, Moveable moveB){
        switch (moveA.getDangerLevel()){
            case 0:
                if (moveB.getDangerLevel() ==moveA.getDangerLevel()){
                    cell.setMoveable(moveA);
                }//else kill A
                deletetedMoveables = new Moveable[] {moveA};
                break;
            case 1:
                if (moveB.getDangerLevel() <moveA.getDangerLevel()){
                    deletetedMoveables = cell.getAllMoveables();
                    cell.removeAllMoveables();
                    cell.setMoveable(moveA);// b is killed

                }else if(moveB.getDangerLevel() <moveA.getDangerLevel()){
                        cell.setMoveable(moveA); 
                }// else kill A
                deletetedMoveables = new Moveable[] {moveA};
               break;
            case 2:
                if(moveB.getDangerLevel() ==moveA.getDangerLevel()){//b is also an explosion
                    cell.setMoveable(moveA); 
                } else{//kill B
                    deletetedMoveables = cell.getAllMoveables();
                    cell.removeAllMoveables();
                    cell.setMoveable(moveA);
                }
               break;
            default:
                throw new IllegalArgumentException("variable DANGER_LEVEL out of scope. Must be 0,1 or 2");   


        }
        return cell;


    }
    /**
     * Determine outcome of a cell
     * @param cell the cell being interacted with
     * @param moveA the moveable moving into the cell
     * @param item the item in the cell
     * @return the new state of the cell
     */
    public Cell resolve(Cell cell, Moveable moveA, Item item){
        //B must be an assassin
        switch (moveA.getDangerLevel()){
            case 0:
                returnedMoveable = moveA;
                returnedItem =  item;
                break; 
            case 1: 
                break;
            case 2: 
                if (item.getClass() == Bomb.class){
                    item.action(false);
                } else if(item.getClass() == Door.class){
                    //dont delete door
                } else{
                    cell.removeItem();
                }
                
                break;
            default: 
                throw new IllegalArgumentException("variable DANGER_LEVEL out of scope. Must be 0,1 or 2");   
        }
        return cell;
    }
    /**
     * Determine outcome of a cell
     * @param cell the cell being interacted with
     * @param moveA the moveable moving into the cell
     * @param moveB the moveable already in the cell
     * @param item the item in the cell
     * @return the new state of the cell
     */
    public Cell resolve(Cell cell, Moveable moveA, Moveable moveB, Item item){
        //B must be an assassin
        switch (moveA.getDangerLevel()){
            case 0: // B kills A
                deletetedMoveables = new Moveable[] {moveA};
                break;
            case 1: // A and b same lethality therefore no conflict, moveA also added to cell
                cell.setMoveable(moveA);
                break;
                
            case 2: // A kills B and item (if item is a bomb, detonate item)
                deletetedMoveables = cell.getAllMoveables();
                cell.removeAllMoveables();
                if (item.getClass() == Bomb.class){
                    item.action(false);
                } else if(item.getClass() == Door.class){
                    //dont delete door
                } else{
                    cell.removeItem();
                }
                break;
            default: 
                    throw new IllegalArgumentException("variable DANGER_LEVEL out of scope. Must be 0,1 or 2");  
        } 
        return cell;
    }
    /**
     * @return any movables that were deleted.
     */
    public Moveable[] getDeletedMoveables(){
        Moveable[] deleted = this.deletetedMoveables;
        this.deletetedMoveables =null;
        return deleted;
    }
    /**
     * @return any moveables that picked up an item
     */
    public Moveable getReturnedMoveable(){
        Moveable returnMoveable = this.returnedMoveable;
        this.returnedMoveable = null;
        return returnMoveable;
    }
    /**
     * @return any items that were piked up
     */
    public Item getReturnedItem(){
        Item returnItem = this.returnedItem;
        this.returnedItem = null;
        return returnItem;
    }
}