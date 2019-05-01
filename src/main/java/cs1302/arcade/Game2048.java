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
public class Game2048 extends Group{

    TilePane pane;
    Tile2048[][] tiles;
    ArcadeApp application;
    VBox vbox;
    HBox hbox;
    StackPane stackpane;
    Random rand;
    Button ngButton;
    int score;
    Text scoreText;
    Text scoreNumText;
    Text gameOver;
    VBox scorevbox;
    Button mmButton;
    
    public Game2048(ArcadeApp application) {
        this.application = application;
        score = 0;
        stackpane = new StackPane();
        vbox = new VBox();
        hbox = new HBox();
        pane = new TilePane();
        pane.setHgap(8);
        pane.setVgap(8);
        pane.setPrefColumns(4);
        pane.setPrefRows(4);
        pane.setMaxSize(560, 560);
        hbox.getChildren().add(pane);
        pane.setTranslateX(232);
        tiles = new Tile2048[4][4];
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k < 4; k++) {
                tiles[i][k] = new Tile2048(this);
                pane.getChildren().add(tiles[i][k]);
            }
        }
        this.setOnKeyPressed(createKeyHandler());
        vbox.setAlignment(Pos.CENTER);
        rand = new Random();
        ngButton = new Button("New Game");
        mmButton = new Button();
        mmButton.setPadding(Insets.EMPTY);
        ImageView mainMenu = new ImageView(new Image("2048/MainMenu.png"));
        mmButton.setGraphic(mainMenu);
        mmButton.setOnAction(e -> application.setScene(application.getScene()));
        scoreText = new Text("Score");
        scoreNumText = new Text("0");
        gameOver = new Text("Game Over");
        try {
            String path = "src/main/resources/2048/JFWilwod.ttf";
            FileInputStream fp = new FileInputStream(path);
            FileInputStream fp2 = new FileInputStream(path);
            scoreText.setFont(Font.loadFont(fp,24));
            scoreNumText.setFont(new Font("System Bold", 24));
            gameOver.setFont(Font.loadFont(fp2,144));
            
        } catch(Exception e) {
            e.printStackTrace();
            scoreText.setFont(new Font("System Bold",24));
            gameOver.setFont(Font.loadFont("System Bold", 144));
        }
        scorevbox = new VBox();
        scorevbox.getChildren().addAll(scoreText, scoreNumText);
        scorevbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(ngButton,mmButton,scorevbox,hbox);
        scorevbox.setTranslateX(230);
        ngButton.setOnAction(e->this.newGame());
        gameOver.setOpacity(0);
        ImageView background = new ImageView(new Image("2048/GameBackground.png"));
        stackpane.getChildren().addAll(background,vbox,gameOver); 
        this.getChildren().addAll(stackpane);


        
        this.newGame();

    }

    public void newGame() {
        for(int i=0;i<4;i++) {
            for(int k=0;k<4;k++) {
                tiles[i][k].setEmpty(true);
                tiles[i][k].setNumber(0);
            }
        }
        gameOver.setOpacity(0);
        spawnTile();
        spawnTile();
        this.setImages();
        this.setScore(0);
    }
    
    public void spawnTile() {
        ArrayList<Tile2048> emptyImgs = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k<4; k++) {
                if(tiles[i][k].isEmpty()) {
                    emptyImgs.add(tiles[i][k]);
                }
            }
        }
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

    private EventHandler<? super KeyEvent> createKeyHandler() {
        return event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                this.shiftLeft();
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                this.shiftRight();
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                this.shiftUp();
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                this.shiftDown();
            } else {
                System.out.println(event);
            }
            if(this.isFull() && this.isGameOver()) {
                this.gameOver();
            }
        };
    }

    public void setImages() {
        for(int i = 0; i < 4; i++) {
            for (int k = 0; k< 4; k++) {
                tiles[i][k].setImage(new Image(tiles[i][k].getUrl()));
                tiles[i][k].setMerged(false);
            }
        }
    }

    public boolean canGoLeft(int i, int j) {
        if(j < 1 || tiles[i][j].isEmpty() || tiles[i][j-1].hasMerged()) {
            return false;
        }
        return tiles[i][j-1].equals(tiles[i][j]);
    }
    
    public void shiftLeft() {
        int counter = 0;
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
    public boolean canGoRight(int i, int j) {
        if(j > 2 || tiles[i][j].isEmpty() || tiles[i][j+1].hasMerged()) {
            return false;
        }
        return tiles[i][j+1].equals(tiles[i][j]);

    }
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
    public boolean canGoUp(int j, int k) {
        if(j < 1 || tiles[j][k].isEmpty() || tiles[j-1][k].hasMerged()) {
            return false;
        }
        return tiles[j][k].equals(tiles[j-1][k]);
    }
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
    public boolean canGoDown(int j, int k) {
        if(j > 2 || tiles[j][k].isEmpty() || tiles[j+1][k].hasMerged()) {
            return false;
        }
        return tiles[j][k].equals(tiles[j+1][k]);
    }
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
    

    public void gameOver() {
        gameOver.setOpacity(1);
    }

    public void setScore(int score) {
        this.score = score;
        this.scoreNumText.setText(Integer.toString(score));
    }

    public int getScore() {
        return score;
    }

    
}
