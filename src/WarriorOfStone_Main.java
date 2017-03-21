//FILE::WarriorOfStone_Main.java
//AUTHOR::Kevin.P.Barnett, Eleanor.A.Post
//DATE::Feb.03.2017

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

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

    private void registerImages()
    {
        this.gfx.registerEntityImage(this.player.getSPRITE_URI());
        for(Pair<String, Pair<Float, Float>> entity:this.currentLevel.getLevelEntities())
            this.gfx.registerEntityImage(entity.getKey());
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

        this.registerImages();

        ArrayList<Pair<String, Pair<Float, Float>>> entitesToBeDrawn = new ArrayList<>();

        new AnimationTimer()
        {
            public void handle(long time)
            {
                entitesToBeDrawn.clear();

                ////////////////////////////////////////////////////////
                //      Insert Main Game Information Stoof Below      //
                ////////////////////////////////////////////////////////
                entitesToBeDrawn.add(new Pair<>(player.getSPRITE_URI(), player.getPosition()));

                movePlayer(currentLevel.getCollisionBoxes());

                ////////////////////////////////////////////////////////
                //   Though Keep It Above Here                        //
                //   That Is the Final Write to Screen Thing Below    //
                ////////////////////////////////////////////////////////


                entitesToBeDrawn.addAll(currentLevel.getLevelEntities());
                ArrayList<Rectangle> collisionBoxes = new ArrayList(player.getCollisionBoxes());

                for(Rectangle colBox:currentLevel.getCollisionBoxes())
                    collisionBoxes.add(colBox);

                gfx.update(entitesToBeDrawn, collisionBoxes);

                if(keyMap.get("Esc"))
                {
                    this.stop();
                    System.exit(0);
                }
            }
        }.start();
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