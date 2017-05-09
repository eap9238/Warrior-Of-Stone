package BaseEngine;//FILE::BaseEngine.Menu.java
//AUTHOR::Kevin.P.Barnett
//DATE::Feb.03.2017
import java.util.ArrayList;
import java.util.List;

public class Menu
{
    private ArrayList<Button> menu;

    Menu(String menuDefinitionUrl)
    {
    }

    public ArrayList<Button> getMenu()
    {
        return this.menu;
    }

    private class Button
    {
        private float width, height;
        private String functionName, associatedImageUrl;

        Button(float width, float height, String functionName, String imageUrl)
        {
            this.width = width;
            this.height = height;
            this.functionName = functionName;
            this.associatedImageUrl = imageUrl;
        }

        public float[] getCoordinates()
        {
            float[] coordinatePair = new float[]{this.width, this.height};
            return coordinatePair;
        }

        public String getFunctionName()
        {
            return this.functionName;
        }

        public String getAssociatedImageUrl()
        {
            return this.associatedImageUrl;
        }
    }
}