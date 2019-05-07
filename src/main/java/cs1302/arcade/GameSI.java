package cs1302.arcade;
import javafx.scene.paint.Color;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Random;
public class GameSI extends Group {

    public final ImagePattern alienLsrImg = new ImagePattern(new Image("spaceInv/laser.png"));
    
    ArcadeApp application;
    Rectangle ship;
    Random rand;
    Rectangle space;
    Rectangle frame;
    Rectangle nebula;
    Rectangle joystick;
    LinkedList<Alien> shootAliens;
    LinkedList<Timeline> alienLaserTimes;
    LinkedList<Alien> aliens;
    LinkedList<Barrier> barriers;
    LinkedList<Rectangle> lasers;
    ArcButton menu;
    ArcButton reset;
    ArcButton quit;
    StackPane game;
    boolean noBullet;
    Rectangle laser;
    VBox aliensvbox;
    Timeline laserTime;
    Timeline animTime;
    Timeline alienTime;
    Timeline playerTimeR;
    Timeline playerTimeL;
    Timeline levelTime;
    Timeline alienShootTime;
    Timer timer;
    Rectangle leftBound;
    Rectangle rightBound;
    int alienDirection;
    int anim;
    int level;
    Text level1;
    Text level2;
    Text level3;
    boolean paused;
    Text scoreText;
    int score;
    
    
    public GameSI(ArcadeApp application) {
    this.application = application;
	noBullet = true;
	level = 1;
	rand = new Random();
	scoreText = new Text("Score: "+Integer.toString(score));
	scoreText.setFill(Color.rgb(255,255,255));
	scoreText.setTranslateY(-225);
	timer = new Timer(true);
	space = new Rectangle(700,500, new ImagePattern(new Image("spaceInv/space.png")));
	frame = new Rectangle(1024, 768, new ImagePattern(new Image("spaceInv/frame.png")));
	nebula = new Rectangle(556, 799, new ImagePattern(new Image("spaceInv/nebula.jpg")));
	joystick = new Rectangle(200, 200, new ImagePattern(new Image("spaceInv/joystick.png")));
	this.setUpLaser();
	this.setUpPlayer();
	ship.setTranslateY(200);
	game = new StackPane();
	game.setTranslateX(162);
	game.setTranslateY(134);
	this.setUpAliens();
	nebula.setTranslateX(862);
	joystick.setTranslateX(580);
	joystick.setTranslateY(580);
	this.setUpLevel();
	
	
	
	game.getChildren().addAll(space,aliensvbox,laser,ship,level1,level2,level3,scoreText);
	this.setUpAlienShoot();


        //Main Menu button for SpaceInvaders
	menu = new ArcButton(0,0,new Image("spaceInv/mainMenu.png"), e -> {
		application.setScene(application.getScene());
		this.pause();
	});
        menu.setTranslateX(200);
        menu.setTranslateY(50);
        
        //Restart button for SpaceInvaders    
	reset = new ArcButton(100,0,new Image("spaceInv/restart.png"), e -> {
		score = 0;
		scoreText.setText("Score: "+Integer.toString(score));
		reset.setDisable(true);
		menu.setDisable(true);
		alienDirection = 0;
		ship.setTranslateX(0);
		laser.setTranslateY(2000);
		for(int i = 0; i < 50; i++) {
		    aliens.get(i).setTranslateX(0);
		    aliens.get(i).setTranslateY(0);
		    aliens.get(i).setDead(false);
		    
		}
		this.alienShoot();
		reset.setTranslateX(750);
		this.setLevel(1);
		this.level();
		this.resetBarriers();
	});
	quit = new ArcButton(200, 0, new Image("spaceInv/exit.png"), e-> {
		for(Timeline timeline: alienLaserTimes) {
		    timeline.stop();
		}
		alienShootTime.stop();
		timer.cancel();
		timer.purge();
		laserTime.stop();
		alienTime.stop();
		animTime.stop();
		playerTimeR.stop();
		playerTimeL.stop();
		System.exit(0);
		
	});
	anim = 1;
	alienDirection = 0;
	rightBound = new Rectangle(1,500,Color.BLUE);
	rightBound.setTranslateX(350);
	leftBound = new Rectangle(1,500,Color.BLUE);
	leftBound.setTranslateX(0);
	game.getChildren().add(rightBound);
	this.getChildren().addAll(frame,menu,reset,quit,game,nebula,joystick);
	noBullet = true;
	this.setUpAnimations();
	this.setUpBarriers();
	this.pause();
    }

    public EventHandler<ActionEvent> createLaserShoot(int i) {
	EventHandler<ActionEvent> event = e -> {
	    lasers.get(i).setTranslateY(lasers.get(i).getTranslateY()+10);
	    //if(lasers.get(i).getTranslateY() < -500) {
	    //	lasers.get(i).setTranslateY(1000);
	    //	alienLaserTimes.get(i).pause();
	    //}
	};
	return event;
    }

    

