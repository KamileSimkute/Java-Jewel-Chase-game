
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

/**
 * Main class that the player will navigate in order to ply the game
 */
public class Menu extends Application{
    // The dimensions of the window
    private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;

	// The dimensions of the canvas
	private static final int MENU_WIDTH = 600;
	private static final int MENU_HEIGHT = 400;

    private Canvas canvas;
	private Stage primaryStage;
    // List of the names of level files
    private String[] levels = {"Levelfile.txt","Level2.txt","level3.txt","level4.txt"};
	private String save = "LevelSave.txt";

	/** 
	 * method initially called upon game startup.
	 * @param primaryStage tge stage on which to draw.
	*/
    public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		// Build the GUI 
		Pane root = startMenu();
		
		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		// Display the scene on the stage
		primaryStage.setScene(scene);
		primaryStage.setTitle("Gem Thief");
		primaryStage.show();
	}
	
	/** 
	 * @param newMenu
	 */
	public void changeMenu(Pane newMenu){
		Scene scene = new Scene(newMenu, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		// Display the scene on the stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}

    /**
	 * Create the initial GUI.
	 * @return The panel that contains the created GUI.
	 */
	private Pane startMenu() {
		// Create top-level panel that will hold all GUI nodes.
		BorderPane startRoot = new BorderPane();
				
		// Create the canvas that we will draw on.
		// We store this as a gloabl variable so other methods can access it.
		canvas = new Canvas(MENU_WIDTH, MENU_HEIGHT);
		startRoot.setCenter(canvas);
		
		VBox startMenu = new VBox();
		startMenu.setSpacing(10);
		startMenu.setPadding(new Insets(10, 10, 10, 10)); 
		startMenu.setAlignment(Pos.CENTER);
		startRoot.setTop(startMenu);
		
		// Create the startmenu content
		Text message = new Text(MessageOfTheDay.getMsgDay());
		Button selectLevelButton = new Button("Select Level");
        selectLevelButton.setMinWidth(MENU_WIDTH);

		Button loadSaveButton = new Button("Load Saved Game");
		loadSaveButton.setMinWidth(MENU_WIDTH);	

		Button viewScoreBoardButton = new Button("View Scoreboard");
		viewScoreBoardButton.setMinWidth(MENU_WIDTH);	

		Button exitButton = new Button("Exit");
		exitButton.setMinWidth(MENU_WIDTH);	
		
		// Setup the behaviour of the buttons.
		selectLevelButton.setOnAction(e -> {
			changeMenu(levelSelectPane());
		});
		loadSaveButton.setOnAction(e -> {
			load();
		});
		viewScoreBoardButton.setOnAction(e -> {
			viewScoreBoard();
		});
		exitButton.setOnAction(e -> {
			exit();
		});
		startMenu.getChildren().addAll(message,selectLevelButton, viewScoreBoardButton,loadSaveButton,exitButton);	

        return startRoot;
	}
    /**
	 * Pane for selecting levels.
	 * @return pane to diaplay
	 */
	private Pane levelSelectPane(){
		// Create top-level panel that will hold all GUI nodes.
		BorderPane levelPane = new BorderPane();
				
		// Create the canvas that we will draw on.
			// We store this as a gloabl variable so other methods can access it.
		canvas = new Canvas(MENU_WIDTH, MENU_HEIGHT);
		levelPane.setCenter(canvas);
		
		VBox levelMenu = new VBox();
		levelMenu.setSpacing(10);
		levelMenu.setPadding(new Insets(10, 10, 10, 10)); 
		levelMenu.setAlignment(Pos.CENTER);
		levelPane.setTop(levelMenu);
		Button levelOneButton = new Button("Level 1");
		levelOneButton.setMinWidth(MENU_WIDTH);
		Button levelTwoButton = new Button("Level2");
		levelTwoButton.setMinWidth(MENU_WIDTH);	

		Button levelThreeButton = new Button("Level3");
		levelThreeButton.setMinWidth(MENU_WIDTH);	

		Button levelFourButton = new Button("Level4");
		levelFourButton.setMinWidth(MENU_WIDTH);	
		
		
		Button returnButton = new Button("Return");
		returnButton.setMinWidth(MENU_WIDTH);	
			
		// Setup the behaviour of the buttons.
		levelOneButton.setOnAction(e -> {
			startGame(this.levels[0]);
		});
		levelTwoButton.setOnAction(e -> {
			startGame(this.levels[1]);
		});
		levelThreeButton.setOnAction(e -> {
			startGame(this.levels[2]);
		});
		levelFourButton.setOnAction(e -> {
			startGame(this.levels[3]);
		});
		returnButton.setOnAction(e -> {
			changeMenu(startMenu());
		});
		levelMenu.getChildren().addAll(levelOneButton, levelTwoButton,levelThreeButton,levelFourButton,returnButton);	
	
		return levelPane;
	}	
    /**
	 * pane for when the game has been paused
	 * @param the paused game
	 * @return the game pane
	 */
	private Pane pauseMenuPane(Game game) {
		BorderPane pauseRoot = new BorderPane();
		VBox pauseMenu = new VBox();
		pauseMenu.setSpacing(10);
		pauseMenu.setPadding(new Insets(10, 10, 10, 10)); 
		pauseMenu.setAlignment(Pos.CENTER);
		pauseRoot.setTop(pauseMenu);
		
		// Create the pausemenu content
		Button resumeLevelButton = new Button("Resume Game");
        resumeLevelButton.setMinWidth(MENU_WIDTH);

		Button saveGameButton = new Button("Save Game");
		saveGameButton.setMinWidth(MENU_WIDTH);	

		Button quitToMenuButton = new Button("Exit to Main Menu");
		quitToMenuButton.setMinWidth(MENU_WIDTH);	

		Button exitButton = new Button("Exit Game");
		exitButton.setMinWidth(MENU_WIDTH);	
		
		// Setup the behaviour of the buttons.
		resumeLevelButton.setOnAction(e -> {
			resumeGame(game);
		});
		saveGameButton.setOnAction(e -> {
			//saveGame(game);
			exit();
		});
		quitToMenuButton.setOnAction(e -> {
			changeMenu(startMenu());
		});
		exitButton.setOnAction(e -> {
			exit();
		});
		pauseMenu.getChildren().addAll(resumeLevelButton, saveGameButton,quitToMenuButton,exitButton);	

        return pauseRoot;
	}
    /**
	 * Iitialises a game from the given file.
	 * @param filename
	 */
	public void startGame(String filename){
		Game game = new Game(filename,primaryStage);
		game.unpauseGame();
		if (game.isPaused()){
            changeMenu(pauseMenuPane(game));
		}
	}
	/**
	 * Continue the tick method of a game that has been paused.
	 * @param game te game that was paused
	 */
	public void resumeGame(Game game){
		game.unpauseGame();
		if (game.isPaused()){
            changeMenu(pauseMenuPane(game));
		}
	}
	/**
	 * not implemented
	 */
	public void viewScoreBoard(){
		System.out.println("Not Implemented");
	}
	/**
	 * Attempts to load a saved game.
	 */
	public void load(){
		startGame(this.save);
	}
	/**
	 * Quits game and ends execution
	 */
	public void exit(){
		Platform.exit();
	}
	
	/** 
	 * @param args[]
	 */
	public static void main(String args[]){
		launch(args);

	}
	


}
