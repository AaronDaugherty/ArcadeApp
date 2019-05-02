package cs1302.arcade;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
public class ArcButton extends Button{

    public ArcButton(int x, int y, Image image, EventHandler<ActionEvent> value) {
        super();
        this.setGraphic(new ImageView(image));
        this.setOnAction(value);
        this.setPadding(Insets.EMPTY);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
