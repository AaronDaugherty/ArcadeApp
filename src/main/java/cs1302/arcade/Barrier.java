package cs1302.arcade;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Barrier extends Rectangle {

    private final Image barrier1 = new Image("spaceInv/barrier1.png");
    private final Image barrier2 = new Image("spaceInv/barrier2.png");
    private final Image barrier3 = new Image("spaceInv/barrier3.png");
    private final Image barrier4 = new Image("spaceInv/barrier4.png");

    private int dmgLvl;
    private int width;
    private int height;

    public Barrier(Image image, int length, int width){
        super(length, width, new ImagePattern(image));
        this.width = width;
        this.height = height;
    }

    public Barrier() {
        super(30, 30, new ImagePattern(new Image("spaceInv/barrier1.png")));
        this.width = 30;
        this.height = 30;
    }

    public void setDmgLvl(int dmg) {
        this.dmgLvl = dmg;
        if(dmg == 1) {
            this.setFill(new ImagePattern(barrier1)); 
    }
        if(dmg == 2) {
            this.setFill(new ImagePattern(barrier2));
        }
        if(dmg == 3) {
            this.setFill(new ImagePattern(barrier3));
        }
        if(dmg == 4) {
            this.setFill(new ImagePattern(barrier4));
        }
        else {
            System.out.println("ERROR: Not a valid damage value");
        }
    }

    public int getDmgLvl() {
        return this.dmgLvl;
    }
}
            
