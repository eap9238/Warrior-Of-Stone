//FILE::Block.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;

public class Block
{
    private String BLOCK_URI;

    private Pair<Float, Float> blockCoordinates;
    private Rectangle collisionBox;
    private String type;

    public Block(String[] blockStringArray)
    {
        this.BLOCK_URI = blockStringArray[1];
        this.blockCoordinates = new Pair<>(Float.parseFloat(blockStringArray[2]), Float.parseFloat(blockStringArray[3]));
        this.type = blockStringArray[4];
        this.collisionBox = new Rectangle(this.blockCoordinates.getKey(), this.blockCoordinates.getValue(), 28, 28);
    }

    public Rectangle getCollisionBox()
    {
        return this.collisionBox;
    }

    public String getBLOCK_URI()
    {
        return BLOCK_URI;
    }

    public Pair<Float, Float> getBlockCoordinates()
    {
        return this.blockCoordinates;
    }
}