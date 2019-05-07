package cs1302.arcade;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import javafx.scene.paint.Color;
import javafx.scene.effect.ColorInput;
/**
 *Represents a game of 2048.
 */
public class Game2048 extends Group{

    TilePane pane;
    Tile2048[][] tiles;
    ArcadeApp application;
    VBox vbox;
    HBox hbox;
    StackPane stackpane;
    Random rand;
    Button ngButton;
    Button exitButton;
    int score;
    Text scoreText;
    Text scoreNumText;
    Text gameOver;
    Text win;
    VBox scorevbox;
    ArcButton mmButton;
    ImageView background;
    ImageView GOBackground;
    boolean isWin;

    /**
     *Constructor for Game2048 class. Builds background, text,
     *and buttons.
     *@param An ArcadeApp application
     */
    public Game2048(ArcadeApp application) {
        isWin = false;
        this.application = application;
        score = 0;
        stackpane = new StackPane();
        vbox = new VBox();
        this.setUpPane();
        this.setOnKeyPressed(createKeyHandler());
        vbox.setAlignment(Pos.CENTER);
        rand = new Random();
        //Main Menu Button
        mmButton = new ArcButton(-300, 25,new Image("2048/MainMenu.png"),
                                 e -> application.setScene(application.getScene()));
        //New Game Button
        ngButton = new ArcButton(0,0,new Image("2048/NewGame.png"),
                                 e -> this.newGame());
        //Exit Button
    	exitButton = new ArcButton(300,-25, new Image("2048/exit.png"),
				   e -> application.getGameSI().getQuit().fire());
        
	    this.setUpScoreAndGameOver();
        vbox.getChildren().addAll(mmButton,ngButton,exitButton,scorevbox,hbox);
        ngButton.setOnAction(e->this.newGame());
        background = new ImageView(new Image("2048/GameBackground.png"));
        stackpane.getChildren().addAll(background,vbox,GOBackground, gameOver, win); 
        this.getChildren().addAll(stackpane);
        this.newGame();
    }

    /**
     *Sets up score and game over text and functionality. Adds and sets
     *color for font for game over and score. Creates transparent 
     *background image for game over screen.
     *
     */
    public void setUpScoreAndGameOver() {
        scoreText = new Text("Score");
        scoreNumText = new Text("0");
        gameOver = new Text("Game Over");
        scoreText.setFill(Color.rgb(10,71,18));
        scoreNumText.setFill(Color.rgb(10,71,18));
        gameOver.setFill(Color.rgb(0,0,0));
        gameOver.setOpacity(0);
        win = new Text("You Win!");
        win.setFill(Color.rgb(10,71,18));
        win.setOpacity(0);
        //Adding wood font.
        try {
            String path = "src/main/resources/2048/JFWilwod.ttf";
            FileInputStream fp = new FileInputStream(path);
            FileInputStream fp2 = new FileInputStream(path);
            FileInputStream fp3 = new FileInputStream(path);
            scoreText.setFont(Font.loadFont(fp,24));
            scoreNumText.setFont(new Font("System Bold", 24));
            gameOver.setFont(Font.loadFont(fp2,144));
            win.setFont(Font.loadFont(fp3,144));
            
        } catch(Exception e) {
            scoreNumText.setFont(new Font("System Bold", 24));
            scoreText.setFont(new Font("System Bold",24));
            gameOver.setFont(Font.loadFont("System Bold", 144));
        }
        scorevbox = new VBox();
        scorevbox.getChildren().addAll(scoreText, scoreNumText);
        scorevbox.setAlignment(Pos.CENTER);
        scorevbox.setTranslateX(230);
        GOBackground = new ImageView(new Image("2048/GOB.jpg"));
        GOBackground.setOpacity(0);
        GOBackground.setMouseTransparent(true);
    }

