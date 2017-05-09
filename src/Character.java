//FILE::Monster.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Character
{
    public final int PLAYER_MAX_HEALTH = 100;

    protected String SPRITE_URI;
    protected float positionX, positionY, velocityX, velocityY;
    protected Rectangle[] collisionBoxes; //0:Up 1:Down 2:Left 3:Right
    protected int health, lives;
    protected boolean facingLeft, isFalling;

    public Character()
    {
        this.collisionBoxes = new Rectangle[5];

        for(int i=0; i<5; ++i)
            this.collisionBoxes[i] = new Rectangle(0, 0, 0, 0);
    }

    public ArrayList<Rectangle> getCollisionBoxes()
    {
        ArrayList<Rectangle> outBox = new ArrayList<>();

        for(int i=0; i<4; ++i)
            outBox.add(this.collisionBoxes[i]);

        return outBox;
    }

    public Pair<Float, Float> getPosition(){return new Pair<>(this.positionX, this.positionY);}
    public Boolean getFacingDirection()
    {
        return this.facingLeft;
    }

    public String toString()
    {
        return String.format("URI: %s\n" +
                        "Health: %d\n" +
                        "Lives: %d" +
                        "Set Position: (%f, %f)\n", this.SPRITE_URI, this.health, this.lives,
                this.positionX, this.positionY);
    }

    public String getSPRITE_URI(){return SPRITE_URI;}
}