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
import java.lang.Runnable;
import java.lang.Thread;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameSI extends Group {

    ArcadeApp application;
    Rectangle ship;
    Rectangle space;
    Rectangle test;
    ArcButton menu;
    StackPane game;
    VBox vbox;
    boolean noBullet;
    Rectangle laser;

    public GameSI(ArcadeApp application) {
        this.application = application;
	this.setOnKeyPressed(createKeyHandler());
	ship = new Rectangle(32,32, new ImagePattern(new Image("spaceInv/ship.png")));
	space = new Rectangle(700,500, new ImagePattern(new Image("spaceInv/space.png")));
	test = new Rectangle(32,32, new ImagePattern(new Image("spaceInv/ship.png")));
	laser = new Rectangle(2,4, new ImagePattern(new Image("spaceInv/laser.png")));
	test.setTranslateY(200);
	test.setTranslateX(64);
	ship.setTranslateY(200);
	game = new StackPane();
	game.setTranslateX(162);
	game.setTranslateY(134);
        game.getChildren().addAll(space,laser,ship,test);
	laser.setTranslateY(1000);
	EventHandler<ActionEvent> handler = event -> laser.setTranslateY(laser.getTranslateY()-1);
	KeyFrame keyFrame = new KeyFrame(Duration.seconds(.0025), handler);
	Timeline timeline = new Timeline();
	timeline.setCycleCount(Timeline.INDEFINITE);
	timeline.getKeyFrames().add(keyFrame);
	timeline.play();
        menu = new ArcButton(0,0,new Image("2048/MainMenu.png"), e -> 
                             application.setScene(application.getScene()));
        this.getChildren().addAll(menu,game);
	noBullet = true;
    }
    
    private EventHandler<? super KeyEvent> createKeyHandler() {
	return event -> {

	    if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
		this.shipLeft();
	    }

	    if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
		this.shipRight();
	    }

	    if((event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W)&&noBullet) {
		this.shoot();
	    }

	};
    }

    public void shoot() {
	laser.setTranslateY(ship.getTranslateY());
	laser.setTranslateX(ship.getTranslateX());
    }

    public void shipLeft() {
	if(ship.getTranslateX() - 5 < -333) {
	    ship.setTranslateX(-333);
	} else {
	    ship.setTranslateX(ship.getTranslateX() -5);
	}
	this.collisionCheck();
    }

    public void shipRight() {
	if(ship.getTranslateX() + 5> 333) {
	    ship.setTranslateX(333);
	} else {
	    ship.setTranslateX(ship.getTranslateX() + 5);
	}
	this.collisionCheck();
    }

    public void collisionCheck() {
	if(ship.getBoundsInParent().intersects(test.getBoundsInParent())) {
	    System.out.println("RED ALRT");
	}
    }
}
