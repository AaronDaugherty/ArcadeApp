package cs1302.arcade;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
/**
 *Represents a button for the Arcade.
 */
public class ArcButton extends Button{

    /**
     *Constructor for ArcButton. Builds a special button with a specified
     *graphic, no padding, specified location, and specified action.
     *@param int x X translation
     *@param int y Y translation
     *@param Image image for button
     *@param EventHandler Action for button
     */
    public ArcButton(int x, int y, Image image, EventHandler<ActionEvent> value) {
        super();
        this.setGraphic(new ImageView(image));
        this.setOnAction(value);
        this.setPadding(Insets.EMPTY);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
