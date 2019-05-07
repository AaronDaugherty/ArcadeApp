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

/**
 *Represents a game of SpaceInvaders.
 *
 */
public class GameSI extends Group {

    public final ImagePattern alienLsrImg = new ImagePattern(
        new Image("spaceInv/alienlaser1.png"));
    
    ArcadeApp application;
    Rectangle ship;
    boolean hurt;
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
    TimerTask lifeTask;
    Timer lifeTime;
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
    int lives;
    Text livesText;
    ImageView gameOver;
    ImageView win;
    
    /**
     * Constructor for GameSI class. Constructs a game of SpaceInvaders.
     * Sets up the needed variables for the game.
     * @param application the parent app running the game
     */
    public GameSI(ArcadeApp application) {
        this.setUpVariables();
        win = new ImageView(new Image("spaceInv/youwin.png"));
        win.setOpacity(0);
        livesText = new Text("Lives: "+Integer.toString(lives));
        livesText.setFill(Color.rgb(255,255,255));
        timer = new Timer(true);
        this.setUpLaser();
        this.setUpPlayer();
        game = new StackPane();
        this.setUpAliens();
        this.setUpLevel();
        game.getChildren().addAll(space,laser,ship,level1,
                                  level2,level3,scoreText,livesText,gameOver,win);
        for(int i = 0; i < 50; i++) {
            game.getChildren().add(aliens.get(i));
        }
        this.setUpAlienShoot();
        //Main Menu button for SpaceInvaders
        menu = new ArcButton(0,0,new Image("spaceInv/mainMenu.png"), e -> {
                application.setScene(application.getScene());
                this.pause();
        });
        //Restart button for SpaceInvaders    
        reset = new ArcButton(100,0,new Image("spaceInv/restart.png"), e -> this.reset());
        quit = new ArcButton(200, 0, new Image("spaceInv/exit.png"), e-> this.quit());
        anim = 1;
        alienDirection = 0;
        rightBound = new Rectangle(1,500,Color.BLUE);
        leftBound = new Rectangle(1,500,Color.BLUE);
        game.getChildren().add(rightBound);
        this.getChildren().addAll(frame,menu,reset,quit,game,nebula,joystick);
        noBullet = true;
        this.setUpAnimations();
        this.setUpBarriers();
        this.pause();
        this.translationSetup();
        
    }

    /**
     * This helper method instantiates many
     * needed variables for the {@code GameSI} game.
     */
    public void setUpVariables() {
        space = new Rectangle(700,500, new ImagePattern(new Image("spaceInv/space.png")));
        frame = new Rectangle(1024, 768, new ImagePattern(new Image("spaceInv/frame.png")));
        nebula = new Rectangle(556, 799, new ImagePattern(new Image("spaceInv/nebula.jpg")));
        joystick = new Rectangle(200, 200, new ImagePattern(new Image("spaceInv/joystick.png")));
    
        lives = 3;
        gameOver = new ImageView(new Image("spaceInv/gameover.png"));
        
        gameOver.setOpacity(0);
        hurt = false;
        this.application = application;
        noBullet = true;
        level = 1;
        rand = new Random();
        scoreText = new Text("Score: "+Integer.toString(score));
        scoreText.setFill(Color.rgb(255,255,255));
    }

    /**
     * This helper method translates all of the variables
     * that are needed in the constructor.
     */
    public void translationSetup() {
        scoreText.setTranslateY(-225);
        scoreText.setTranslateX(-100);
        ship.setTranslateY(200);
        leftBound.setTranslateX(0);
        rightBound.setTranslateX(350);
        menu.setTranslateX(200);
        menu.setTranslateY(50);
        nebula.setTranslateX(862);
        joystick.setTranslateX(580);
        joystick.setTranslateY(580);
        livesText.setTranslateY(-225);
        livesText.setTranslateX(50);
        game.setTranslateX(100);
        game.setTranslateY(134);
    }

    /**
     * This method resets the game to the base state
     * for the game at level 1.
     */
    public void reset() {
        gameOver.setOpacity(0);
        hurt = false;
        lives = 3;
        livesText.setText("Lives: "+Integer.toString(lives));
        score = 0;
        scoreText.setText("Score: "+Integer.toString(score));
        reset.setDisable(true);
        menu.setDisable(true);
        alienDirection = 0;
        ship.setTranslateX(0);
        laser.setTranslateY(2000);
        for(int i = 0; i < 50; i++) {
            aliens.get(i).setDead(false);
            
        }
        for(Alien alien: aliens) {
            alien.setCanShoot(false);
        }
        for(int i = 0; i < 10; i++) {
            aliens.get(i).setCanShoot(true);
        }
        this.alienShoot();
        reset.setTranslateX(750);
        this.setLevel(1);
        this.level();
        this.resetBarriers();
        this.resetAliens();
        win.setOpacity(0);
    }

