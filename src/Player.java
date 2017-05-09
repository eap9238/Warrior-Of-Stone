//FILE::Player.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;

public class Player extends Character
{
    private int score;

    Player()
    {
        super();
        this.SPRITE_URI = "mainCharacterKun.png";

        this.health = PLAYER_MAX_HEALTH;
        this.lives = 3;
        this.score = 0;
        this.positionX = 0;
        this.positionY = 0;

        this.velocityX = 0;
        this.velocityY = 0;
        this.isFalling = false;
    }

    public int getHealth(){return this.health;}
    public void setHealth(int health){this.health = health;}

    public void setInitialPosition(Pair<Float, Float> playerCoordinates)
    {
        this.positionX = playerCoordinates.getKey();
        this.positionY = playerCoordinates.getValue();

        //Up
        this.collisionBoxes[0].setHeight(1);
        this.collisionBoxes[0].setWidth(3);

        //Down
        this.collisionBoxes[1].setHeight(1);
        this.collisionBoxes[1].setWidth(3);


        //Left
        this.collisionBoxes[2].setHeight(3);
        this.collisionBoxes[2].setWidth(1);

        //Right
        this.collisionBoxes[3].setHeight(3);
        this.collisionBoxes[3].setWidth(1);

        this.collisionBoxes[4].setHeight(1);
        this.collisionBoxes[4].setWidth(28);

        setCollisionBoxes();
    }

    public void moveLeft()
    {
        //this.positionX -= 5;
        this.velocityX -= 1;
        this.facingLeft = true;

        //now alters the player's current directions and velocity
    }

    public void moveRight()
    {
        //this.positionX += 5;
        this.velocityX += 1;
        this.facingLeft = false;

        //now alters the player's current directions and velocity
    }

    public void jump()
    {
        if (!this.isFalling)
        {
            this.velocityY = 15;
            //starts jump

            this.positionY -=1;
            //avoid collision error

            this.isFalling = true;
            //begin isFalling
        }
    }

    public void setCollisionBoxes()
    {
        //Up
        this.collisionBoxes[0].setX(this.positionX+12);
        this.collisionBoxes[0].setY(this.positionY);

        //Down
        this.collisionBoxes[1].setX(this.positionX+12);
        this.collisionBoxes[1].setY(this.positionY + 33);

        //Left
        this.collisionBoxes[2].setX(this.positionX);
        this.collisionBoxes[2].setY(this.positionY+15);

        //Right
        this.collisionBoxes[3].setX(this.positionX + 27);
        this.collisionBoxes[3].setY(this.positionY+15);

        //isFalling
        this.collisionBoxes[4].setX(this.positionX);
        this.collisionBoxes[4].setY(this.positionY + 34);
    }

    public void moveX(ArrayList<Rectangle> levelCollisionBoxes)
    {
        //vertical

        if (this.isFalling)
        {
            this.velocityY = this.velocityY - (float)0.8;
            //use gravity
        }
        else
        {
            this.velocityY = 0;
            //stop isFalling if not isFalling
        }

        //test for vertical collisions

        // if(this.collisionBoxes[0].intersects(this.collisionBoxes[1].getLayoutBounds()))

        if (this.velocityY > 100)
        {
            this.velocityY = 100;
        }
        else if (this.velocityY < -10)
        {
            this.velocityY = -10;
        }
        //sets max and min Y speeds

        this.positionY -= this.velocityY;
        //fall

        //temp
        this.isFalling = true;

        //* drags chara down
        for (Rectangle collidingbox: levelCollisionBoxes) //collision box set
        {
            if (this.collisionBoxes[0].intersects(collidingbox.getLayoutBounds()))
            {
                this.velocityY = 0;
                this.positionY = (float)(collidingbox.getY() + collidingbox.getHeight() + 1);
            }
            else if (this.collisionBoxes[1].intersects(collidingbox.getLayoutBounds()))
            {
                this.velocityY = 0;
                this.positionY = (float)(collidingbox.getY() - 35); // second value is the player height
                this.isFalling = false;
            }
            else if (this.collisionBoxes[4].intersects(collidingbox.getLayoutBounds()))
            {
                this.isFalling = false;
                //baaaaaaaaaaaaaaaad idea
            }
             //outdated code if new idea works

            //new idea
            /*
            if (this.positionY + 34 > (float)collidingbox.getY())
            {
                this.velocityY = 0;
                this.positionY = (float)collidingbox.getY() - 34;
                this.isFalling = false;
            }
            else if (this.positionY < (float)(collidingbox.getY() - collidingbox.getHeight()))
            {
                this.velocityY = 0;
                this.positionY = (float)collidingbox.getY() - 34;
            }
            */
        }

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
        //sets max and min X speeds

        this.positionX += velocityX;

        ///* drags chara left
        for (Rectangle collidingbox: levelCollisionBoxes) //collision box set
        {
            if (this.collisionBoxes[2].intersects(collidingbox.getLayoutBounds()))
            {
                this.velocityX = 0;
                this.positionX = (float)(collidingbox.getX() + collidingbox.getWidth() + 1);
            }
            else if (this.collisionBoxes[3].intersects(collidingbox.getLayoutBounds()))
            {
                this.velocityX = 0;
                this.positionX = (float) (collidingbox.getX() - 29); //second number is character width
            }
        }
        //*/

        //alters position, called each frame

        /* //DEBUG ONLY ->
        if(this.positionY >= 420)
        {
            this.velocityY = 0;
            this.isFalling = false;
            this.positionY = 420;
        }
        //DEBUG ONLY <- */

        //NOT DEBUG->
        if(this.positionX < 0)
        {
            this.velocityX = 0;
            this.positionX = 0;
        }
        //NOT DEBUG <-

        setCollisionBoxes();

        //fixes collision
    }

    //use left to swap current direction of player
}