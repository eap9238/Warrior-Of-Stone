package World;//FILE::World.Level.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import Entities.Boss;
import Entities.Character;
import Entities.Human;
import Entities.Monster;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Level
{
    private final String ASSET_PATH = System.getProperty("user.dir")+"\\assets\\";

    private Level nextLevel;
    private String background;
    private String music;
    private Float width, height;
    private ArrayList<Block> levelBlocks;
    private Pair<Float, Float> initialPlayerCoordinates;
    private ArrayList<Character> levelEntities;

    public Level()throws Exception
    {
        this.init();
        this.readInFile("main");
    }

    private Level(String levelName) throws Exception
    {
        if(!levelName.equals("EOG"))
        {
            this.init();
            this.readInFile(levelName);
        }
    }

    private void init()
    {
        this.levelBlocks = new ArrayList<>();
        this.initialPlayerCoordinates = new Pair<>(0f, 0f);
        this.levelEntities = new ArrayList<>();
    }

    private void readInFile(String levelName) throws Exception
    {
        Scanner readLevel = new Scanner(new File(ASSET_PATH+"level\\"+levelName+".lvl"));

        while(readLevel.hasNext())
        {
            String in = readLevel.nextLine();
            if(in.matches(".*:.*") || in.matches(".*:.*:.*") || in.matches(".*:.*:.*:.*:.*"))
            {
                String[] bufferedIn = in.split(":");

                switch(bufferedIn[0])
                {
                    case "nextLevel":
                        this.nextLevel = new Level(bufferedIn[1]);
                        break;
                    case "background":
                        this.background = bufferedIn[1];
                        break;
                    case "music":
                        this.music = bufferedIn[1];
                        break;
                    case "dim":
                        this.width = Float.parseFloat(bufferedIn[1]);
                        this.height = Float.parseFloat(bufferedIn[2]);
                        break;
                    case "block":
                        this.levelBlocks.add(new Block(bufferedIn));
                        break;
                    case "player":
                        this.initialPlayerCoordinates = new Pair<>(Float.parseFloat(bufferedIn[1]), Float.parseFloat(bufferedIn[2]));
                        break;
                    case "character":
                        switch(bufferedIn[1])
                        {
                            case "human":
                                this.levelEntities.add(new Human(bufferedIn[2], Float.parseFloat(bufferedIn[3]), Float.parseFloat(bufferedIn[4])));
                                break;
                            case "monster":
                                this.levelEntities.add(new Monster(bufferedIn[2], Float.parseFloat(bufferedIn[3]), Float.parseFloat(bufferedIn[4])));
                                break;
                            case "boss":
                                this.levelEntities.add(new Boss());
                                break;
                        }
                        break;
                    default:
                        System.err.println("World.Level File Format Corrupted");
                        break;
                }
            }
        }
    }

    public Float getWidth()
    {
        return this.width;
    }
    public Float getHeight()
    {
        return height;
    }

    public Pair<Float, Float> getPlayerCoordinates()
    {
        return this.initialPlayerCoordinates;
    }

    public ArrayList<Pair<String, Pair<Float, Float>>> getLevelEntities()
    {
        ArrayList<Pair<String, Pair<Float, Float>>> returnLevelEntities = new ArrayList<>();

        for(Block b:this.levelBlocks)
            returnLevelEntities.add(new Pair<>(b.getBLOCK_URI(), b.getBlockCoordinates()));

        for(Character c:this.levelEntities)
            returnLevelEntities.add(new Pair<>(c.getSPRITE_URI(), c.getPosition()));

        return returnLevelEntities;
    }

    public ArrayList<Rectangle> getCollisionBoxes()
    {
        ArrayList<Rectangle> levelCollisionBoxes = new ArrayList<>();
        for(Block b:this.levelBlocks)
            levelCollisionBoxes.add(b.getCollisionBox());

        return levelCollisionBoxes;
    }
}