    /**
     * This ends all of the timelines and timers
     * to make a nice quit option for the user. It makes
     * the experience much neater.
     */
    public void quit() {
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
    }

    /**
     * This method finds the create laser and timeline
     * and plays the correct timeline to make the laser
     * move when the alien shoots.
     * @param i the laser that is being utilized
     * @return event the event that handles the aliens laser
     */
    EventHandler<ActionEvent> createLaserShoot(int i) {
        EventHandler<ActionEvent> event = e -> {
            lasers.get(i).setTranslateY(lasers.get(i).getTranslateY()+10);
            if(lasers.get(i).getTranslateY() < -500) {
                lasers.get(i).setTranslateY(1000);
                alienLaserTimes.get(i).pause();
            }
            if(lasers.get(i).getBoundsInParent().intersects(ship.getBoundsInParent())&&!hurt) {
                lives = lives -1;
                livesText.setText("Lives: "+Integer.toString(lives)); 
                hurt = true;
                paused = true;
                if(lives == 0) {
                    this.gameOver();
                } else {
                    ship.setFill(new ImagePattern(new Image("spaceInv/shipdeath.png")));
                    lifeTask = new TimerTask() {
                            public void run() {
                                ship.setFill(new ImagePattern(new Image("spaceInv/ship.png")));
                                paused = false;
                                hurt = false;
                                cancel();
                            }
                        };
                    lifeTime = new Timer(true);
                    lifeTime.schedule(lifeTask,2000);
            
                }
            }
            for(Barrier barrier: barriers) {
                if(lasers.get(i).getBoundsInParent().intersects(barrier.getBoundsInParent())) {
                    lasers.get(i).setTranslateY(-1000);
                    barrier.setDmgLvl(barrier.getDmgLvl()+1);
                }
            }
        };
        return event;
    }

    
    /**
     *Sets up shooting for aliens. Creates timelines for each laser
     *and sets up animation.
     */
    public void setUpAlienShoot() {
        alienShootTime = new Timeline();
        alienShootTime.getKeyFrames().add(new KeyFrame(Duration.seconds(.03),
                                                       e-> System.out.println("TEST")));
        lasers = new LinkedList<Rectangle>();
        Rectangle laser = new Rectangle(8,16,alienLsrImg);
        for(int i = 0; i < 10; i++) {
            laser = new Rectangle(8,16,alienLsrImg);
            lasers.add(laser);
            game.getChildren().add(laser);
            laser.setTranslateX(1000);
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

    /**
     *Spawns alien laser in the correct location and plays
     *timeline for laser. 
     *@param An Alien onject
     */
    public void shootAlien(Alien alien) {
        alien.getLaser().setTranslateX(alien.getTranslateX());
        alien.getLaser().setTranslateY(alien.getTranslateY());
        int laserNum = aliens.indexOf(alien);
        laserNum = laserNum % 10;
        alienLaserTimes.get(laserNum).play();
    }

    /**
     *Determines if the alien can shoot and adds it to a list.
     *Enables random shooting by having an alien fire every time 5
     *is chosen randomly out of 20.
     */
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
        alienShootTime.getKeyFrames().set(0,alienShootKey);
    }

    /**
     * This method gets the quit button that is being used.
     * @return quit the quit button for the game
     */
    public ArcButton getQuit() {
        return quit;
    }

    /**
     * This method gets the reset button that is being used.
     * @return reset the reset button for the game
     */
    public ArcButton getReset() {
        return reset;
    }

    /**
     * This is a helper method that sets up the levels
     * adn their appriate text for the game;
     */
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

    /**
     * This method makes the appropriate handler
     * to make the ship go right.
     * @return e the event making the ship go right
     */
    public EventHandler<ActionEvent> movementR() {
        return e -> {
            this.shipRight();
        };
    }

    /**
     * This method makes the appropriate handler
     * to make the ship go left.
     * @return e the event making the ship go left
     */
    public EventHandler<ActionEvent> movementL() {
        return e -> {
            this.shipLeft();
        };
    }

    /**
     * This method sets up the player and creates the appropriate
     * timelines and handlers that the user will be using for the game.
     */
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
        lifeTime = new Timer(true);
        lifeTask = new TimerTask() {
                public void run() {
                    ship.setFill(new ImagePattern(new Image("spaceInv/ship.png")));
                    paused = false;
                    hurt = false;
                    cancel();
                }
            };
    }