    /**
     *Sets up grid for a game of 2048. Sets appropriate
     *values for spacing and size for TilePane. Loops through
     *2d array of tiles and adds them to the TilePane.
     */
    public void setUpPane() {
        hbox = new HBox();
        pane = new TilePane();
        hbox.getChildren().add(pane);
        pane.setHgap(8);
        pane.setVgap(8);
        pane.setPrefColumns(4);
        pane.setPrefRows(4);
        pane.setMaxSize(560, 560);
        pane.setTranslateX(232);
        tiles = new Tile2048[4][4];
        //Adds each tile to pane
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k < 4; k++) {
                tiles[i][k] = new Tile2048(this);
                pane.getChildren().add(tiles[i][k]);
            }
        }
    }
    /**
     *Resets 2048 game. Loops through 2d array of tiles 
     *and sets all to empty. Reset all end of game pop ups
     *and then respawns starter tiles.
     */
    public void newGame() {
        //Setting to empty
        for(int i=0;i<4;i++) {
            for(int k=0;k<4;k++) {
                tiles[i][k].setEmpty(true);
                tiles[i][k].setNumber(0);
            }
        }
        gameOver.setOpacity(0);
        GOBackground.setOpacity(0);
        win.setOpacity(0);
        spawnTile();
        spawnTile();
        this.setImages();
        this.setScore(0);
        isWin = false;
    }

    /**
     *Randomly spawns tiles on the gameboard. Loops through
     *and randomly places a two or four in exactly two spots
     *on the game board.
     */
    public void spawnTile() {
        ArrayList<Tile2048> emptyImgs = new ArrayList<>();
        //Adding empty images
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k<4; k++) {
                if(tiles[i][k].isEmpty()) {
                    emptyImgs.add(tiles[i][k]);
                }
            }
        }
        //Spawning random tiles
        if(emptyImgs.size() > 0) {
            int index = rand.nextInt(emptyImgs.size());
            int number = rand.nextInt(9);
            if(number > 0) {
                emptyImgs.get(index).setImage(new Image("2048/2.png"));
                emptyImgs.get(index).setNumber(2);
            } else {
                emptyImgs.get(index).setImage(new Image("2048/4.png"));
                emptyImgs.get(index).setNumber(4);
            }
            emptyImgs.get(index).setEmpty(false);                                            
        }
    }

    /**
     *Enables movement of tiles with arrow keys and WASD.
     *
     */
    private EventHandler<? super KeyEvent> createKeyHandler() {
        return event -> {
            if(isWin) {

            } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                this.shiftLeft();
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                this.shiftRight();
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                this.shiftUp();
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                this.shiftDown();
            } 
            if(this.isFull() && this.isGameOver()) {
                this.gameOver();
            }
        };
    }

    /**
     *Sets isWin to true and sets opacity of Game Over Background
     *to 0.5, creating a fade effect on the screen.
     */
    public void win() {
        //Fade effect
        GOBackground.setOpacity(.5);
        win.setOpacity(1);
        isWin = true;
    }
    /**
     *Updates entire gameboard. Loops through 2d array of tiles
     *and sets each to its correct image based on its URL.
     *
     */
    public void setImages() {
        //Setting images based on URLS
        for(int i = 0; i < 4; i++) {
            for (int k = 0; k< 4; k++) {
                tiles[i][k].setImage(new Image(tiles[i][k].getUrl()));
                tiles[i][k].setMerged(false);
            }
        }
    }

    /**
     *Determines if a tile can move left by checking if it is not empty, is 
     *not on the edge, and the tile to its left has not already merged.
     *@param i first index in 2d array, j second index in 2d array
     */
    public boolean canGoLeft(int i, int j) {
        if(j < 1 || tiles[i][j].isEmpty() || tiles[i][j-1].hasMerged()) {
            return false;
        }
        return tiles[i][j-1].equals(tiles[i][j]);
    }

    /**
     *Loops through a 2d array of tiles and checks each one for
     *the ability to move left, shifts left each tile that is able
     *and merges tiles who are able to be merged.
     *
     */
    public void shiftLeft() {
        int counter = 0;
        //Checking for movability
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k<4;k++) {
                for(int j = k; j > 0; j--) {
                    if(tiles[i][j-1].isEmpty() && !tiles[i][j].isEmpty()) {
                        tiles[i][j].swap(tiles[i][j-1]);
                        counter++;
                    } else {
                        if(this.canGoLeft(i,j)) {
                            tiles[i][j].merge(tiles[i][j-1]);
                            counter++;
                            tiles[i][j-1].setMerged(true);
                        }
                        break;
                    }
                }
            }
        }
        if(counter > 0) {
            this.spawnTile();
            this.setImages();
        }

    }

    
    /**
     *Determines if a tile can move right by checking if it is not empty, is 
     *not on the edge, and the tile to its right has not already merged.
     *@param i first index in 2d array, j second index in 2d array
     *
     */
    public boolean canGoRight(int i, int j) {
        if(j > 2 || tiles[i][j].isEmpty() || tiles[i][j+1].hasMerged()) {
            return false;
        }
        return tiles[i][j+1].equals(tiles[i][j]);

    }
   /**
     *Loops through a 2d array of tiles and checks each one for
     *the ability to move right, shifts right each tile that is able
     *and merges tiles who are able to be merged.
     *
     */
    public void shiftRight() {
        int counter = 0;
        for(int i = 0; i < 4; i++) {
            for(int k = 3; k>-1;k--) {
                for(int j = k; j < 3; j++) {
                    if(tiles[i][j+1].isEmpty() && !tiles[i][j].isEmpty()) {
                        tiles[i][j].swap(tiles[i][j+1]);
                        counter++;
                        
                    } else {
                        if(this.canGoRight(i,j)) {
                            tiles[i][j].merge(tiles[i][j+1]);
                            counter++;
                            tiles[i][j+1].setMerged(true);
                        }
                        break;
                    
                    }
                    
                }
            }
        }
        if(counter > 0) {
            this.setImages();
            this.spawnTile();
        }
    }

    
    /**
     *Determines if a tile can move up by checking if it is not empty, is 
     *not on the edge, and the tile above it has not already merged.
     *@param i first index in 2d array, j second index in 2d array
     *@return boolean true or false
     */
    public boolean canGoUp(int j, int k) {
        if(j < 1 || tiles[j][k].isEmpty() || tiles[j-1][k].hasMerged()) {
            return false;
        }
        return tiles[j][k].equals(tiles[j-1][k]);
    }

   /**
     *Loops through a 2d array of tiles and checks each one for
     *the ability to move up, shifts up each tile that is able
     *and merges tiles who are able to be merged.
     *
     */
    public void shiftUp() {
        int counter = 0;
        for(int k = 0; k < 4; k++) {
            for(int i = 0; i<4;i++) {
                for(int j = i; j > 0; j--) {
                    if(tiles[j-1][k].isEmpty() && !tiles[j][k].isEmpty()) {
                        tiles[j][k].swap(tiles[j-1][k]);
                        counter++;
                    } else {
                        if(this.canGoUp(j,k)) {
                            tiles[j][k].merge(tiles[j-1][k]);
                            counter++;
                            tiles[j-1][k].setMerged(true);
                        }
                        break;
                    }

                        
                    
                }
            }
        }
        if(counter > 0) {
            this.setImages();
            this.spawnTile();
        }
    }

    
   /**
     *Determines if a tile can move down by checking if it is not empty, is 
     *not on the edge, and the tile below it has not already merged.
     *@param i first index in 2d array, j second index in 2d array  
     *@returns boolean true or false
     */
    public boolean canGoDown(int j, int k) {
        if(j > 2 || tiles[j][k].isEmpty() || tiles[j+1][k].hasMerged()) {
            return false;
        }
        return tiles[j][k].equals(tiles[j+1][k]);
    }

   /**
     *Loops through a 2d array of tiles and checks each one for
     *the ability to move down, shifts down  each tile that is able
     *and merges tiles who are able to be merged.
     *
     */
    public void shiftDown() {
        int counter = 0;
        for(int k = 0; k < 4; k++) {
            for(int i = 3; i>-1;i--) {
                for(int j = i; j < 3; j++) {
                    if(tiles[j+1][k].isEmpty() && !tiles[j][k].isEmpty()) {
                        tiles[j][k].swap(tiles[j+1][k]);
                        counter++;
                    } else {
                        if(this.canGoDown(j,k)) {
                            tiles[j][k].merge(tiles[j+1][k]);
                            counter++;
                            tiles[j+1][k].setMerged(true);
                        }
                        break;
                    }
                }
            }
        }
        if(counter > 0) {
            this.setImages();
            this.spawnTile();
        }
        
    }

    /**
     *Loops through a 2d array of tiles to check if
     *every space is occupied. Returns true if board is full,
     *and false if it is not.
     *@return boolean True or false
     */
    public boolean isFull() {
        for(int i =0; i < 4; i++) {
            for(int k = 0; k < 4; k++) {
                if(tiles[i][k].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *Loops through tiles array and checks if any tiles can move in
     *any direction. If not, returns true, otherwise returns false.
     *@return boolean true or false.
     */
    public boolean isGameOver() {
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k < 4; k++) {
                if(this.canGoLeft(i,k) || this.canGoRight(i,k) ||
                   this.canGoUp(i,k) || this.canGoDown(i,k))
                    return false;
            }
        }
        return true;
    }
    
    /**
     *Displays Game Over text and fades background.
     *
     */
    public void gameOver() {
        gameOver.setOpacity(1);
        GOBackground.setOpacity(.5);
    }
    /**
     *Sets the score variable to the given score and
     *sets the score text to display the proper score value
     *@param int score
     */
    public void setScore(int score) {
        this.score = score;
        this.scoreNumText.setText(Integer.toString(score));
    }

    /**
     *Returns the current game score.
     *@return int score
     */
    public int getScore() {
        return score;
    }

    
}
