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
public class Game2048 extends Group{

    TilePane tiles;
    ImageView[] images;
    ArcadeApp application;
    VBox vbox;
    
    public Game2048(ArcadeApp application) {
        this.application = application;
        vbox = new VBox();
        tiles = new TilePane();
        tiles.setHgap(8);
        tiles.setVgap(8);
        tiles.setPrefColumns(4);
        tiles.setPrefRows(4);
        tiles.setMaxSize(560, 560);
        vbox.getChildren().add(tiles);
        images = new ImageView[16];
        for(int i = 0; i < 16; i++) {
            images[i] = new ImageView(new Image("2048/empty.png"));
            tiles.getChildren().add(images[i]);
        }
        this.setOnKeyPressed(createKeyHandler());
        this.getChildren().add(vbox);
    }
    

    private EventHandler<? super KeyEvent> createKeyHandler() {
        return event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                System.out.println("Left");
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                System.out.println("Right");
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                System.out.println("Up");
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                System.out.println("Down");
            } else {
                System.out.println(event);
            }
        };
    }
    
    public void playGame() {
        boolean gameOver = false;
        while(!gameOver) {
            this.requestFocus();
        }

    }

    
}
