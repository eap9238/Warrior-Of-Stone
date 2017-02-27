//FILE::Player.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;

public class Player extends Character
{
    private final String SPRITE_URI = "mainCharacterKun.png";

    private ArrayList<Rectangle> collisionBoxes;
    private int score;
    private float playerPositionX, playerPositionY, velocityX, velocityY;
    private boolean left, falling;

    Player()
    {
        this.collisionBoxes = new ArrayList<>();
        this.health = PLAYER_MAX_HEALTH;
        this.lives = 3;
        this.score = 0;
        this.playerPositionX = 0;
        this.playerPositionY = 0;

        this.velocityX = 0;
        this.velocityY = 0;
        this.falling = false;
    }

    public int getHealth(){return this.health;}
    public void setHealth(int health){this.health = health;}

    public void setInitialPosition(Pair<Float, Float> playerCoordinates)
    {
        this.playerPositionX = playerCoordinates.getKey();
        this.playerPositionY = playerCoordinates.getValue();
    }

    public void moveLeft()
    {
        //this.playerPositionX -= 5;
        this.velocityX -= 1;
        this.left = true;

        //now alters the player's current directions and velocity
    }

    public void moveRight()
    {
        //this.playerPositionX += 5;
        this.velocityX += 1;
        this.left = false;

        //now alters the player's current directions and velocity
    }

    public void jump()
    {
        if (!this.falling)
        {
            this.velocityY = 15;
            //starts jump

            this.playerPositionY -=1;
            //avoid collision error

            this.falling = true;
            //begin falling
        }
    }

    public void moveX()
    {
        //DEBUG ONLY ->
        if(this.playerPositionY >= 418)
        {
            this.falling = false;
            this.playerPositionY = 418;
        }
        //DEBUG ONLY <-

        if (this.falling)
        {
            this.velocityY = this.velocityY - (float)0.8;
            //use gravity
        }
        else
        {
            this.velocityY = 0;
            //stop falling if not falling
        }

        this.playerPositionY -= this.velocityY;
        //fall

        this.velocityX *= .85;
        //friction (slows when not pressed, but not faster than speeds up

        if (this.velocityX > 5)
        {
            this.velocityX = 5;
        }
        else if (this.velocityX < -5)
        {
            this.velocityX = -5;
        }
        else if (this.velocityX > 0 && this.velocityX <= .5)
        {
            this.velocityX = 0;
        }
        else if (this.velocityX < 0 && this.velocityX >= -.5)
        {
            this.velocityX = 0;
        }
        //sets max and min speeds

        this.playerPositionX += velocityX;

        //alters position, called each frame
    }

    //use left to swap current direction of player

    public Pair<Float, Float> getPosition(){return new Pair<>(this.playerPositionX, this.playerPositionY);}

    public String toString()
    {
        return String.format("URI: %s\n" +
                             "Health: %d\n" +
                             "Lives: %d" +
                             "Score: %d\n" +
                             "Set Position: (%f, %f)\n", this.SPRITE_URI, this.health, this.lives, this.score,
                                                          this.playerPositionX, this.playerPositionY);
    }

    public String getSPRITE_URI(){return SPRITE_URI;}
}