    /**
     * This method pauses the game and all the timelines.
     */
    public void pause() {
        paused = true;
        laserTime.pause();
        alienTime.pause();
        animTime.pause();
        playerTimeR.pause();
        playerTimeL.pause();
        for(Timeline timeline: alienLaserTimes) {
            timeline.pause();
        }
    }
    
    /**
     * This method plays the game and all the timelines. It
     * also resets the appropriate variables.
     */
    public void play() {
        this.level();
        TimerTask levelTask = new TimerTask() {
                public void run() {
                    for(Timeline timeline: alienLaserTimes) {
                        timeline.play();
                    }
                    alienTime.play();
                    animTime.play();
                    level1.setOpacity(0);
                    level2.setOpacity(0);
                    level3.setOpacity(0);
                    paused = false;
                    noBullet = true;
                    reset.setDisable(false);
                    menu.setDisable(false);
                    //cancel();
                    if(laser.getTranslateY() > -250 && laser.getTranslateY() < 250) {
                        laserTime.play();
                    }
                    alienShootTime.play();
                    cancel();
                }
            };
        timer.schedule(levelTask,2000);
    }
    /**
     * This method resets the position and instance variables
     * for each alien in the game.
     */
    public void resetAliens() {
        for(int i = 0; i < 10; i++) {
            aliens.get(i).setTranslateX(32*i);
            aliens.get(i).setTranslateY(-96);
        }
        for(int i = 10; i < 20; i++) {
            aliens.get(i).setTranslateX((i-10)*32);
            aliens.get(i).setTranslateY(-128);
        }
        for(int i = 20; i < 30; i++) {
            aliens.get(i).setTranslateX((i-20)*32);
            aliens.get(i).setTranslateY(-160);
        }
        for(int i = 30; i < 40; i++) {
            aliens.get(i).setTranslateX((i-30)*32);
            aliens.get(i).setTranslateY(-192);
        }
        for(int i = 40; i < 50; i++) {
            aliens.get(i).setTranslateX((i-40)*32);
            aliens.get(i).setTranslateY(-224);
        }
    
    }

    /**
     * This method creates the aliens and sets their instance
     * variables for the game the very first time the program is run.
     */
    private void setUpAliens() {
        aliens = new LinkedList<Alien>();
        for(int i = 0; i < 20; i++) {
            aliens.add(new Alien(new Image("spaceInv/alien1open.png"),32,32,1));
            if(i < 10) {
                aliens.get(i).setYDist(160);
                aliens.get(i).setXDist(i*32);
            } else {
                aliens.get(i).setYDist(128);
                aliens.get(i).setXDist(i/2*32);
            }
        }
        for(int i = aliens.size(); i < 40; i++) {
            aliens.add(new Alien(new Image("spaceInv/alien2open.png"),32,32,2));
            if(i<30) {
                aliens.get(i).setYDist(96 );
                aliens.get(i).setXDist(i/3*32);
            } else {
                aliens.get(i).setYDist(64);
                aliens.get(i).setXDist(i/4*32);
            }
        }
        for(int i = aliens.size(); i < 50; i++) {
            aliens.add(new Alien(new Image("spaceInv/alien3open.png"),32,32,3));
            aliens.get(i).setYDist(32);
        }
        this.resetAliens();
        KeyFrame alienKey = new KeyFrame(Duration.seconds(.020), this.createAlienHandler());
        alienTime = new Timeline();
        alienTime.setCycleCount(Timeline.INDEFINITE);
        alienTime.getKeyFrames().add(alienKey);
        alienTime.play();
    }

