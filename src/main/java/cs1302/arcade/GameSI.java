package cs1302.arcade;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class GameSI extends Group {

    ArcadeApp application;
    ImageView ship;
    ArcButton menu;

    public GameSI(ArcadeApp application) {
        this.application = application;
        ship = new ImageView(new Image("spaceInv/spaceship.png"));
        
        menu = new ArcButton(0,0,new Image("2048/MainMenu.png"), e -> 
                             application.setScene(application.getScene()));
        this.getChildren().addAll(ship,menu);
    }
    
    
}
