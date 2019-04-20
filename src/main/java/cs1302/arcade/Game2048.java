package cs1302.arcade;
import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
public class Game2048 extends VBox{

    TilePane tiles;
    ImageView[] images;
    
    public Game2048() {
        tiles = new TilePane();
        tiles.setHgap(8);
        tiles.setVgap(8);
        tiles.setPrefColumns(4);
        tiles.setPrefRows(4);
        tiles.setMaxSize(560, 560);
        this.getChildren().add(tiles);
        images = new ImageView[16];
        for(int i = 0; i < 16; i++) {
            images[i] = new ImageView(new Image("2048/empty.png"));
            tiles.getChildren().add(images[i]);
        }
        
    }
}
