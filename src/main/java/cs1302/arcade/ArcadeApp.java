package cs1302.arcade;

import javafx.geometry.Insets;
import java.util.Random;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

public class ArcadeApp extends Application {
    Stage stage;
    Group group = new Group();           // main container
    Scene scene;
    /**
     * Return a mouse event handler that moves to the rectangle to a random
     * position any time a mouse event is generated by the associated node.
     * @return the mouse event handler
     */
    private EventHandler<? super MouseEvent> createMouseHandler() {
        return event -> {
            
        };
    } // createMouseHandler

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public Scene getScene() {
        return scene;
    }

    
    
    /** {@inheritdoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        StackPane sp2048 = new StackPane();
        StackPane spInvaders = new StackPane();
        VBox vbox = new VBox();
        Button bnt2048 = new Button();
        Game2048 game2048 = new Game2048(this);
        GameSI gameSI = new GameSI(this);
        Button bntSI = new Button();
        
        //Creating 2048 box
        ImageView start2048nm = new ImageView(new Image("2048/2048GreyStart.png"));
        ImageView start2048wm = new ImageView(new Image("2048/2048RedStart.png"));
        sp2048.getChildren().add(new ImageView(new Image("2048/2048Background.png")));
        bnt2048.setGraphic(start2048nm);
        bnt2048.setPadding(Insets.EMPTY);
        bnt2048.setStyle("-fx-focus-color: transparent;");
        bnt2048.setOnMouseEntered(e -> bnt2048.setGraphic(start2048wm));
        bnt2048.setOnMouseExited(e -> bnt2048.setGraphic(start2048nm));

        sp2048.getChildren().add(bnt2048);
        sp2048.setAlignment(bnt2048,Pos.BOTTOM_CENTER);
        bnt2048.setTranslateY(-40);
        

        //Creating Space Invaders box
        ImageView startSI = new ImageView(new Image("spaceInv/start.png"));

        spInvaders.getChildren().add(new ImageView(new Image("spaceInv/background.png")));
        ImageView logo = new ImageView(new Image("spaceInv/logo.png"));
        logo.setTranslateY(-50);
        bntSI.setGraphic(startSI);
        bntSI.setPadding(Insets.EMPTY);
        bntSI.setTranslateY(120);
        spInvaders.getChildren().addAll(logo,bntSI);

        group.setOnMouseClicked(createMouseHandler());
        vbox.getChildren().addAll(sp2048, spInvaders);
        group.getChildren().add(vbox);
        
        scene = new Scene(group, 1024, 768);
        scene.getStylesheets().add("src/main/java/cs1302/arcade/ButtonStyle.css");
        Scene scene2048 = new Scene(game2048, 1024, 768);
        Scene sceneSI = new Scene(gameSI, 1024, 768);
        bnt2048.setOnMouseClicked(e ->  {
                stage.setScene(scene2048);
                game2048.requestFocus();
            });

        bntSI.setOnMouseClicked( e -> {
                stage.setScene(sceneSI);
		gameSI.requestFocus();
		gameSI.play();
            });
        stage.setTitle("cs1302-arcade!");
        stage.setScene(scene);
        stage.sizeToScene();
	stage.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
		if(key.getCode() == KeyCode.SPACE) {
		    key.consume();
		}
	    });
        stage.setResizable(false);
        stage.show();
        
        // the group must request input focus to receive key events
        // @see https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#requestFocus--

    } // start


} // ArcadeApp
