package cs1302.arcade;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
public class Alien extends Rectangle {
    
    int type;
    boolean isDead;
    
    public Alien(Image image, int length, int width, int type) {
        super(length,width, new ImagePattern(image));
	this.type = type;
	isDead = false;
    }

    public int getType() {
	return type;
    }

    public void setDead(boolean dead) {
	this.isDead = dead;
    }

    public boolean isDead() {
	return isDead;
    }

}
