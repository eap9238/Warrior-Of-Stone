//FILE::Level.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Level
{
    private final String ASSET_PATH = System.getProperty("user.dir")+"\\assets\\";

    private Level nextLevel;
    private String background;
    private String music;
    private ArrayList<Block> levelBlocks;
    private Pair<Float, Float> playerCoordinates;
    private ArrayList<Character> levelEntities;

    Level()throws Exception
    {
        this.init();
        this.readInFile("main");
    }

    Level(String levelName) throws Exception
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
        this.playerCoordinates = new Pair<>(0f, 0f);
        this.levelEntities = new ArrayList<>();
    }

    private void readInFile(String levelName) throws Exception
    {
        Scanner readLevel = new Scanner(new File(ASSET_PATH+"level\\"+levelName+".lvl"));

        while(readLevel.hasNext())
        {
            String in = readLevel.nextLine();
            String[] bufferedIn = in.split(":");

            switch(bufferedIn[0])
            {
                case "nextLevel": this.nextLevel = new Level(bufferedIn[1]);
                    break;
                case "background": this.background = bufferedIn[1];
                    break;
                case "music": this.music = bufferedIn[1];
                    break;
                case "block": this.levelBlocks.add(new Block(bufferedIn));
                    break;
                case "player": this.playerCoordinates = new Pair<>(Float.parseFloat(bufferedIn[1]), Float.parseFloat(bufferedIn[2]));
                    break;
                case "character":
                    switch(bufferedIn[1])
                    {
                        case "human": this.levelEntities.add(new Human());
                            break;
                        case "monster": this.levelEntities.add(new Monster());
                            break;
                        case "boss": this.levelEntities.add(new Boss());
                            break;
                    }
                    break;
            }
        }
    }

    public Pair<Float, Float> getPlayerCoordinates()
    {
        return this.playerCoordinates;
    }

    public ArrayList<Pair<String, Pair<Float, Float>>> getLevelEntities()
    {
        ArrayList<Pair<String, Pair<Float, Float>>> returnLevelEntities = new ArrayList<Pair<String, Pair<Float,Float>>>();

        for(Block b:this.levelBlocks)
            returnLevelEntities.add(new Pair<>(b.getBLOCK_URI(), b.getBlockCoordinates()));

        //for(Character c:this.levelEntities)
        //    returnLevelEntities.add(new Pair<>(c.getURI(), c.getCoordinates()));

        return returnLevelEntities;
    }

    public ArrayList<Rectangle> getCollisionBoxes()
    {
        ArrayList<Rectangle> levelCollisionBoxes = new ArrayList<>();
        for(Block b:this.levelBlocks)
            levelCollisionBoxes.addAll(b.getCollisionBoxes());

        return levelCollisionBoxes;
    }
}