    public void setUpAlienShoot() {
	alienShootTime = new Timeline();
	lasers = new LinkedList<Rectangle>();
	Rectangle laser = new Rectangle(4,8,alienLsrImg);
	for(int i = 0; i < 10; i++) {
	    laser = new Rectangle(4,8,alienLsrImg);
	    lasers.add(laser);
	    game.getChildren().add(laser);
	    for(int k = 0; k < 5; k++) {
		if(k == 0) {
		    aliens.get(i).setCanShoot(true);
		}
		aliens.get(((10*k)+i)).addLaser(laser);
	    }
	}
	alienLaserTimes = new LinkedList<Timeline>();
	for(int i = 0; i < 10; i++) {
	    Timeline timeline = new Timeline();
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    alienLaserTimes.add(timeline);
	}
	for(int i = 0; i < 10; i++) {
	    KeyFrame alienShootKey2 = new KeyFrame(Duration.seconds(.03),this.createLaserShoot(i));
	    alienLaserTimes.get(i).getKeyFrames().add(alienShootKey2);
	}
    }

    public void shootAlien(Alien alien) {
	alien.getLaser().setTranslateX(alien.getTranslateX()-alien.getXDist());
	alien.getLaser().setTranslateY(alien.getTranslateY());
	//System.out.println("Alien X: "+alien.getTranslateX());
	//System.out.println("Alien Y: "+alien.getTranslateY());
	//System.out.println("Laser X: "+alien.getLaser().getTranslateX());
        //System.out.println("Laser Y: "+alien.getLaser().getTranslateY());
	alienLaserTimes.get(aliens.indexOf(alien)).play();
    }

    public void alienShoot() {
	shootAliens = new LinkedList<Alien>();
	for(Alien alien: aliens) {
	    if(alien.getCanShoot()) {
		shootAliens.add(alien);
	    }
	}
	EventHandler<ActionEvent> alienShootHandler = e -> {
	    for(Alien alien: shootAliens) {
		int randNum = rand.nextInt(20);
		if(randNum == 5) {
		    this.shootAlien(alien);
		}
	    }
	    
	};
	KeyFrame alienShootKey = new KeyFrame(Duration.seconds(1), alienShootHandler);
	alienShootTime.setCycleCount(Timeline.INDEFINITE);
	alienShootTime.getKeyFrames().add(alienShootKey);
    }

    public ArcButton getQuit() {
	return quit;
    }

    public ArcButton getReset() {
	return reset;
    }

    public void setUpLevel() {
	level1 = new Text("Level 1");
	level2 = new Text("Level 2");
	level3 = new Text("Level 3");
	Font f = new Font("System Bold", 48);
	level1.setFont(f);
	level2.setFont(f);
	level3.setFont(f);
	level1.setFill(Color.rgb(255,255,255));
	level2.setFill(Color.rgb(255,255,255));
	level3.setFill(Color.rgb(255,255,255));
	level1.setOpacity(0);
	level2.setOpacity(0);
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
	paused = true;
	laserTime.pause();
	alienTime.pause();
	animTime.pause();
	playerTimeR.pause();
	playerTimeL.pause();
    }
    

    public void play() {
	this.level();
	TimerTask levelTask = new TimerTask() {
		public void run() {
		    alienTime.play();
		    animTime.play();
		    level1.setOpacity(0);
		    level2.setOpacity(0);
		    level3.setOpacity(0);
		    paused = false;
		    noBullet = true;
		    reset.setDisable(false);
		    menu.setDisable(false);
		    cancel();
		    if(laser.getTranslateY() > -250 && laser.getTranslateY() < 250) {
			laserTime.play();
		    }
		    alienShootTime.play();
		}
	    };
	timer.schedule(levelTask,2000);
    }
	    

