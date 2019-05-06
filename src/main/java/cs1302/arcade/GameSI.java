package cs1302.arcade;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
import java.util.LinkedList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import java.util.Timer;
import java.util.TimerTask;
public class GameSI extends Group {

    ArcadeApp application;
    Rectangle ship;
    Rectangle space;
    LinkedList<Alien> aliens;
    ArcButton menu;
    ArcButton reset;
    StackPane game;
    VBox aliensvbox;
    HBox alienshbox;
    HBox alienshbox2;
    boolean noBullet;
    Rectangle laser;
    Timeline laserTime;
    Timeline animTime;
    Timeline alienTime;
    Timeline playerTimeR;
    Timeline playerTimeL;
    Timeline levelTime;
    Rectangle leftBound;
    Rectangle rightBound;
    int alienDirection;
    int anim;
    int level;
    ImageView level1;
    ImageView level2;
    ImageView level3;
    
    public GameSI(ArcadeApp application) {
        this.application = application;
	level = 1;
	space = new Rectangle(700,500, new ImagePattern(new Image("spaceInv/space.png")));
	this.setUpLaser();
	this.setUpPlayer();
	ship.setTranslateY(200);
	game = new StackPane();
	game.setTranslateX(162);
	game.setTranslateY(134);
	this.setUpAliens();
	this.setUpLevel();
	game.getChildren().addAll(space,aliensvbox,laser,ship,level1,level2,level3);
        menu = new ArcButton(0,0,new Image("2048/MainMenu.png"), e -> {
		application.setScene(application.getScene());
		this.pause();
	});
	reset = new ArcButton(100,0,new Image("2048/TryAgain.png"), e -> {
		for(Alien alien: aliens) {
		    alien.setTranslateX(0);
		    alien.setTranslateY(0);
		    alienDirection = 0;
		    this.setLevel(1);
		    this.play();
		}
	});
	anim = 1;
	alienDirection = 0;
	rightBound = new Rectangle(1,500,Color.BLUE);
	rightBound.setTranslateX(350);
	leftBound = new Rectangle(1,500,Color.BLUE);
	leftBound.setTranslateX(0);
	game.getChildren().add(rightBound);
        this.getChildren().addAll(menu,reset, game);
	noBullet = true;
	this.setUpAnimations();
	this.pause();
    }

    public void setUpLevel() {
	level1 = new ImageView(new Image("spaceInv/Level1.png"));
	level2 = new ImageView(new Image("spaceInv/Level2.png"));
	level3 = new ImageView(new Image("spaceInv/Level3.png"));
	level1.setOpacity(0);
	level2.setOpacity(0);
	level2.setTranslateY(60);
	level3.setOpacity(0);
        
    }

    public EventHandler<ActionEvent> movementR() {
	return e -> {
	    this.shipRight();
	};
    }

    public EventHandler<ActionEvent> movementL() {
	return e -> {
	    this.shipLeft();
	};
    }
    
    public void setUpPlayer() {
	ship = new Rectangle(32,32, new ImagePattern(new Image("spaceInv/ship.png")));
	this.setOnKeyPressed(createKeyHandler());
	this.setOnKeyReleased(createKeyHandler2());
	KeyFrame playerKeyR = new KeyFrame(Duration.seconds(.02), this.movementR());
        playerTimeR = new Timeline();
        playerTimeR.setCycleCount(Timeline.INDEFINITE);
        playerTimeR.getKeyFrames().add(playerKeyR);

	KeyFrame playerKeyL = new KeyFrame(Duration.seconds(.02), this.movementL());
	playerTimeL = new Timeline();
	playerTimeL.setCycleCount(Timeline.INDEFINITE);
	playerTimeL.getKeyFrames().add(playerKeyL);
    }
    
    public void pause() {
	laserTime.pause();
	alienTime.pause();
	animTime.pause();
	playerTimeR.pause();
	playerTimeL.pause();
    }
    
    public void play() {
	this.level();
	Timer t = new Timer();
	t.schedule(new LevelTask(), 2000);
    }

    class LevelTask extends TimerTask {
	public void run() {
	    laserTime.play();
	    alienTime.play();
	    animTime.play();
	    level1.setOpacity(0);
	    level2.setOpacity(0);
	    level3.setOpacity(0);
	}
    }

