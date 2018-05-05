package com.spartronics4915.atlas;

import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Logger:
//  a simple class to route all logging through.
//  future enhancement:
//      * support logging to file
//      * runtime inference of logging level (practice vs competition)
//  usage:
//      singleton: Logger.getInstance().debug("here's my message");
//      via Robot:  robot.m_logger.debug("here's a debugging msg");
//      via Subsystem: this.m_logger.debug("here's a message");
//  loglevel conventions:
//      debug: used for debugging software... not available during
//          competition.
//      info:  used for less interesting but non-debugging message.
//      notice: used for msgs you always want to see in log
//          - subsystem initializations
//          - important state transitions (ie entering auto, teleop)
//      warning: used to convey non-fatal but abnormal state
//      error: used to convey strong abnormal conditions
//      exception: used in a catch block to report exceptions.
//
public class Logger
{

    // The code formatter attempts to reorder the levels, that doesn't help...

    //@formatter:off
    public enum Level
    {
        DEBUG,
        INFO,
        NOTICE,
        WARNING,
        ERROR
    }
    //@formatter:on

    private static List<Logger> sAllLoggers = new ArrayList<>();
    private static Logger sSharedLogger;
    private static DateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
    // NB: changing the date-format may negatively impact downstream/display
    //     code that may depend upon this format.

    private Level mLogLevel; // per-instance
    private String mNamespace;

    public static List<Logger> getAllLoggers()
    {
        return sAllLoggers;
    }

    public static Logger getSharedInstance()
    {
        if (sSharedLogger == null)
        {
            sSharedLogger = new Logger("<shared>", Level.DEBUG);
        }
        return sSharedLogger;
    }

    public Logger(String nm, Level lev)
    {
        mNamespace = nm;
        mLogLevel = lev;
        sAllLoggers.add(this);
    }

    public void debug(String msg)
    {
        if (reportLevel(Level.DEBUG))
        {
            logMsg("DEBUG  ", msg);
        }
    }

    public void info(String msg)
    {
        if (reportLevel(Level.INFO))
        {
            logMsg("INFO   ", msg);
        }
    }

    public void notice(String msg)
    {
        if (reportLevel(Level.NOTICE))
        {
            logMsg("NOTICE ", msg);
        }
    }

    public void warning(String msg)
    {
        if (reportLevel(Level.WARNING))
        {
            logMsg("WARNING", msg);
        }
    }

    public void error(String msg)
    {
        logMsg("ERROR  ", msg);
    }

    public void exception(Exception e, boolean skipStackTrace)
    {
        logMsg("EXCEPT ", e.getMessage());
        if (!skipStackTrace)
        {
            e.printStackTrace();
        }
    }

    private void logMsg(String lvl, String msg)
    {
        Date now = new Date();
        String nowstr = sDateFormat.format(now);
        System.out.println(nowstr + " " + lvl + " " + mNamespace + ": " + msg + "\r"); // We need \r because National Instruments likes to compress frequent messages without the carriage return
        // NB: changing the date-format may negatively impact downstream/display
        //     code that may depend upon this format.
    }

    private boolean reportLevel(Level lev)
    {
        return lev.ordinal() >= mLogLevel.ordinal();
    }

    public String getNamespace()
    {
        return mNamespace;
    }

    public Level getLogLevel()
    {
        return mLogLevel;
    }

    public void setLogLevel(Level level)
    {
        mLogLevel = level;
    }
}
