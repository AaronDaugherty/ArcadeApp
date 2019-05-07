package cs1302.arcade;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
public class Alien extends Rectangle {
    
    int type;
    boolean isDead;
    int xDist;
    int yDist;
    boolean canShoot;
    Rectangle laser;
    
    public Alien(Image image, int length, int width, int type) {
        super(length,width, new ImagePattern(image));
	this.type = type;
	isDead = false;
	canShoot = false;
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

    public void setXDist(int xDist) {
	this.xDist = xDist;
    }

    public int getXDist() {
	return xDist;
    }
    
    public void setYDist(int yDist) {
	this.yDist = yDist;
    }

    public int getYDist() {
	return yDist;
    }

    public void setCanShoot(boolean canShoot) {
	this.canShoot = canShoot;
    }

    public boolean getCanShoot() {
	return canShoot;
    }

    public void addLaser(Rectangle laser) {
	this.laser = laser;
    }

    public Rectangle getLaser() {
	return laser;
    }

}
