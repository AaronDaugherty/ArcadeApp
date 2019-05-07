package cs1302.arcade;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Represents a barrier block object in SpaceInvaders.
 *
 */

public class Barrier extends Rectangle {

    private final ImagePattern BARRIER_1 = new ImagePattern(new Image("spaceInv/barrier1.png"));
    private final ImagePattern BARRIER_2 = new ImagePattern(new Image("spaceInv/barrier2.png"));
    private final ImagePattern BARRIER_3 = new ImagePattern(new Image("spaceInv/barrier3.png"));
    private final ImagePattern BARRIER_4 = new ImagePattern(new Image("spaceInv/barrier4.png"));

    private int dmgLvl;
    private int width;
    private int height;

    /**
     *Constructor for Barrier class. Creates a barrier
     *with a given length and width
     *@param int length and width
     */
    public Barrier(int length, int width){
        super(length, width);
    this.setFill(BARRIER_1);
        this.width = width;
        this.height = height;
    this.setDmgLvl(1);
    }

    /**
     *Default constructor for Barrier class. Sets height
     * and width to 30 and sets dmgLvl to 1. 
     *
     */
    public Barrier() {
        super(30, 30);
    this.setFill(BARRIER_1);
        this.width = 30;
        this.height = 30;
    this.setDmgLvl(1);
    }

    /**
     *Sets the dmgLvl variable witht he given int
     *and updates the paint fill with the correct
     *image for the damage status.
     *@param int damage level
     */
    public void setDmgLvl(int dmg) {
        this.dmgLvl = dmg;
        if(dmg == 1) {
            this.setFill(BARRIER_1); 
    }
        if(dmg == 2) {
            this.setFill(BARRIER_2);
        }
        if(dmg == 3) {
            this.setFill(BARRIER_3);
        }
        if(dmg == 4) {
            this.setFill(BARRIER_4);
        }
        if(dmg == 5) {
            this.setTranslateX(5000);
        }
    }
    /**
     *Returns the value of the dmgLvl variable.
     *@returns an int dmgLvl
     */
    public int getDmgLvl() {
        return this.dmgLvl;
    }
}
            
