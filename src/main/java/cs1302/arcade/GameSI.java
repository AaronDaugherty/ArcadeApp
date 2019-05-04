package cs1302.arcade;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
public class GameSI extends Group {

    ArcadeApp application;
    Rectangle ship;
    Rectangle space;
    ArcButton menu;
    StackPane game;
    VBox vbox;
    

    public GameSI(ArcadeApp application) {
        this.application = application;
	this.setOnKeyPressed(createKeyHandler());
	ship = new Rectangle(32,32, new ImagePattern(new Image("spaceInv/ship.png")));
	space = new Rectangle(700,500, new ImagePattern(new Image("spaceInv/space.png")));
	ship.setTranslateY(200);
	game = new StackPane();
	game.setTranslateX(162);
	game.setTranslateY(134);
        game.getChildren().addAll(space,ship);
        menu = new ArcButton(0,0,new Image("2048/MainMenu.png"), e -> 
                             application.setScene(application.getScene()));
        this.getChildren().addAll(menu,game);
    }
    
    private EventHandler<? super KeyEvent> createKeyHandler() {
	return event -> {

	    if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
		this.shipLeft();
	    }

	    if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
		this.shipRight();
	    }

	};
    }

    public void shipLeft() {
	if(ship.getTranslateX() - 5 < -333) {
	    ship.setTranslateX(-333);
	} else {
	    ship.setTranslateX(ship.getTranslateX() -5);
	}
    }

    public void shipRight() {
	if(ship.getTranslateX() + 5> 333) {
	    ship.setTranslateX(333);
	} else {
	    ship.setTranslateX(ship.getTranslateX() + 5);
	}
    }
}
