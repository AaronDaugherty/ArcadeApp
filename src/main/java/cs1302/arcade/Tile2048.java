package cs1302.arcade;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Tile2048 extends ImageView{

    boolean empty;
    int number;

    public Tile2048() {
        Image image = new Image("2048/empty.png");
        this.setImage(image);
        empty = true;
        number = 0;
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

    public void merge(Tile2048 tile) {
        tile.setNumber(tile.getNumber() *2);
        this.setNumber(0);
        this.setEmpty(true);
    }



}
