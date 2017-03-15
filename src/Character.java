//FILE::Monster.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Character
{
    public final int PLAYER_MAX_HEALTH = 100;

    private Rectangle[] collisionBoxes;
    public int health, lives;

    public Character()
    {
        this.collisionBoxes = new Rectangle[4];

        for(int i=0; i<4; ++i)
            this.collisionBoxes[i] = new Rectangle(0, 0, 0, 0);
    }

    public ArrayList<Rectangle> getCollisionBoxes()
    {
        ArrayList<Rectangle> outBox = new ArrayList<>();

        for(int i=0; i<4; ++i)
            outBox.add(this.collisionBoxes[i]);

        return outBox;
    }

    public abstract String toString();
}