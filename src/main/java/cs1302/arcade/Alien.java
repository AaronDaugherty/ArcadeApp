package cs1302.arcade;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
public class Alien extends Rectangle {
    
    int type;
    
    public Alien(Image image, int length, int width, int type) {
        super(length,width, new ImagePattern(image));
	this.type = type;
    }

    public int getType() {
	return type;
    }


}
