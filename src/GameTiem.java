//FILE::GameTiem.java
//AUTHOR::Kevin.P.Barnett
//DATE::May.01.2017

public class GameTiem
{
    private static Long currentTime;

    public void setCurrentTime(Long newTime)
    {
        currentTime = newTime;
    }

    public Long getCurrentTime()
    {
        return currentTime;
    }
}