    /**
     * This method handles alien collision and movement each time
     * it is used.
     * @return event the EventHandler to handle collision
     */
    public EventHandler<ActionEvent> createAlienHandler() {
        EventHandler<ActionEvent> event = e -> {
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
            for(Alien alien: aliens) {
                if(alien.getTranslateY() <= ship.getTranslateY()) {
                    this.gameOver();
                }
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
        return event;
    }

    /**
     * This method sets up the animations for aliens when the aliens
     * are moving throughout the game.
     */
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
    /**
     * This method creates the event handler for the user's laser
     * causing it to kill aliens and destroy blocks.
     * @return event the event handler to handle user laser movement
     */
    public EventHandler<ActionEvent> createLaserHandler() {
        EventHandler<ActionEvent> event = e -> {
            laser.setTranslateY(laser.getTranslateY()-10);
            for(Alien alien: aliens) {
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
            
                    alien.setCanShoot(false);
                    if(aliens.indexOf(alien) > 39) {
            
                    } else {
                        aliens.get(aliens.indexOf(alien) + 10).setCanShoot(true);
                        this.alienShoot();
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
        return event;
    }
    /**
     * This method sets up the laser for the user and the timeline that it is on.
     */
    private void setUpLaser() {
        laser = new Rectangle(4,8, new ImagePattern(new Image("spaceInv/laser.png")));
        KeyFrame laserKey = new KeyFrame(Duration.seconds(.02), this.createLaserHandler());
        laserTime = new Timeline();
        laser.setTranslateY(1000);
        laserTime.setCycleCount(Timeline.INDEFINITE);
        laserTime.getKeyFrames().add(laserKey);
        //laserTime.play();

    }

    /**
     * This method handles the levelling for the user. There are 3 levels and then a win screen.
     */
    public void level() {
        if(this.getLevel() == 1) {
            this.pause();
            level1.setOpacity(1);
        } else if(this.getLevel() ==2) {
            for(int i = 0; i < 50; i++) {
                aliens.get(i).setDead(false);
                if(i < 10) {
                    aliens.get(i).setCanShoot(true);
                } else {
                    aliens.get(i).setCanShoot(false);
                }
            }
            level2.setOpacity(1);
            this.pause();
            this.resetAliens();
            for(int i = 0; i < 50; i++) { 
                int k = 32;
                k = k + (-32 * (i/10));
                aliens.get(i).setTranslateY(k);
            }
        } else if(this.getLevel() == 3){
            level3.setOpacity(1);
            this.pause();
            this.resetAliens();
            for(int i = 0; i < 50; i++) {
                aliens.get(i).setDead(false);
                if(i<10) {
                    aliens.get(i).setCanShoot(true);
                } else {
                    aliens.get(i).setCanShoot(false);
                }
            }
            for(int i = 0; i < 50; i ++) {
                int k = 96;
                k = k + (-32 * (i/10));
                aliens.get(i).setTranslateY(k);
            }

        } else {
            this.win();
        }
    }
    
    /**
     * This method gets the level that the user is on.
     * @return level the level the user is on
     */
    public int getLevel() {
        return level;
    }

    /**
     * This method sets the user's level
     * @param level the level that the user will be on
     */
    public void setLevel(int level) {
        this.level = level;
        this.play();
    }

    /**
     * This method creates the KeyHandler for how the user's keys will affect the game.
     * @return event the KeyEvent the user is currently doing
     */
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

    /**
     * This method creates the second KeyHandler for how releasing keys affects the game.
     * This method helps remove input lag in the game.
     * @return event the KeyEvent the user has finished doing
     */
    private EventHandler<? super KeyEvent> createKeyHandler2() {
        return event -> {
            if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                playerTimeL.pause();
            } else if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                playerTimeR.pause();
            }
        };
    }

    /**
     * This method handles the actual shooting by the user when approriately called
     */
    public void shoot() {
        if(noBullet) {
            laser.setTranslateY(ship.getTranslateY());
            laser.setTranslateX(ship.getTranslateX());
            laserTime.play();
            noBullet = false;
        }
    }

    /**
     * This method causes the ship/user to go left.
     */
    public void shipLeft() {
        if(ship.getTranslateX() - 5 < -333) {
            ship.setTranslateX(-333);
        } else {
            ship.setTranslateX(ship.getTranslateX() -5);
        }
    }

    /**
     * This method causes the ship/user to go right.
     */
    public void shipRight() {
        if(ship.getTranslateX() + 5> 333) {
            ship.setTranslateX(333);
        } else {
            ship.setTranslateX(ship.getTranslateX() + 5);
        }
    }

    
    /**
     * This method resets the barrier location for a game reset. It also
     * resets their damage levels.
     */
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

    /**
     * This method sets up the barriers initially by adding them to their
     * list and to the scene graph and setting their position.
     */
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

    /**
     * This method causes the game over screen to appear and pause the game
     */
    public void gameOver() {
        this.pause();
        gameOver.setOpacity(1);
    }

    /**
     * This method causes the win screen to appear and pause the game.
     */
    public void win() {
        this.pause();
        win.setOpacity(1);
    }
    
}