    private void setUpAliens() {
	aliens = new LinkedList<Alien>();
        HBox alienshbox = new HBox();
	HBox alienshbox2 = new HBox();
	HBox alienshbox3 = new HBox();
	HBox alienshbox4 = new HBox();
	HBox alienshbox5 = new HBox();
	aliensvbox = new VBox();
	aliensvbox.getChildren().addAll(alienshbox5,alienshbox4,alienshbox3,alienshbox2, alienshbox);
        for(int i = 0; i < 20; i++) {
            aliens.add(new Alien(new Image("spaceInv/alien1open.png"),32,32,1));
	    if(i < 10) {
		alienshbox.getChildren().add(aliens.get(i));
		aliens.get(i).setYDist(160);
		aliens.get(i).setXDist(i*32);
	    } else {
		alienshbox2.getChildren().add(aliens.get(i));
		aliens.get(i).setYDist(128);
		aliens.get(i).setXDist(i/2*32);
	    }
        }
	for(int i = aliens.size(); i < 40; i++) {
	    aliens.add(new Alien(new Image("spaceInv/alien2open.png"),32,32,2));
	    if(i<30) {
		alienshbox3.getChildren().add(aliens.get(i));
		aliens.get(i).setYDist(96 );
		aliens.get(i).setXDist(i/3*32);
	    } else {
		alienshbox4.getChildren().add(aliens.get(i));
		aliens.get(i).setYDist(64);
		aliens.get(i).setXDist(i/4*32);
	    }
	}
	for(int i = aliens.size(); i < 50; i++) {
	    aliens.add(new Alien(new Image("spaceInv/alien3open.png"),32,32,3));
	    alienshbox5.getChildren().add(aliens.get(i));
	    aliens.get(i).setYDist(32);
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
	KeyFrame alienKey = new KeyFrame(Duration.seconds(.020), alienHandler);
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
		    } else if(alien.getType() == 3) {
			alien.setFill(new ImagePattern(new Image("spaceInv/alien3closed.png")));
		    }
		}
		anim = 1;
	    } else {
		for(Alien alien: aliens) {
		    if(alien.getType() == 1) {
			alien.setFill(new ImagePattern(new Image("spaceInv/alien1open.png")));
		    } else if(alien.getType() == 2) {
			alien.setFill(new ImagePattern(new Image("spaceInv/alien2open.png")));
		    } else if(alien.getType() == 3) {
			alien.setFill(new ImagePattern(new Image("spaceInv/alien3open.png")));
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
	    laser.setTranslateY(laser.getTranslateY()-10);
	    for(Alien alien: aliens) {
		if(laser.getTranslateY()+32 <= alien.getTranslateY()-alien.getYDist()&&
		   laser.getTranslateY()-32 <= alien.getTranslateY()-alien.getYDist()) {
		    if(laser.getBoundsInParent().intersects(alien.getBoundsInParent())) {
			noBullet = true;
			alien.setTranslateX(5000);
			laser.setTranslateX(1000);
			alien.setDead(true);
			if(alien.getType() == 1) {
			    score+= 100;
			} else if(alien.getType() == 2) {
			    score += 200;
			} else if(alien.getType() == 3) {
			    score += 300;
			}
			scoreText.setText("Score: "+Integer.toString(score));
			
		    }
		}
	    }
	    for(Barrier barrier: barriers) {
		if(laser.getBoundsInParent().intersects(barrier.getBoundsInParent())) {
		    noBullet = true;
		    laser.setTranslateX(1000);
		    barrier.setDmgLvl(barrier.getDmgLvl()+1);
		}
	    }
	    if(laser.getTranslateY() < -250) {
	    noBullet = true;
	    laser.setTranslateY(1000);
	    }
	};
	KeyFrame laserKey = new KeyFrame(Duration.seconds(.02), laserHandler);
        laserTime = new Timeline();
	laser.setTranslateY(1000);
        laserTime.setCycleCount(Timeline.INDEFINITE);
        laserTime.getKeyFrames().add(laserKey);
        //laserTime.play();

    }

    public void level() {
	if(this.getLevel() == 1) {
	    this.pause();
	    level1.setOpacity(1);
	} else if(this.getLevel() ==2) {
	    level2.setOpacity(1);
	    this.pause();
	    for(int i = 0; i < 20; i++) {
		aliens.get(i).setTranslateX(0);
		aliens.get(i).setTranslateY(100);
		alienDirection = 0;
		aliens.get(i).setDead(false);
            }
	} else {
	    level3.setOpacity(1);
	    this.pause();
	    for(Alien alien: aliens) {
		alien.setTranslateX(0);
		alien.setTranslateY(100);
		alienDirection = 0;
		alien.setDead(false);
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
	    if(!paused) {
		if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
		    playerTimeL.play();
		}else if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
		    playerTimeR.play();
		} else if((event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W)&&noBullet) {
		    this.shoot();
		}
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
	if(noBullet) {
	    laser.setTranslateY(ship.getTranslateY());
	    laser.setTranslateX(ship.getTranslateX());
	    laserTime.play();
	    noBullet = false;
	}
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

    public void resetBarriers() {

	barriers.get(0).setTranslateX(-250);
	barriers.get(1).setTranslateX(-100);
	barriers.get(2).setTranslateX(100);
	barriers.get(3).setTranslateX(250);
	barriers.get(4).setTranslateX(-190);
	barriers.get(5).setTranslateX(-40);
	barriers.get(6).setTranslateX(40);
	barriers.get(7).setTranslateX(190);
	barriers.get(8).setTranslateX(205);
	barriers.get(9).setTranslateX(-235);
	barriers.get(10).setTranslateX(-55);
	barriers.get(11).setTranslateX(-205);
	barriers.get(12).setTranslateX(-85);
	barriers.get(13).setTranslateX(55);
	barriers.get(14).setTranslateX(83);
	barriers.get(15).setTranslateX(235);
	for(Barrier barrier: barriers) {
	    barrier.setDmgLvl(1);
	}
	for(int i = 0; i < 8; i++) {
	    barriers.get(i).setTranslateY(150);
	}
	for(int i = 8; i < 16; i++) {
	    barriers.get(i).setTranslateY(120);
	}
    }
    
    public void setUpBarriers() {
	barriers = new LinkedList<Barrier>();
	for(int i = 0; i < 16; i++) {
	    Barrier barrier = new Barrier();
	    barriers.add(barrier);
	}
	this.resetBarriers();
	for(Barrier barrier:barriers) {
	    game.getChildren().add(barrier);
	}
    }

    
}
