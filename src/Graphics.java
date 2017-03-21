//FILE::Graphics.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Graphics extends Application
{
    private final String SCREEN_TITLE = "Warrior of Stone: Well Probably...";
    private final String ASSET_PATH = System.getProperty("user.dir")+"\\assets\\";

    //line//
    private Rectangle2D screen;

    private Stage primaryStage;
    private Scene primaryScene;
    private Canvas primaryCanvas;
    private GraphicsContext graphicsContext;

    private ArrayList<Pair<String, Pair<Float, Float>>> entitiesToBeDrawn;
    private ArrayList<Rectangle> collisionBoxesToBeDrawn;
    private HashMap<String, Image> entityImageMap;
    private HashMap<String, Boolean> keyMap;

    private static WarriorOfStone_Main bootGameInstance;

    /*
     * displayScreen takes the compiled information to assemble the scene that is displayed.
     */

    private void displayScreen()
    {
        this.graphicsContext.setFill(Color.WHITESMOKE);
        //line//
        this.graphicsContext.fillRect(0, 0, this.screen.getWidth(), this.screen.getHeight());

        for(Pair entity:this.entitiesToBeDrawn)
        {
            Image sprite = this.entityImageMap.get(entity.getKey());
            Pair<Float, Float> coordinatePair = (Pair<Float, Float>) entity.getValue();
            this.graphicsContext.drawImage(sprite, coordinatePair.getKey(), coordinatePair.getValue());
        }

        this.graphicsContext.setFill(Color.rgb(255, 102, 0, .5));
        for(Rectangle colBox:this.collisionBoxesToBeDrawn)
            this.graphicsContext.fillRect(colBox.getX(), colBox.getY(), colBox.getWidth(), colBox.getHeight());

        this.primaryStage.setTitle(SCREEN_TITLE);
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(this.primaryScene);
        this.primaryStage.show();
    }

    /**
     * Accepts a URI to an image and preloads it into the image database hashmap
     * @param URI
     */
    public void registerEntityImage(String URI)
    {
        Image img = new Image("file:"+ASSET_PATH+"images\\"+URI);
        this.entityImageMap.put(URI, new Image("file:"+ASSET_PATH+"images\\"+URI));//, img.getWidth()*2, img.getHeight()*2, false, false));
    }

    /**
     * Creates the human interaction capablities of the game and prepopulates the hashmap with all false values
     */
    private void registerKeyStrokes()
    {
        for(KeyCode key:KeyCode.values())
            this.keyMap.put(key.getName(), false);

        this.primaryScene.setOnKeyPressed(e -> this.keyMap.put(e.getCode().getName(), true));
        this.primaryScene.setOnKeyReleased(e -> this.keyMap.put(e.getCode().getName(), false));
    }

    /**
     * Inputs an arraylist of all objects that are to be drawn and then calls the displayScreen function
     * @param entitiesToBeDrawn
     */
    public void update(ArrayList<Pair<String, Pair<Float, Float>>> entitiesToBeDrawn, ArrayList<Rectangle> collisionBoxes)
    {
        this.entitiesToBeDrawn = entitiesToBeDrawn;
        this.collisionBoxesToBeDrawn = collisionBoxes;
        this.displayScreen();
    }

    private void zoom()
    {
        this.primaryCanvas.setScaleX(2);
        this.primaryCanvas.setScaleY(2);
    }

    @Override
    public void init()
    {
        this.entitiesToBeDrawn = new ArrayList<Pair<String, Pair<Float, Float>>>();
        this.entityImageMap = new HashMap<>();
        this.keyMap = new HashMap<>();
        this.collisionBoxesToBeDrawn = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;

        Group root = new Group();
        this.primaryScene = new Scene(root);

        //Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        this.screen = Screen.getPrimary().getVisualBounds();

        this.primaryCanvas = new Canvas(screen.getWidth(), screen.getHeight());
        root.getChildren().add(this.primaryCanvas);
        //root.resize(screen.getWidth(), screen.getHeight());

        this.graphicsContext = this.primaryCanvas.getGraphicsContext2D();

        this.registerKeyStrokes();
        this.primaryStage.setOnCloseRequest(e -> System.exit(1));

        this.primaryStage.setFullScreen(true);

        this.bootGameInstance.GAME_LOOP(this, this.keyMap);
    }

    public static void load(String[] args, WarriorOfStone_Main main)
    {
        bootGameInstance = main;
        Application.launch(args);
    }
}