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
                emptyImgs.get(index).setImage("2048/2.png");
            } else {
                emptyImgs.get(index).setImage("2048/4.png");               
            }
            emptyImgs.get(index).setEmpty(false);
            for(int i = 0; i < 4; i++) {
                for(int k = 0; k <4; k++) {
                    if(emptyImgs.get(index) == tiles[i][k]) {
                        tiles[i][k].setEmpty(false);
                        if(number>0) {
                            tiles[i][k].setNumber(2);
                        } else {
                            tiles[i][k].setNumber(4);
                        }
                        
                    }
                }
            }
            /**for(int i = 0; i < 4; i++) {
                  for(int k = 0; k < 4 ;k++) {
                    Tile2048 a = tiles[i][k];
                    System.out.println("Tile "+i+"-"+k+" Empty: "+a.isEmpty() + " Number: "+a.getNumber());
                }
                }*/
            System.out.println();
        } else {
            this.gameOver();
        }
        
    }

    private EventHandler<? super KeyEvent> createKeyHandler() {
        return event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                this.shiftLeft();
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                System.out.println("Right");
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                System.out.println("Up");
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                System.out.println("Down");
            } else if (event.getCode() == KeyCode.M){
                this.spawnTile();
            } else {
                System.out.println(event);
            }
        };
    }

    public void shiftLeft() {
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k<4;k++) {
                for(int j = k; j > 0; j--) {
                    if(tiles[i][k-1].isEmpty()) {
                        Tile2048 temp = tiles[i][k-1];
                        tiles[i][k-1] = tiles[i][k];
                        tiles[i][k] = temp;
                        tiles[i][k-1].get
                    }
                    
                }
            }
        }
    }
    public void shiftRight() {
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k<4;k++) {
                // tiles[i][k] =  
            }
        }
    }
     public void shiftUp() {
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k<4;k++) {
                // tiles[i][k] =  
            }
        }
    }
    public void shiftDown() {
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k<4;k++) {
                //  tiles[i][k] =  
            }
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
