package BaseEngine;
//FILE::LoggingEngine.java
//AUTHOR::Kevin.P.Barnett
//DATE::Aug.20.2017

import java.io.*;
import java.time.LocalDateTime;

public class LoggingEngine
{
    private static final String os = System.getProperty("os.name");
    private static final boolean APPEND = true;
    private static String DEFAULT_LOGFILE;
    private static LoggingEngine thisInstance;
    private static BufferedWriter LogFile;
    private static LocalDateTime dateTime;

    public static LoggingEngine getInstance()
    {
        if(thisInstance == null)
            thisInstance = new LoggingEngine();
        return thisInstance;
    }

    private LoggingEngine()
    {
        if(os.contains("Linux"))
            DEFAULT_LOGFILE = "/usr/bin/wos/logs/log.txt";
        else if(os.contains("Windows"))
            DEFAULT_LOGFILE = System.getProperty("user.home")+"\\Program Files\\WOS\\";
        else if(os.contains(""))
            DEFAULT_LOGFILE = "";
        else
            throw new RuntimeException("OS NOT SUPPORTED");

        System.out.println(DEFAULT_LOGFILE);

        try
        {
            openLogFile();
        }
        catch(IOException e)
        {
            throw new RuntimeException("LOGGING FILE OUTPUT PATH NOT FOUND");
        }
    }

    private void openLogFile() throws IOException
    {
        LogFile = new BufferedWriter(new FileWriter(new File(DEFAULT_LOGFILE), APPEND));
    }

    private String getFormattedDateTime()
    {
        LocalDateTime currentTime = LocalDateTime.now();
        return String.format("%d/%d/%d %d:%d:%d", currentTime.getMonth().getValue(), currentTime.getDayOfMonth(), currentTime.getYear(), currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
    }

    private String modifyMessage(String message, String... args)
    {
        for(String arg: args)
            message = message.replaceFirst("\\{\\}", arg);
        return message;
    }

    public void debug(String message)
    {
        try{LogFile.write(String.format("DEBUG[%s]: %s\n", getFormattedDateTime(), message)); LogFile.flush();}
        catch(IOException e){System.err.println("Failed to Write to Log");}
    }
    public void debug(String message,String... args)
    {
        message = modifyMessage(message, args);
        try{LogFile.write(String.format("DEBUG[%s]: %s\n", getFormattedDateTime(), message)); LogFile.flush();}
        catch(IOException e){System.err.println("Failed to Write to Log");}
    }

    public void info(String message)
    {
        try{LogFile.write(String.format("DEBUG[%s]: %s\n", getFormattedDateTime(), message)); LogFile.flush();}
        catch(IOException e){System.err.println("Failed to Write to Log");}
    }
    public void info(String message, String... args)
    {
        message = modifyMessage(message, args);
        try{LogFile.write(String.format("DEBUG[%s]: %s\n", getFormattedDateTime(), message)); LogFile.flush();}
        catch(IOException e){System.err.println("Failed to Write to Log");}
    }

    public void error(String message)
    {
        try{LogFile.write(String.format("DEBUG[%s]: %s\n", getFormattedDateTime(), message)); LogFile.flush();}
        catch(IOException e){System.err.println("Failed to Write to Log");}
    }
    public void error(String message, String... args)
    {
        message = modifyMessage(message, args);
        try{LogFile.write(String.format("DEBUG[%s]: %s\n", getFormattedDateTime(), message)); LogFile.flush();}
        catch(IOException e){System.err.println("Failed to Write to Log");}
    }
}