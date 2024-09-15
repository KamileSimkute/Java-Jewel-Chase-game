import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class



/** 
 * Class responsible for converting a game file into format for game to use. 
*/
public class FileManager {
    private int timer;
    private int score;
    private int height;
    private int width;
    private String[][] cellColours;
    private String[][] entities;
    private String loadedFile;

    /**
     * Constructor.
     * @param filename file to read from.
     */
    public FileManager(String filename) {
        this.loadedFile = filename;
    }
    /**
     * Read the file and break it up into its component parts.
     */
    public void readLevelFile(){
        try {
            File loadFile = new File(loadedFile);
            Scanner fileReader = new Scanner(loadFile);
            //get gametime and score
            Scanner lineReader = new Scanner(fileReader.nextLine());
            this.score = lineReader.nextInt();
            this.timer = lineReader.nextInt();
            lineReader.close();
            //get length and width
            lineReader = new Scanner(fileReader.nextLine());
            this.width = lineReader.nextInt();
            this.height = lineReader.nextInt();
            lineReader.close();
            // Loop through colours
            int xIter = 0;
            int yIter = 0;
            this.cellColours = new String[width][height];
            do {
                lineReader = new Scanner(fileReader.nextLine());
                while(lineReader.hasNext() && xIter != this.width){
                    this.cellColours[xIter][yIter] = lineReader.next();
                    xIter++;
                }
                lineReader.close();
                yIter++;
                xIter =0;
            } while(fileReader.hasNextLine() && yIter < this.height);
            fileReader.nextLine(); // skip empty line
            //read entities
            ArrayList<String[]> tempEntities = new ArrayList<String[]>();
            while (fileReader.hasNextLine()) {
                lineReader = new Scanner(fileReader.nextLine());
                String[] entityAsString = new String[5];
                int entityIterator =0;
                while(lineReader.hasNext()){
                    entityAsString[entityIterator] = lineReader.next();
                    entityIterator ++;

                }
                tempEntities.add(entityAsString);
                lineReader.close();
            }
            this.entities = new String[tempEntities.size()][5];
            for (int i = 0; i < tempEntities.size(); i++) {
                String[] row = tempEntities.get(i);
                entities[i] = row;
            }
            
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error 404 file not found.");
            e.printStackTrace();
        }
    }
    /**
     * Getter for the level time
     * @return time
     */
    public int getTimer() {
        return timer;
    }
    /**
     * Getter for the current score.
     * @return the score.
     */
    public int getScore() {
        return score;
    }
    /**
     * Getter for the height of the grid.
     * @return grid height
     */
    public int getHeight() {
        return height;
    }
    /**
     * Getter for the width of the grid.
     * @return grid width
     */
    public int getWidth() {
        return width;
    }
    /**
     * Getter for the colour array
     * @return colours
     */
    public String[][] getColours() {
        return cellColours;
    }
    /**
     * Getter for objects containted within the grid
     * @return object array
     */
    public String[][] getEntities() {
        return entities;
    }
    /**
     * Saves the game to a file.
     * @param game the game to save
     */
    public static void save(Game game){
        String colour = "";
        String attribute = "";
        String nln = "\r\n";
        String space = " ";

        try {
            FileWriter myWriter = new FileWriter("LevelSave.txt");
            myWriter.write(game.getScore() + " " + game.getTime()+ "\r\n");
            myWriter.write(game.getHeight() + " " + game.getWidth()+ "\r\n");

            for (int i=0; i<game.getHeight();i++){
                for (int j=0;j<game.getWidth();j++)
                    for (int k=0; k<4; k++){
                        myWriter.write(Misc.tileColourToStringAbbreviated(game.getBoard()[i][j].getTileColours()[k]));
                    }
                    myWriter.write(space);
                myWriter.write(nln);
            }

            for (int i=0; i<game.getHeight();i++){  
                for (int j=0; j<game.getWidth();j++){
                    if(null !=game.getBoard()[i][j].getFirstMoveable()){
                        Moveable moveable =game.getBoard()[i][j].getFirstMoveable();
                        if (moveable.getForwardDirection() != null){
                            String direction = moveable.getForwardDirectionAsString();
                            attribute = direction;
                            break;
                        }
                        if (moveable.getClass()== FloorFollowingThief.class){
                            colour= Misc.tileColourToStringAbbreviated(((FloorFollowingThief)moveable).getColour());
                        }
                            
                        myWriter.write( game.getBoard()[i][j].getFirstMoveable().toString() + " " + i + " " + j + " " + attribute + " "+colour+"\r\n");
                    }   
                     if(null !=game.getBoard()[i][j].getItem()){
                        Item item =game.getBoard()[i][j].getItem();
                        if (item.getColour() != null){
                            colour = item.getColour();
                            attribute = colour;
                            break;
                        }
                        myWriter.write( game.getBoard()[i][j].getFirstMoveable().toString() + " " + i + " " + j + " " + attribute + "\r\n");
                    }
                }
            myWriter.close();
            }
        
            System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            }
    }
}