    private void setUpAliens() {
	aliens = new LinkedList<Alien>();
        alienshbox = new HBox();
	alienshbox2 = new HBox();
	aliensvbox = new VBox();
	aliensvbox.getChildren().addAll(alienshbox2, alienshbox);
        for(int i = 0; i < 10; i++) {
            aliens.add(new Alien(new Image("spaceInv/alien1open.png"),32,32,1));
            alienshbox.getChildren().add(aliens.get(i));
	    //aliens.get(i).setTranslateX(300);
        }
	for(int i = aliens.size(); i < 20; i++) {
	    aliens.add(new Alien(new Image("spaceInv/alien2open.png"),32,32,2));
	    alienshbox2.getChildren().add(aliens.get(i));
	}
	EventHandler<ActionEvent> alienHandler = event -> {
	    switch (alienDirection) {
	    case 0:
	    for(Alien alien: aliens) {
		if(alien.getBoundsInParent().intersects(rightBound.getBoundsInParent())) {
		    alienDirection = 1;
		    for(Alien alien2: aliens) {
			alien2.setTranslateY(alien2.getTranslateY() + 16);
		    }
		    break;
		}
		alien.setTranslateX(alien.getTranslateX() + 1);
	    }
	    break;
	    case 1:
	    for(Alien alien: aliens) {
		if(alien.getBoundsInParent().intersects(leftBound.getBoundsInParent())) {
		    alienDirection = 0;
		    for(Alien alien2: aliens) {
			alien2.setTranslateY(alien2.getTranslateY() + 16);
		    }
		    break;
		}
		alien.setTranslateX(alien.getTranslateX() - 1);
	    }
	    break;
	    }
	};
	KeyFrame alienKey = new KeyFrame(Duration.seconds(.005), alienHandler);
	alienTime = new Timeline();
	alienTime.setCycleCount(Timeline.INDEFINITE);
	alienTime.getKeyFrames().add(alienKey);
	alienTime.play();
    }

    private void setUpAnimations() {
	EventHandler<ActionEvent> animHandler = event -> {
	    if(anim == 0) {
		for(Alien alien: aliens) {
		    if(alien.getType() == 1) {
			alien.setFill(new ImagePattern(new Image("spaceInv/alien1closed.png")));
		    } else if(alien.getType() == 2) {
			alien.setFill(new ImagePattern(new Image("spaceInv/alien2closed.png")));
		    }
		}
		anim = 1;
	    } else {
		for(Alien alien: aliens) {
		    if(alien.getType() == 1) {
			alien.setFill(new ImagePattern(new Image("spaceInv/alien1open.png")));
		    } else if(alien.getType() == 2) {
			alien.setFill(new ImagePattern(new Image("spaceInv/alien2open.png")));
		    }
		}
		anim = 0;
	    }
	};
	KeyFrame animKey = new KeyFrame(Duration.seconds(1),animHandler);
	animTime = new Timeline();
	animTime.setCycleCount(Timeline.INDEFINITE);
	animTime.getKeyFrames().add(animKey);
	animTime.play();
    }

    private void setUpLaser() {
	laser = new Rectangle(4,8, new ImagePattern(new Image("spaceInv/laser.png")));
	EventHandler<ActionEvent> laserHandler = event -> {
	    laser.setTranslateY(laser.getTranslateY()-1);
	    for(Alien alien: aliens) {
		if(laser.getBoundsInParent().intersects(alien.getBoundsInParent())) {
		    alien.setTranslateX(5000);
		    laser.setTranslateX(1000);
		    alien.setDead(true);
		}
	    }
	    boolean nextLevel = true;
	    for(Alien alien: aliens) {
		if(!alien.isDead()) {
		    nextLevel = false;
		}
	    }
	    if(nextLevel) {
		this.setLevel(this.getLevel() + 1);
	    }
	};
	KeyFrame laserKey = new KeyFrame(Duration.seconds(.0025), laserHandler);
        laserTime = new Timeline();
	laser.setTranslateY(-1000);
        laserTime.setCycleCount(Timeline.INDEFINITE);
        laserTime.getKeyFrames().add(laserKey);
        laserTime.play();

    }

    public void level() {
	if(this.getLevel() == 1) {
	    this.pause();
	    level1.setOpacity(1);
	} else if(this.getLevel() ==2) {
	    level2.setOpacity(1);
	    this.pause();
	    for(Alien alien: aliens) {
		alien.setTranslateX(0);
		alien.setTranslateY(100);
		alienDirection = 0;
            }
	    
	}
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
	this.play();
    }
    
    private EventHandler<? super KeyEvent> createKeyHandler() {
	return event -> {

	    if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
		playerTimeL.play();
	    }else if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
		playerTimeR.play();
	    } else if((event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W)&&noBullet) {
	        this.shoot();
	    }
	};
    }

    private EventHandler<? super KeyEvent> createKeyHandler2() {
	return event -> {
	    if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
		playerTimeL.pause();
	    } else if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
		playerTimeR.pause();
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
