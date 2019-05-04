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
import java.util.LinkedList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
public class GameSI extends Group {

    ArcadeApp application;
    Rectangle ship;
    Rectangle space;
    LinkedList<Rectangle> aliens;
    ArcButton menu;
    StackPane game;
    HBox alienshbox;
    boolean noBullet;
    Rectangle laser;
    
    KeyFrame laserKey;
    EventHandler<ActionEvent> laserHandler;
    Timeline laserTime;

    KeyFrame alienKey;
    EventHandler<ActionEvent> alienHandler;
    Timeline alienTime;

    Rectangle leftBound;
    Rectangle rightBound;
    int alienDirection;
    
    public GameSI(ArcadeApp application) {
        this.application = application;
	this.setOnKeyPressed(createKeyHandler());
	ship = new Rectangle(32,32, new ImagePattern(new Image("spaceInv/ship.png")));
	space = new Rectangle(700,500, new ImagePattern(new Image("spaceInv/space.png")));
	this.setUpLaser();
	ship.setTranslateY(200);
	game = new StackPane();
	game.setTranslateX(162);
	game.setTranslateY(134);
	this.setUpAliens();
        game.getChildren().addAll(space,alienshbox,laser,ship);
        menu = new ArcButton(0,0,new Image("2048/MainMenu.png"), e -> {
		application.setScene(application.getScene());
		laserTime.pause();
	});
	alienDirection = 0;
	rightBound = new Rectangle(1,500,Color.BLUE);
	rightBound.setTranslateX(350);
	leftBound = new Rectangle(1,500,Color.BLUE);
	leftBound.setTranslateX(0);
	game.getChildren().add(rightBound);
        this.getChildren().addAll(menu,game);
	noBullet = true;
    }

    public void play() {
	laserTime.play();
    }

    private void setUpAliens() {
	aliens = new LinkedList<Rectangle>();
        alienshbox = new HBox();
        alienshbox.setTranslateY(0);
        for(int i = 0; i < 5; i++) {
            aliens.add(new Rectangle(32,32,new ImagePattern(new Image("spaceInv/alien.png"))));
            alienshbox.getChildren().add(aliens.get(i));
        }
	//alienshbox.maxWidth(160);
	alienHandler = event -> {
	    switch (alienDirection) {
	    case 0:
	    for(Rectangle alien: aliens) {
		if(alien.getBoundsInParent().intersects(rightBound.getBoundsInParent())) {
		    alienDirection = 1;
		    break;
		}
		alien.setTranslateX(alien.getTranslateX() + 1);
	    }
	    break;
	    case 1:
	    for(Rectangle alien: aliens) {
		if(alien.getBoundsInParent().intersects(leftBound.getBoundsInParent())) {
		    alienDirection = 0;
		    break;
		}
		alien.setTranslateX(alien.getTranslateX() - 1);

	    }
	    break;
	    }
	  
	};
	alienKey = new KeyFrame(Duration.seconds(.005), alienHandler);
	alienTime = new Timeline();
	alienTime.setCycleCount(Timeline.INDEFINITE);
	alienTime.getKeyFrames().add(alienKey);
	alienTime.play();
    }

    private void setUpLaser() {
	laser = new Rectangle(2,4, new ImagePattern(new Image("spaceInv/laser.png")));
	laserHandler = event -> {
	    laser.setTranslateY(laser.getTranslateY()-1);
	    for(Rectangle alien: aliens) {
		if(laser.getBoundsInParent().intersects(alien.getBoundsInParent())) {
		    alien.setTranslateX(1000);
		    laser.setTranslateX(1000);
		}
	    }
	};
	laserKey = new KeyFrame(Duration.seconds(.0025), laserHandler);
        laserTime = new Timeline();
	laser.setTranslateY(-1000);
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
