
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Class dedicated to drawing the game during runtime
 */
public class GameGraphics {

	// The dimensions of the canvas
    private Stage primaryStage;
    private String input = "";
    public GameGraphics(Stage primaryStage){
        this.primaryStage = primaryStage;
        

    }
    /**
     * Redraws the game pane to reflect changes in the game.
     * @param game game to read from
     * @return any keyinputs recieved.
     */
    public String update(Game game) {

		// Build the GUI 
		Pane root = gamePane(game,game.getWidth(),game.getHeight());
		// Create a scene from the GUI
		Scene scene = new Scene(root, game.getWidth()*102, (game.getHeight()*102)+50);
        scene.setOnKeyPressed((KeyEvent event) -> {
            takeInput(event);
        });
		// Display the scene on the stage
		primaryStage.setScene(scene);
		primaryStage.show();
        String inputString = input;
        input = "";
        return inputString;

	}
    /**
     * Generates a graphical display of the game
     * @param game the game to draw
     * @param boardWidth width of the grid
     * @param boardHeight height of the grid
     * @return pane for the game
     */
    private Pane gamePane(Game game,int boardWidth,int boardHeight) {
		// Create top-level panel that will hold all GUI nodes.
        GridPane startRoot = new GridPane();
		// Create the canvas that we will draw on.
		// We store this as a gloabl variable so other methods can access it.
		startRoot.setPrefHeight((boardHeight*102)+50);
        startRoot.setPrefWidth(boardWidth*102);
        Text time = new Text(""+game.getTime());
        Text score = new Text(""+game.getScore());
        GridPane cellPane;
        for(int i=0; i<boardHeight; i++){
            for(int j=0; j<boardWidth; j++){
                cellPane = new GridPane();
                cellPane.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                Rectangle cellRect;
                //clean up
                cellRect = new Rectangle(50,50);
                cellRect.setFill(Misc.tileColorTColor(game.getBoard()[j][i].getTileColours()[0]));
                cellPane.add(cellRect, 0, 0);
                cellRect = new Rectangle(50,50);
                cellRect.setFill(Misc.tileColorTColor(game.getBoard()[j][i].getTileColours()[1]));
                cellPane.add(cellRect, 0, 1);
                cellRect = new Rectangle(50,50);
                cellRect.setFill(Misc.tileColorTColor(game.getBoard()[j][i].getTileColours()[2]));
                cellPane.add(cellRect, 1, 0);
                cellRect = new Rectangle(50,50);
                cellRect.setFill(Misc.tileColorTColor(game.getBoard()[j][i].getTileColours()[3]));
                cellPane.add(cellRect, 1, 1);
                startRoot.add(cellPane, i, j);
                if (null != game.getBoard()[j][i].getItem()){
                    Image image = new Image("file:"+game.getBoard()[j][i].getItem().getSprite());
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    startRoot.add(imageView, i, j);
                }
                if (null != game.getBoard()[j][i].getFirstMoveable()){
                    Image image = new Image("file:"+game.getBoard()[j][i].getFirstMoveable().getSprite());
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    startRoot.add(imageView, i, j);
                }
            }
        }
        startRoot.add(time,0,game.getHeight());
        startRoot.add(score,1,game.getHeight());
        return startRoot;
	}

    /**
     * Reads keyboard input and ether updates the forward direction if an arrow or wasd key is pressed, or pauses if p is pressed.
     * @param event The key event that was pressed.
     */
    public void takeInput(KeyEvent event){
        String keyInput="";
        switch (event.getCode()) { 
            case UP:
                keyInput = "UP";
                break;
            case DOWN:
                keyInput = "DOWN";
                break;
            case LEFT:
                keyInput = "LEFT";
                break;
            case RIGHT:
                keyInput = "RIGHT";
                break;
            case P:
                keyInput = "P";
                break;
            default:
                break;
        }
        input = keyInput;
    }
    
}