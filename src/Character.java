//FILE::Monster.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Character
{
    public final int PLAYER_MAX_HEALTH = 100;

    private ArrayList<Rectangle> collisionBoxes;
    public int health, lives;


    public abstract String toString();
}