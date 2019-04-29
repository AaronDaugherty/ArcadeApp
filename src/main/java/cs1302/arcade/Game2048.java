package cs1302.arcade;
import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Random;
public class Game2048 extends Group{

    TilePane pane;
    Tile2048[][] tiles;
    ArcadeApp application;
    VBox vbox;
    Random rand;
    
    public Game2048(ArcadeApp application) {
        this.application = application;
        vbox = new VBox();
        pane = new TilePane();
        pane.setHgap(8);
        pane.setVgap(8);
        pane.setPrefColumns(4);
        pane.setPrefRows(4);
        pane.setMaxSize(560, 560);
        vbox.getChildren().add(pane);
        tiles = new Tile2048[4][4];
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k < 4; k++) {
                tiles[i][k] = new Tile2048();
                pane.getChildren().add(tiles[i][k]);
            }
        }
        this.setOnKeyPressed(createKeyHandler());
        this.getChildren().add(vbox);
        rand = new Random();
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

        } else {
            this.gameOver();
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
            } else if (event.getCode() == KeyCode.M){
                this.spawnTile();
            } else {
                System.out.println(event);
            }
        };
    }

    public void setImages() {
        for(int i = 0; i < 4; i++) {
            for (int k = 0; k< 4; k++) {
                tiles[i][k].setImage(new Image(tiles[i][k].getUrl()));
            }
        }
    }

    public void shiftLeft() {
        int counter = 0;
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k<4;k++) {
                for(int j = k; j > 0; j--) {
                    if(tiles[i][j-1].isEmpty() && !tiles[i][j].isEmpty()) {
                        tiles[i][j].swap(tiles[i][j-1]);
                        counter++;
                    }
                    if(tiles[i][j-1].equals(tiles[i][j])) {
                        tiles[i][j].merge(tiles[i][j-1]);
                        counter++;
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
    public void shiftRight() {
        int counter = 0;
        for(int i = 0; i < 4; i++) {
            for(int k = 3; k>-1;k--) {
                for(int j = k; j < 3; j++) {
                    if(tiles[i][j+1].isEmpty() && !tiles[i][j].isEmpty()) {
                        tiles[i][j].swap(tiles[i][j+1]);
                        counter++;
                    }
                    if(tiles[i][j+1].equals(tiles[i][j])) {
                        tiles[i][j].merge(tiles[i][j+1]);
                        counter++;
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
    public void shiftUp() {
        int counter = 0;
        for(int k = 0; k < 4; k++) {
            for(int i = 0; i<4;i++) {
                for(int j = i; j > 0; j--) {
                    if(tiles[j-1][k].isEmpty() && !tiles[j][k].isEmpty()) {
                        tiles[j][k].swap(tiles[j-1][k]);
                        counter++;
                     }
                    if(tiles[j][k].equals(tiles[j-1][k])) {
                        tiles[j][k].merge(tiles[j-1][k]);
                        counter++;
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
    public void shiftDown() {
        int counter = 0;
        for(int k = 0; k < 4; k++) {
            for(int i = 3; i>-1;i--) {
                for(int j = i; j < 3; j++) {
                    if(tiles[j+1][k].isEmpty() && !tiles[j][k].isEmpty()) {
                        tiles[j][k].swap(tiles[j+1][k]);
                        counter++;
                    }
                    if(tiles[j][k].equals(tiles[j+1][k])) {
                        tiles[j][k].merge(tiles[j+1][k]);
                        counter++;
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


    
    public void playGame() {
        boolean gameOver = false;
        while(!gameOver) {
            this.requestFocus();
        }

    }

    public void gameOver() {
        System.out.println("Game Over");
    }

    
}
