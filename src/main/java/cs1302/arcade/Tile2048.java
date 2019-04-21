package cs1302.arcade;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Tile2048 extends ImageView{

    Image image;
    boolean empty;


    public Tile2048() {
        image = new Image("2048/empty.png");
        this.setImage(image);
        empty = true;
    }

    public boolean canGoLeft(Tile2048[][] images, int i) {
        return true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setImage(String image) {
        this.image = new Image(image);
        this.setImage(this.image);
    }

}
