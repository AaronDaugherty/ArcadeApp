package cs1302.arcade;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
public class GameSI extends Group {

    ArcadeApp application;
    ImageView ship;
    ArcButton menu;
    StackPane game;
    VBox vbox;
    

    public GameSI(ArcadeApp application) {
        this.application = application;
        ship = new ImageView(new Image("spaceInv/spaceship.png"));
        game = new StackPane();
	game.setTranslateX(162);
	game.setTranslateY(134);
        game.getChildren().addAll(new ImageView(new Image("spaceInv/space.png")),ship);
        menu = new ArcButton(0,0,new Image("2048/MainMenu.png"), e -> 
                             application.setScene(application.getScene()));
        this.getChildren().addAll(menu,game);
    }
    
    
}
