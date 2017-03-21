//FILE::Player.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;

public class Player extends Character
{
    private final String SPRITE_URI = "mainCharacterKun.png";

    private int score;
    private float playerPositionX, playerPositionY, velocityX, velocityY;
    private boolean left, falling;

    Player()
    {
        super();
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

        this.collisionBoxes[0].setHeight(1);
        this.collisionBoxes[0].setWidth(28);

        this.collisionBoxes[1].setHeight(1);
        this.collisionBoxes[1].setWidth(28);

        this.collisionBoxes[2].setHeight(34);
        this.collisionBoxes[2].setWidth(1);

        this.collisionBoxes[3].setHeight(34);
        this.collisionBoxes[3].setWidth(1);

        setCollisionBoxes();
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

    public void setCollisionBoxes()
    {
        this.collisionBoxes[0].setX(this.playerPositionX);
        this.collisionBoxes[0].setY(this.playerPositionY);

        this.collisionBoxes[1].setX(this.playerPositionX);
        this.collisionBoxes[1].setY(this.playerPositionY + 33);

        this.collisionBoxes[2].setX(this.playerPositionX);
        this.collisionBoxes[2].setY(this.playerPositionY);

        this.collisionBoxes[3].setX(this.playerPositionX + 27);
        this.collisionBoxes[3].setY(this.playerPositionY);
    }

    public void moveX()
    {
        //vertical

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

        //test for vertical collisions

        // if(this.collisionBoxes[0].intersects(this.collisionBoxes[1].getLayoutBounds()))

        /* drags chara down
        for (Rectangle collidingbox: getCollisionBoxes()) //collision box set
        {
            if (this.collisionBoxes[0].intersects(collidingbox.getLayoutBounds()))
            {
                this.velocityY = 0;
                this.playerPositionY = (float)(collidingbox.getY() + collidingbox.getHeight());
            }
        else if (this.collisionBoxes[1].intersects(collidingbox.getLayoutBounds()))
            {
                this.velocityY = 0;
                this.playerPositionY = (float)(collidingbox.getY() - 34); // second value is the player height
                this.falling = false;
            }
        }
        */

        this.playerPositionY -= this.velocityY;
        //fall

        //horizontal

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

        /* drags chara left
        for (Rectangle collidingbox: getCollisionBoxes()) //collision box set
        {
            if (this.collisionBoxes[2].intersects(collidingbox.getLayoutBounds()))
            {
                this.velocityX = 0;
                this.playerPositionX = (float)(collidingbox.getX() + collidingbox.getWidth());
            }
            else if (this.collisionBoxes[3].intersects(collidingbox.getLayoutBounds()))
            {
                this.velocityX = 0;
                this.playerPositionX = (float)(collidingbox.getX() - 28); //second number is character width
            }
        }
        */

        this.playerPositionX += velocityX;

        //alters position, called each frame

        /* //DEBUG ONLY ->
        if(this.playerPositionY >= 420)
        {
            this.velocityY = 0;
            this.falling = false;
            this.playerPositionY = 420;
        }
        //DEBUG ONLY <- */

        //NOT DEBUG->
        if(this.playerPositionX < 0)
        {
            this.velocityX = 0;
            this.playerPositionX = 0;
        }
        //NOT DEBUG <-

        setCollisionBoxes();

        //fixes collision
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