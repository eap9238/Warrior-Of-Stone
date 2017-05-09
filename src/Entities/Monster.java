package Entities;//FILE::Entities.Monster.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.21.2017

public class Monster extends Character
{
    public Monster(String url, float positionX, float positionY)
    {
        super();

        this.SPRITE_URI = url;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String toString()
    {
        return "";
    }
}