package BaseEngine;//FILE::BaseEngine.Graphics.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Graphics extends Application
{
    private final String SCREEN_TITLE = "Warrior of Stone: Well Probably...";
    private final String ASSET_PATH = System.getProperty("user.dir")+"\\assets\\";
    private final double SCALE_FACTOR = 3;

    //line//
    private Rectangle2D screen;

    private Stage primaryStage;
    private Scene primaryScene;
    private StackPane mainStackPane;
    private ScrollPane primaryScrollPane;
    private Canvas primaryCanvas;
    private GridPane primaryHUDPane;
    private GraphicsContext graphicsContext;

    private Float width, height;
    private ArrayList<Pair<String, Pair<Float, Float>>> entitiesToBeDrawn;
    private ArrayList<Rectangle> collisionBoxesToBeDrawn;
    private Pair<Pair<String, Boolean>, Pair<Float, Float>> playerDrawInformation;

    private HashMap<String, Image> entityImageMap;
    private HashMap<String, Boolean> keyMap;

    private static WarriorOfStone_Main bootGameInstance;

    /**
     * displayScreen takes the compiled information to assemble the scene that is displayed.
     */
    private void displayScreen()
    {
        this.graphicsContext.setFill(Color.LIGHTBLUE);
        //line//
        this.graphicsContext.fillRect(0, 0, this.width, this.height);

        Pair<String, Boolean> uriAndDirection = this.playerDrawInformation.getKey();
        Pair<Float, Float> coordinatePair = this.playerDrawInformation.getValue();
        Image sprite = this.entityImageMap.get(uriAndDirection.getKey());

        if(!uriAndDirection.getValue())
            this.graphicsContext.drawImage(sprite, (coordinatePair.getKey()*SCALE_FACTOR), coordinatePair.getValue()*SCALE_FACTOR);
        else
            this.graphicsContext.drawImage(sprite, (coordinatePair.getKey()*SCALE_FACTOR)+sprite.getWidth(), coordinatePair.getValue()*SCALE_FACTOR, -sprite.getWidth(), sprite.getHeight());

        for(Pair entity:this.entitiesToBeDrawn)
        {
            sprite = this.entityImageMap.get(entity.getKey());
            coordinatePair = (Pair<Float, Float>) entity.getValue();

            this.graphicsContext.drawImage(sprite, coordinatePair.getKey()*SCALE_FACTOR, coordinatePair.getValue()*SCALE_FACTOR);
        }

        this.updateScreenPosition();

        /*this.graphicsContext.setFill(Color.rgb(255, 102, 0, .5));
        for(Rectangle colBox:this.collisionBoxesToBeDrawn)
            this.graphicsContext.fillRect(colBox.getX()*SCALE_FACTOR, colBox.getY()*SCALE_FACTOR, colBox.getWidth()*SCALE_FACTOR, colBox.getHeight()*SCALE_FACTOR);*/

        this.primaryStage.setTitle(SCREEN_TITLE);
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(this.primaryScene);
        this.primaryStage.show();
    }

    private void updateScreenPosition()
    {
        //Double playerPosition_X = this.playerDrawInformation.getValue().getKey()*SCALE_FACTOR/this.width;
        Double playerPosition_X = this.playerDrawInformation.getValue().getKey().doubleValue();
        Double playerPosition_Y = this.playerDrawInformation.getValue().getValue().doubleValue();

        this.primaryScrollPane.setHvalue((playerPosition_X - .165*this.screen.getWidth()) * SCALE_FACTOR / (this.width-this.screen.getWidth()));
        this.primaryScrollPane.setVvalue((playerPosition_Y - .165*this.screen.getHeight()) * SCALE_FACTOR / (this.height-this.screen.getHeight()));
    }

    /**
     * Accepts a URI to an image and preloads it into the image database hashmap
     * @param URI
     */
    public void registerEntityImage(String URI)
    {
        Image img = new Image("file:"+ASSET_PATH+"images\\"+URI);
        this.entityImageMap.put(URI, new Image("file:"+ASSET_PATH+"images\\"+URI, img.getWidth()*SCALE_FACTOR, img.getHeight()*SCALE_FACTOR, false, false));
    }

    public void loadFileSpecifiedImages() throws FileNotFoundException
    {
        Scanner imageReader = new Scanner(new File(this.ASSET_PATH+"images\\imageURIs.txt"));

        while(imageReader.hasNextLine())
            this.registerEntityImage(imageReader.nextLine());
    }

    public void buildHUD()
    {
        //Define Padding
        HBox padding = new HBox();
        padding.setMinSize(10, 10);

        //Create StackPane for the Health Animation
        StackPane healthPane = new StackPane();

        //For adding Health
        Button statusBar = new Button();
        statusBar.setMinSize(405, 135);
        statusBar.setBackground(new Background(new BackgroundImage(this.entityImageMap.get("statusBar.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        Button healthBar = new Button();
        healthBar.setMinSize(405, 135);
        healthBar.setBackground(new Background(new BackgroundImage(this.entityImageMap.get("healthBar.gif"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        healthPane.getChildren().add(statusBar);
        healthPane.getChildren().add(healthBar);

        HBox lifeContainerPadding = new HBox();
        lifeContainerPadding.setMinSize(0, 10);
        HBox lifeContainers = new HBox();
        lifeContainers.setSpacing(10);
        for(int i=0; i<3; i++)
        {
            //For adding Life Containers
            Button lifeContainer = new Button();
            lifeContainer.setMinSize(45, 45);
            lifeContainer.setBackground(new Background(new BackgroundImage(this.entityImageMap.get("lifeContainer.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

            lifeContainers.getChildren().add(lifeContainer);
        }

        this.primaryHUDPane.add(padding, 0, 0);
        this.primaryHUDPane.add(healthPane, 1, 1);
        this.primaryHUDPane.add(lifeContainerPadding, 1, 2);
        this.primaryHUDPane.add(lifeContainers, 1, 3);
    }

    /**
     * Creates the human interaction capablities of the game and prepopulates the hashmap with all false values
     */
    private void registerKeyStrokes()
    {
        for(KeyCode key : KeyCode.values())
            this.keyMap.put(key.getName(), false);

        this.primaryScrollPane.setOnKeyPressed(e -> this.keyMap.put(e.getCode().getName(), true));
        this.primaryScrollPane.setOnKeyReleased(e -> this.keyMap.put(e.getCode().getName(), false));
    }

    private void disableScroll()
    {
        this.primaryScrollPane.addEventFilter(ScrollEvent.SCROLL,new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaX() != 0) {
                    event.consume();
                }
            }
        });

        List<KeyEvent> mappedEvents = new ArrayList<>();

        primaryScrollPane.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if(mappedEvents.remove(event))
                    return;

                switch (event.getCode())
                {
                    case UP:
                    case DOWN:
                    case LEFT:
                    case RIGHT:
                        KeyEvent newEvent = remap(event);
                        mappedEvents.add(newEvent);
                        event.consume();
                        Event.fireEvent(event.getTarget(), newEvent);
                }
            }

            private KeyEvent remap(KeyEvent event)
            {
                KeyEvent newEvent = new KeyEvent(
                        event.getEventType(),
                        event.getCharacter(),
                        event.getText(),
                        event.getCode(),
                        !event.isShiftDown(),
                        event.isControlDown(),
                        event.isAltDown(),
                        event.isMetaDown()
                );
                return newEvent.copyFor(event.getSource(), event.getTarget());
            }
        });
    }

    /**
     * Inputs an arraylist of all objects that are to be drawn and then calls the displayScreen function
     * @param entitiesToBeDrawn
     */
    public void update(Pair<Pair<String, Boolean>, Pair<Float, Float>> playerPosition, ArrayList<Pair<String, Pair<Float, Float>>> entitiesToBeDrawn, ArrayList<Rectangle> collisionBoxes, Float width, Float height)
    {
        this.playerDrawInformation = playerPosition;
        this.entitiesToBeDrawn = entitiesToBeDrawn;
        this.collisionBoxesToBeDrawn = collisionBoxes;

        this.primaryCanvas.setWidth(width);
        this.primaryCanvas.setHeight(height);

        this.width = width;
        this.height = height;

        this.primaryScrollPane.requestFocus();

        this.displayScreen();
    }

    @Override
    public void init()
    {
        this.entitiesToBeDrawn = new ArrayList<Pair<String, Pair<Float, Float>>>();
        this.entityImageMap = new HashMap<>();
        this.keyMap = new HashMap<>();
        this.collisionBoxesToBeDrawn = new ArrayList<>();

        this.width = 0f;
        this.height = 0f;
    }

    public void configureScrollPane()
    {
        this.primaryScrollPane = new ScrollPane(this.primaryCanvas);
        this.primaryScrollPane.setPrefSize(this.screen.getMaxX(), this.screen.getMaxX());

        this.primaryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.primaryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;

        //Set Main Grouping Element
        Group root = new Group();
        this.primaryScene = new Scene(root);

        //Get Screen Properties
        this.screen = Screen.getPrimary().getVisualBounds();

        this.mainStackPane = new StackPane();

        //Initialize Visual Componenents
        this.primaryCanvas = new Canvas(0, 0);
        this.primaryHUDPane = new GridPane();

        configureScrollPane();

        registerKeyStrokes();

        this.mainStackPane.getChildren().add(this.primaryScrollPane);
        this.mainStackPane.getChildren().add(this.primaryHUDPane);

        root.getChildren().add(this.mainStackPane);
        this.disableScroll();

        this.graphicsContext = this.primaryCanvas.getGraphicsContext2D();

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