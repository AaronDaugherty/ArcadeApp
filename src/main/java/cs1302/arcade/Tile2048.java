package cs1302.arcade;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 *Represents tiles in the game 2048.
 *
 */
public class Tile2048 extends ImageView{

    boolean hasMerged;
    boolean empty;
    int number;
    Game2048 game;
    /**
     *Constructor for Tile2048. Sets all
     *properties to default values.
     */
    public Tile2048(Game2048 game) {
        Image image = new Image("2048/0.png");
        this.setImage(image);
        empty = true;
        number = 0;
        hasMerged = false;
        this.game = game;
    }
    /**
     *Swaps the given tile parameter with the
     *calling object tile.
     *@param A tile2048 object
     */
    public void swap(Tile2048 tile) {
        boolean tempBool = tile.isEmpty();
        int tempNumber = tile.getNumber();
        tile.setEmpty(this.isEmpty());
        tile.setNumber(this.getNumber());
        this.setNumber(tempNumber);
        this.setEmpty(tempBool);
    }

    /**
     *Returns a url formed by using the stored
     *number of the tile object.
     *@return String A url for an image.
     */
    public String getUrl() {
        return "2048/"+this.getNumber()+".png";
    }

    

    /**
     *Returns true if the calling tile is equal to the 
     *parameter tile.
     *@param A tile2048 object
     *@return boolean true or false
     */
    public boolean equals(Tile2048 tile) {
        return this.number == tile.number;
    }

    /**
     *Returns the value of the number variable
     *for the calling tile2048 object.
     *@return int value of tile
     */
    public int getNumber() {
        return number;
    }

    /**
     *Returns true if the tile is empty.
     *@return boolean true or false
     */
    public boolean isEmpty() {
        return empty;
    }
    /**
     *Sets the given empty value to the tile's
     *empty value
     *@param boolean true or false
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     *Sets the given number to the tile.
     *If number is 2048, then the game ends with a win
     *@param int number of tile
     */
    public void setNumber(int number) {
        this.number = number;
        if(number == 2048) {
            game.win();
        }
    }

    /**
     *Merges two tiles together, adding their numbers.
     *@param Tile2048 a tile
     */
    public void merge(Tile2048 tile) {
        tile.setNumber(tile.getNumber() *2);
        this.setNumber(0);
        this.setEmpty(true);
        game.setScore(game.getScore() + tile.getNumber());
    }

    /**
     *Sets the value of hasMerged to store if
     *the tile has merged.
     *@param boolean true or false
     */
    public void setMerged(boolean hasMerged) {
        this.hasMerged = hasMerged;
    }
    /**
     *Returns true if the tile has merged.
     *@return boolean true or false
     *
     */
    public boolean hasMerged() {
        return hasMerged;
    }



}
