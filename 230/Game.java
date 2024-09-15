import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Game contains the board of cells and handles the game tick method.
 * @author Joseph Ryan,James Wheeler
 */
public class Game {

    private int score = 0;
    private int boardHeight;
    private int boardWidth;
    private int remainingLoot;//remaining collectables
    private final Logic gameLogic;
    private final FileManager fileManager;
    private final GameGraphics graphics;
    private Cell[][] gameBoard;
    private int[] doorLocation = new int[2];
    private double remainingTime;
    private final int GAME_SPEED = 1000;
    private final double GAME_TICK_SPEED = GAME_SPEED/1000;
    private final Timeline tickTimeline;
    private boolean over;
    private boolean paused = false;
    private Player player;

    /**
     * Creates the board and initialises the game logic and file manager. Starts the clock which updates movables.
     * @param gameFile location of the file with game construct data
     */
    public Game(String gameFile,Stage graphicsStage) {
        this.gameLogic = new Logic();
        this.fileManager = new FileManager(gameFile);
        this.graphics = new GameGraphics(graphicsStage);
        InitializeLevel();
        // calls onTick every 100ms
        System.out.println(toString());
        this.tickTimeline = new Timeline(new KeyFrame(Duration.millis(GAME_SPEED), event -> onTick()));
        this.tickTimeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
    * Pauses the game
    */
    public void pauseGame() {
        this.paused = true;
        this.tickTimeline.pause();
    }

    /**
    * Resumes the game
     */
    public void unpauseGame() {
        this.paused = false;
        this.tickTimeline.play();
    }
    public boolean isPaused(){
        return this.paused;
    }

    /**
    * Ticks at the rate set in the constructor, each tick look for moveables and move them.
    */
    private void onTick() {
        if (this.over){
            return;
        }
        //decrement time
        this.remainingTime -= GAME_TICK_SPEED;
        // game over as out of time
        if (remainingTime <= 0){
            boolean noPlayer = false;
            gameOver(noPlayer);
            return;
        }
        // process all bomb on map
        handleBombs();
        String input = graphics.update(this);
        if (input=="P"){
            pauseGame();
        } else{
            player.setForwardDirection(input);
        }
        //find all moveables
        ArrayList<int[]> entityLocations = new ArrayList<>();
        for (int i=0; i<boardHeight;i++){
            for (int j=0; j<boardWidth;j++){
                int[] location = new int[]{i,j}; 
                if(null !=gameBoard[i][j].getFirstMoveable()){
                    entityLocations.add(location);
                }
            }
        }
        for (int i=0;i<entityLocations.size();i++){
            int x = entityLocations.get(i)[0];
            int y =entityLocations.get(i)[1];
            if(null !=gameBoard[x][y].getFirstMoveable()){
                boolean legalMove = true;
                final int MAX_ALLOWED_ATTEMPTS =4;
                int attempNo =0;
                int[]move;
                Moveable moving;
                boolean jumped =false;
                do {//try to move 4 times(all 4 directions)
                    moving = gameBoard[x][y].getFirstMoveable();
                    if (moving.getClass() == SmartThief.class){
                        move = ((SmartThief)moving).move(legalMove,gameBoard,boardWidth,boardHeight,entityLocations.get(i));
                    } else{
                        move = moving.move(legalMove);
                    }
                    move = Misc.vectorToBoardVector(move);
                    if (moving.getClass() == FloorFollowingThief.class){
                        legalMove = Misc.validate(gameBoard, boardWidth,boardHeight,entityLocations.get(i),move,((FloorFollowingThief)moving).getColour());
                    } else if (moving.getClass() == Explosion.class && !legalMove){
                        gameBoard[x][y].removeFirstMoveable();
                        attempNo +=100;
                    } else{
                        legalMove = Misc.validate(gameBoard, boardWidth,boardHeight,entityLocations.get(i),move,moving.followsRules());
                    }
                    
                    attempNo +=1;
                } while (!legalMove && attempNo < MAX_ALLOWED_ATTEMPTS);
                if (legalMove){
                    int[] newLocation;
                    if (jumped){
                        newLocation = new int[]{move[0],move[1]};
                    } else{
                        newLocation = new int[]{x+move[0],y+move[1]};
                    }
                    Cell newCell = gameBoard[newLocation[0]][newLocation[1]];
                    //determine the state of the cell
                    if (null == newCell.getItem()){
                        if(null == newCell.getFirstMoveable()){
                            newCell = gameLogic.resolve(newCell, gameBoard[x][y].getFirstMoveable());
                        } else {
                            newCell = gameLogic.resolve(newCell, gameBoard[x][y].getFirstMoveable(),newCell.getFirstMoveable());
                        }
                    } else if (null == newCell.getFirstMoveable()){
                            newCell = gameLogic.resolve(newCell, gameBoard[x][y].getFirstMoveable(),newCell.getItem());
                    } else{
                        newCell = gameLogic.resolve(newCell, gameBoard[x][y].getFirstMoveable(),newCell.getFirstMoveable(),newCell.getItem());
                    }
                    gameBoard[x][y].removeFirstMoveable();
                    bombCheck(newCell);
                    doorCheck();
                    gameBoard[newLocation[0]][newLocation[1]] = newCell;
                    manageItemPickup(gameLogic.getReturnedMoveable(),newCell);
                }
            }
        }
    }

    /**
     * Returns the players score
     * @return score as value of items collected and summed up.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets time left until game is over
     * @return time left
     */
    public double getTime(){
        return this.remainingTime;
    }
    /**
     * Get the grid of cells
     * @return cell grid
     */
    public Cell[][] getBoard(){
        return this.gameBoard;
    }
    /**
     * Get Width of the board
     * @return board width
     */
    public int getWidth(){
        return this.boardWidth;
    }
    /**
     * Get height of the board
     * @return board height
     */
    public int getHeight(){
        return this.boardHeight;
    }

    /**
     * Determines the outcome after an item is interacted with
     * @param entity the moveable who picked up the item
     * @param cell the cell which the movable moved onto, where item was placed
     */
    public void manageItemPickup(Moveable entity,Cell cell){
        Item item=  cell.getItem();
        if (null == entity || null == item){
            return;
        }
        if (item.getClass()==Loot.class){
            this.score += ((Collectable)item).getValue(entity.checkIsPlayer());
            cell.removeItem();
            remainingLoot-=1;

        } else if (item.getClass() == Clock.class){
            this.score += ((Collectable)item).getValue(entity.checkIsPlayer());
            cell.removeItem();

        } else if (item.getClass() == Lever.class){
            // find corresponding coloured gate and remove it
            String leverColour = ((Lever)item).getColour();
            for (int i=0; i<boardHeight;i++){
                for (int j=0; j<boardWidth;j++){
                    if(null ==gameBoard[i][j].getItem()){

                    } else if (gameBoard[i][j].getItem().getClass() == Gate.class){
                        if (Objects.equals(((Gate) gameBoard[i][j].getItem()).getColour(), leverColour)){
                            gameBoard[i][j].removeItem();
                            gameBoard[i][j].setUseable();
                            i--;
                        }
                    }
                }
            }
            cell.removeItem();
            remainingLoot-=1;

        } else if (item.getClass() == Door.class){
            if (((Door)item).isOpen()){
                gameOver(entity.checkIsPlayer());
            }

        }
        cell.setMoveable(entity);
    }


    /**
     * Handle bombs by decrementing the time left on them. If time is zero then the generateExplosions method is called.
     */
    private void handleBombs(){
        // cycle through each cell
        for (int i=0; i<boardHeight;i++){
            for (int j=0; j<boardWidth;j++){
                if(gameBoard[i][j].getItem() == null){

                } else if (gameBoard[i][j].getItem().getClass() == Bomb.class){ // when the cell contains a bomb
                    if (((Bomb) gameBoard[i][j].getItem()).isTriggered()){
                        // decrement time by 1 if already triggered
                        ((Bomb) gameBoard[i][j].getItem()).tick();
                    }
                    if (((Bomb) gameBoard[i][j].getItem()).getTimeRemaining() == 0){
                        // explodes if time on the bomb is out.
                        int[] bombBlocation = new int[] {i,j};
                        generateExplosions(bombBlocation);
                        i--;//may cause error
                    }
                }
            }
        }
        
    }

    /**
     * Check a cell for an adjacent bomb.
     * @param cell cell we want to check
     */
    private void bombCheck(Cell cell){
        if(cell.getFlagged()){
            int[] bombLocation = cell.getFlaggedBy();
            System.out.println(bombLocation[0]+"B"+bombLocation[1]);
            gameBoard[bombLocation[0]][bombLocation[1]].getItem().action(false);//bomb does not care who detonated it
            removeFlags(bombLocation);
        }
    }

    /**
     * Generate explodsions in all adjacent cells.
     * @param bombLocation coordinate of the bomb
     */
    private void generateExplosions(int[] bombLocation){
        String[] directions = new String[] {"UP","DOWN","RIGHT","LEFT"};
        // cycle through all adjacent cells for bomb explosion
        Explosion explosion;
        try {
            explosion = new Explosion(directions[0]);
            gameBoard[bombLocation[0]-1][bombLocation[1]] =gameLogic.resolve(gameBoard[bombLocation[0]-1][bombLocation[1]],explosion);
        }catch (IndexOutOfBoundsException e){}// dont create if indxeed is out of grid
        try{
            explosion = new Explosion(directions[1]);
            gameBoard[bombLocation[0]+1][bombLocation[1]] = gameLogic.resolve(gameBoard[bombLocation[0]+1][bombLocation[1]],explosion);
        }catch (IndexOutOfBoundsException ex){}
        try{
            explosion = new Explosion(directions[2]);
            gameBoard[bombLocation[0]][bombLocation[1]+1] = gameLogic.resolve(gameBoard[bombLocation[0]][bombLocation[1]+1],explosion);
        }catch (IndexOutOfBoundsException exe){}
        try{
            explosion = new Explosion(directions[3]);
            gameBoard[bombLocation[0]][bombLocation[1]-1] = gameLogic.resolve(gameBoard[bombLocation[0]][bombLocation[1]-1],explosion);
        }catch (IndexOutOfBoundsException exept){}
        gameBoard[bombLocation[0]][bombLocation[1]].removeItem();
        gameBoard[bombLocation[0]][bombLocation[1]].setUseable();
    }

    /**
     * If all the items have been picked up, open the door.
     */
    private void doorCheck(){
        Door door = ((Door) gameBoard[doorLocation[0]][doorLocation[1]].getItem());
        // opens when player has collected all the loot and the door isn't already open
        if (this.remainingLoot ==0 && !door.isOpen()){
            door.open();
        }
    }

    /**
     * Generates all the cells from the map height and width. Populates the cells with their colours.
     */
    private void generateCells() {
        // cycle through length and populate the board with the cells
        for (int x = 0; x < boardHeight; x++) {
            for (int y = 0; y < boardHeight; y++) {
                TileColour[] cellColours = new TileColour[4];
                for (int i = 0; i < 4; i++) {
                    cellColours[i] = Misc.tileColourFromString(Character.toString(this.fileManager.getColours()[x][y].charAt(i)));
                }
                this.gameBoard[y][x] = new Cell(cellColours);
            }
        }
    }

    /**
     * Initialises the level by reading from the game file and placing the entities and items in their cells.
     */
    private void InitializeLevel() {
        this.fileManager.readLevelFile();
        this.score = fileManager.getScore();
        this.remainingTime = fileManager.getTimer();
        this.boardHeight =fileManager.getHeight();
        this.boardWidth = fileManager.getWidth();

          // initiate the gameBoard with size
        this.gameBoard = new Cell[boardHeight][boardHeight];

        this.generateCells();

        String[][] entities = fileManager.getEntities();
        String entityName;
        int[] coords = new int[2];
        String auxillory;
        String auxilloryB;
        // cycle through entities in the game file
        for (String[] entity : entities) {
            // gets specific entity
            entityName = entity[0];
            coords[0] = Integer.parseInt(entity[1]);
            coords[1] = Integer.parseInt(entity[2]);
            auxillory = entity[3];
            auxilloryB = entity[4];// null for all but floor following thief
            switch (entityName) {
                //items
                case "SilverCoin", "GoldCoin", "Ruby", "Diamond":
                    Loot loot = new Loot(entityName);
                    this.gameBoard[coords[0]][coords[1]].setItem(loot);
                    remainingLoot+=1;
                    break;
                case "Clock":
                    Clock clock = new Clock(Integer.parseInt(auxillory));
                    this.gameBoard[coords[0]][coords[1]].setItem(clock);
                    break;
                case "Lever":
                    Lever lever = new Lever(auxillory);
                    this.gameBoard[coords[0]][coords[1]].setItem(lever);
                    remainingLoot+=1;
                    break;
                case "Gate":
                    Gate gate = new Gate(auxillory);
                    this.gameBoard[coords[0]][coords[1]].setItem(gate);
                    this.gameBoard[coords[0]][coords[1]].setUnusable();
                    break;
                case "Door":
                    Door door = new Door();
                    this.gameBoard[coords[0]][coords[1]].setItem(door);
                    this.doorLocation[0] = coords[0];
                    this.doorLocation[1] = coords[1];
                    break;
                case "Bomb":
                    Bomb bomb = new Bomb();
                    this.gameBoard[coords[0]][coords[1]].setItem(bomb);
                    int[] bombAt =new int[]{Integer.parseInt(entity[1]),Integer.parseInt(entity[2])};
                    this.gameBoard[coords[0]][coords[1]].setUnusable();
                    generateFlags(bombAt);
                    break;
                //movables
                case "FlyingAssassin":
                    FlyingAssassin assasin = new FlyingAssassin(auxillory);
                    this.gameBoard[coords[0]][coords[1]].setMoveable(assasin);
                    break;
                case "Explosion":
                    Explosion explosion = new Explosion(auxillory);
                    this.gameBoard[coords[0]][coords[1]].setMoveable(explosion);
                    break;
                case "FloorFollowingThief":
                    TileColour colourToFollow = Misc.tileColourFromString(auxilloryB);
                    FloorFollowingThief floorThief = new FloorFollowingThief(auxillory, colourToFollow);
                    this.gameBoard[coords[0]][coords[1]].setMoveable(floorThief);
                    break;
                case "SmartThief":
                    SmartThief smartThief = new SmartThief(auxillory);
                    this.gameBoard[coords[0]][coords[1]].setMoveable(smartThief);
                    break;
                case "Player":
                    Player player = new Player();
                    this.gameBoard[coords[0]][coords[1]].setMoveable(player);
                    this.player = player;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown entity found within file.");
            }
            
        }
    }

    /**
     * Marks all cells adjacent to a bom as flagged
     * @param coords coordinates of a bomb
     */
    private void generateFlags(int[] coodinates) {
        // set adjacent cells to flagged
        int x = coodinates[0];
        int y = coodinates[1];
        try {
            this.gameBoard[x + 1][y].setFlagged(coodinates);
            this.gameBoard[x - 1][y].setFlagged(coodinates);
            this.gameBoard[x][y + 1].setFlagged(coodinates);
            this.gameBoard[x][y - 1].setFlagged(coodinates);
        } catch (IndexOutOfBoundsException e){
            // the index of a flag is outside the map
        }
    }

    /**
     * Removes flags on cells adjacent to a bomb
     * @param coords coordinates of the cell to remove flags from
     */
    private void removeFlags(int[] coords) {
        // called upon bomb detonation
        // remove adjacent flags on cells
        int x = coords[0];
        int y = coords[1];
        try {
            this.gameBoard[x+1][y].removeFlag();
            this.gameBoard[x-1][y].removeFlag();
            this.gameBoard[x][y+1].removeFlag();
            this.gameBoard[x][y-1].removeFlag();
        } catch (IndexOutOfBoundsException e){
            // the index of a flag is outside the map
        }
    }

    /**
     * Ends the game; stopping timeline and setting variable as over.
     * @param playerExited whether the interacting entity is a player.
     */
    public void gameOver(boolean playerExited){
        graphics.update(this);
        this.tickTimeline.pause();
        System.out.println(score);
        System.out.println(remainingTime);
        if (playerExited){
            System.out.println("you win");
        } else{
            System.out.println("you lose");
        }
        this.over = true;
    }

    @Override
    public String toString(){
        StringBuilder returnedString = new StringBuilder();
        for (int y=0;y<boardHeight;y++){
            for(int x=0; x<boardWidth;x++){
                returnedString.append("[").append(gameBoard[y][x].toString()).append("], ");
            }
            returnedString.append("\n");
            

        }
        return returnedString.toString();

    }
}