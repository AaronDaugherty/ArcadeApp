package cs1302.arcade;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Tile2048 extends ImageView{

    boolean empty;
    int number;
    int moveNumber;

    public Tile2048() {
        Image image = new Image("2048/empty.png");
        this.setImage(image);
        empty = true;
        number = 0;
        moveNumber = 0;
    }

    public void swap(Tile2048 tile) {
        boolean tempBool = tile.isEmpty();
        int tempNumber = tile.getNumber();
        tile.setEmpty(this.isEmpty());
        tile.setNumber(this.getNumber());
        this.setNumber(tempNumber);
        this.setEmpty(tempBool);
    }

    public String getUrl() {
        return "2048/"+this.getNumber()+".png";
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public int getMoveNumber() {
        return moveNumber;
    }
    
    public int canGoLeft(Tile2048[][] images, int i, int k) {
        int canGoLeft = 0;
        for(int j = k; j >= 0; j--) {
            if(images[i][j].isEmpty()) {
                canGoLeft++;
            }
            if(j < 3) {
                if(images[i][j].equals(images[i][j+1])) {
                    canGoLeft++;
                }
            }
        }
        return canGoLeft;
    }

    public boolean equals(Tile2048 tile) {
        return this.number == tile.number;
    }

    public int getNumber() {
        return number;
    }
    
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setNumber(int number) {
        this.number = number;
    }



}
