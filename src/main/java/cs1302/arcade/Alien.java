package cs1302.arcade;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 *Represents an alien object in the game SpaceInvaders
 *
 */

public class Alien extends Rectangle {
    
    int type;
    boolean isDead;
    int xDist;
    int yDist;
    boolean canShoot;
    Rectangle laser;

    /**
     *Constructor for the Alien class. Constructs an alien using the
     *Rectangle constructor and setting the type, isDead, and canShoot
     *variables.
     *@param Image the image  
     *@param int Length
     *@param int Width
     *@param int type of alien
     */
    public Alien(Image image, int length, int width, int type) {
        super(length,width, new ImagePattern(image));
	this.type = type;
	isDead = false;
	canShoot = false;
    }

    /**
     *Returns type of alien
     *@return type an int represnting alien type
     */
    public int getType() {
	return type;
    }

    /**
     *Sets the dead status of alien.
     *@param boolean if dead, true, if alive, false
     */
    public void setDead(boolean dead) {
	this.isDead = dead;
    }

    /**
     *Returns the death status of alien
     *@return boolean true if dead, false if alive
     */
    public boolean isDead() {
	return isDead;
    }

    /**
     *Sets the X distance for alien
     *@param int xDist Distance to transform alien in X plane
     */
    public void setXDist(int xDist) {
	this.xDist = xDist;
    }

    /**
     *Returns the current transformed X distance.
     *@return XDist an int representing distance transformed in X plane
     */
    public int getXDist() {
	return xDist;
    }

  /**
    *Sets the Y distance for alien
    *@param int YDist Distance to transform alien in Y plane
    */
    public void setYDist(int yDist) {
	this.yDist = yDist;
    }

   /**
    *Returns the current transformed Y distance.
    *@return YDist an int representing distance transformed in Y plane
    */
    public int getYDist() {
	return yDist;
    }

    /**
     *
     *
     */
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
