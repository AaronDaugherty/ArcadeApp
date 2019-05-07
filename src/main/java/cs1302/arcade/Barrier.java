package cs1302.arcade;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Barrier extends Rectangle {

    private final ImagePattern barrier1 = new ImagePattern(new Image("spaceInv/barrier1.png"));
    private final ImagePattern barrier2 = new ImagePattern(new Image("spaceInv/barrier2.png"));
    private final ImagePattern barrier3 = new ImagePattern(new Image("spaceInv/barrier3.png"));
    private final ImagePattern barrier4 = new ImagePattern(new Image("spaceInv/barrier4.png"));

    private int dmgLvl;
    private int width;
    private int height;

    public Barrier(int length, int width){
        super(length, width);
	this.setFill(barrier1);
        this.width = width;
        this.height = height;
	this.setDmgLvl(1);
    }

    public Barrier() {
        super(30, 30);
	this.setFill(barrier1);
        this.width = 30;
        this.height = 30;
	this.setDmgLvl(1);
    }

    public void setDmgLvl(int dmg) {
        this.dmgLvl = dmg;
        if(dmg == 1) {
            this.setFill(barrier1); 
	}
        if(dmg == 2) {
            this.setFill(barrier2);
        }
        if(dmg == 3) {
            this.setFill(barrier3);
        }
        if(dmg == 4) {
            this.setFill(barrier4);
        }
        if(dmg == 5) {
            this.setTranslateX(5000);
        }
    }

    public int getDmgLvl() {
        return this.dmgLvl;
    }
}
            
