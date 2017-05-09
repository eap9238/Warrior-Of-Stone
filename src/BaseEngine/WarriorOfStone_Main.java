package BaseEngine;//FILE::BaseEngine.WarriorOfStone_Main.java
//AUTHOR::Kevin.P.Barnett, Eleanor.A.Post
//DATE::Feb.03.2017

import Entities.*;
import World.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.time.OffsetTime.now;

public class WarriorOfStone_Main
{
    private HashMap<String, Boolean> keyMap;
    private Graphics gfx;
    private Player player;
    private Level currentLevel;

    private void initialize(String[] args)
    {
        this.player = new Player();

        try{this.loadLevels();}
        catch(Exception e){e.printStackTrace();}

        Graphics.load(args, this);
    }

    private void registerImages() throws FileNotFoundException
    {
        //Load Assests Specified in the Images Folder
        this.gfx.loadFileSpecifiedImages();

        /*
        this.gfx.registerEntityImage(this.player.getSPRITE_URI());
        for(Pair<String, Pair<Float, Float>> entity:this.currentLevel.getLevelEntities())
            this.gfx.registerEntityImage(entity.getKey());
        */
    }

    private void loadLevels() throws Exception
    {
        this.currentLevel = new Level();
        this.player.setInitialPosition(this.currentLevel.getPlayerCoordinates());
    }

    private void movePlayer(ArrayList<Rectangle> levelCollisionBoxes)
    {
        if(this.keyMap.get("Left") || this.keyMap.get("A"))
        {
            this.player.moveLeft();
        }
        else if(this.keyMap.get("Right") || this.keyMap.get("D"))
        {
            this.player.moveRight();
        }

        this.player.moveX(levelCollisionBoxes);

        if (this.keyMap.get("Up") || this.keyMap.get("W") || this.keyMap.get("Space"))
        {
            this.player.jump();
        }
    }

    public void GAME_LOOP(Graphics gfx, HashMap<String, Boolean> keyMap)
    {
        this.gfx = gfx;
        this.keyMap = keyMap;

        try{this.registerImages();}
        catch(FileNotFoundException e)
        {
            System.out.println("Failed to Load Images");
            System.exit(-1);
        }

        ArrayList<Pair<String, Pair<Float, Float>>> entitesToBeDrawn = new ArrayList<>();

        AnimationTimer mainGame = new AnimationTimer()
        {
            public void handle(long time)
            {
                DeltaTime.setDeltaTime(time);

                entitesToBeDrawn.clear();

                ////////////////////////////////////////////////////////
                //      Insert Main Game Information Stoof Below      //
                ////////////////////////////////////////////////////////

                Pair<Pair<String, Boolean>, Pair<Float, Float>> playerPosition = new Pair<>(new Pair<>(player.getSPRITE_URI(), player.getFacingDirection()), player.getPosition());

                movePlayer(currentLevel.getCollisionBoxes());

                ////////////////////////////////////////////////////////
                //   Though Keep It Above Here                        //
                //   That Is the Final Write to Screen Thing Below    //
                ////////////////////////////////////////////////////////


                ///////////////////////////////////////////////////////
                //           Check for opening a menu                //
                ///////////////////////////////////////////////////////

                entitesToBeDrawn.addAll(currentLevel.getLevelEntities());
                ArrayList<Rectangle> collisionBoxes = new ArrayList(player.getCollisionBoxes());

                for(Rectangle colBox:currentLevel.getCollisionBoxes())
                    collisionBoxes.add(colBox);

                gfx.update(playerPosition, entitesToBeDrawn, collisionBoxes, currentLevel.getWidth(), currentLevel.getHeight());

                if(keyMap.get("Esc"))
                    System.exit(0);
            }
        };

        mainGame.start();
    }

    public static void main(String[] args)
    {
        WarriorOfStone_Main MainGame = new WarriorOfStone_Main();
        MainGame.initialize(args);
    }

    public String toString()
    {
        return "Yes";
    }
}