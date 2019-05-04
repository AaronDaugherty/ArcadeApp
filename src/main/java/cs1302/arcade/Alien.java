package cs1302.arcade;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
public class Alien extends Rectangle {

    public Alien(Image image) {
        super(32,32, new ImagePattern(image));
    }


}
