//FILE::Block.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017

import javafx.util.Pair;

public class Block
{
    private String BLOCK_URI;

    private Pair<Float, Float> blockCoordinates;
    private String type;

    public Block(String[] blockStringArray)
    {
        this.BLOCK_URI = blockStringArray[1];
        this.blockCoordinates = new Pair<>(Float.parseFloat(blockStringArray[2]), Float.parseFloat(blockStringArray[3]));
        this.type = blockStringArray[4];
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