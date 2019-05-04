package cs1302.arcade;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    Rectangle[] aliens;
    ArcButton menu;
    StackPane game;
    HBox alienshbox;
    boolean noBullet;
    Rectangle laser;
    KeyFrame laserKey;
    EventHandler<ActionEvent> laserHandler;
    Timeline laserTime;

    public GameSI(ArcadeApp application) {
        this.application = application;
	this.setOnKeyPressed(createKeyHandler());
	ship = new Rectangle(32,32, new ImagePattern(new Image("spaceInv/ship.png")));
	space = new Rectangle(700,500, new ImagePattern(new Image("spaceInv/space.png")));
	aliens = new Rectangle[5];
	alienshbox = new HBox();
	alienshbox.setTranslateY(0);
	for(int i = 0; i < 5; i++) {
	    aliens[i] = new Rectangle(32,32,new ImagePattern(new Image("spaceInv/alien.png")));
	    alienshbox.getChildren().add(aliens[i]);
	}
	this.setUpLaser();
	ship.setTranslateY(200);
	game = new StackPane();
	game.setTranslateX(162);
	game.setTranslateY(134);
        game.getChildren().addAll(space,alienshbox,laser,ship);
        menu = new ArcButton(0,0,new Image("2048/MainMenu.png"), e -> {
		application.setScene(application.getScene());
		laserTime.pause();
	});
		    
        this.getChildren().addAll(menu,game);
	noBullet = true;
    }

    public void play() {
	laserTime.play();
    }

    private void setUpLaser() {
	laser = new Rectangle(2,4, new ImagePattern(new Image("spaceInv/laser.png")));
	laserHandler = event -> {
	    laser.setTranslateY(laser.getTranslateY()-1);
	    for(int i = 0; i < 5; i++) {
		if(laser.getBoundsInParent().intersects(aliens[i].getBoundsInParent())) {
		    aliens[i].setTranslateX(1000);
		    laser.setTranslateX(1000);
		}
	    }
	};
	laserKey = new KeyFrame(Duration.seconds(.0025), laserHandler);
        laserTime = new Timeline();
	laser.setTranslateY(1000);
        laserTime.setCycleCount(Timeline.INDEFINITE);
        laserTime.getKeyFrames().add(laserKey);
        laserTime.play();

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
	//if(ship.getBoundsInParent().intersects(test.getBoundsInParent())) {
	//System.out.println("RED ALRT");
	    //}
    }
}
