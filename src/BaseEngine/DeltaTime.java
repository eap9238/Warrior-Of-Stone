package BaseEngine;
//FILE::GameTime.java
//AUTHOR::Kevin.P.Barnett
//DATE::May.01.2017

public class DeltaTime
{
    private static Long currentTime;
    private static Long deltaTime;

    public static void setDeltaTime(Long newTime)
    {
        if(deltaTime != null)
            deltaTime = newTime-currentTime;
        else
            deltaTime = newTime;

        currentTime = newTime;
    }

    public static Long getCurrentTime()
    {
        return currentTime;
    }

    public static Long getDeltaTime()
    {
        return deltaTime / 10000000;
    }
}