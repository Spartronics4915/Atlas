package com.spartronics4915.atlas;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.spartronics4915.atlas.Logger.Level;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{

    private Logger mLogger;
    private Robot mRobot;

    public OI(Robot robot)
    {
        mRobot = robot;
        mLogger = new Logger("OI", Logger.Level.DEBUG);

        initAutoOI();
        initDrivetrainOI();

        // Init loggers last, as this uses special values generated when other loggers are created.
        initLoggers();

        // TODO: Reimplement in Gradle
        // Version string and related information
        try (InputStream manifest = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"))
        {
            // build a version string
            Attributes attributes = new Manifest(manifest).getMainAttributes();
            String buildStr = "by: " + attributes.getValue("Built-By") +
                    "  on: " + attributes.getValue("Built-At") +
                    "  (" + attributes.getValue("Code-Version") + ")";
            SmartDashboard.putString("Build", buildStr);

            mLogger.notice("=================================================");
            mLogger.notice("Initialized in station " + SmartDashboard.getString("AllianceStation", "Blue"));
            mLogger.notice(Instant.now().toString());
            mLogger.notice("Built " + buildStr);
            mLogger.notice("=================================================");

        }
        catch (IOException e)
        {
            SmartDashboard.putString("Build", "version not found!");
            mLogger.error("Build version not found!");
            mLogger.exception(e, true /* no stack trace needed */);
        }
    }

    private void initAutoOI()
    {
        // Add autonomous options to SmartDashboard here
        // FIXME
    }

    // getAutoCommand should happen during autonomous init (so long after robotInit).
    // Timing is important here.
    public Command getAutoCommand()
    {
        return new Command()
        {

            @Override
            protected boolean isFinished()
            {
                return false;
            }
        }; // FIXME
    }

    private void initDrivetrainOI()
    {
        // Initalize the drivetrain
    }

    private void initLoggers()
    {

        /*
         * Get the shared instance, then throw away the result.
         * This ensures that the shared logger is created, even if never used
         * elsewhere.
         */
        Logger.getSharedInstance();

        for (Logger logger : Logger.getAllLoggers())
        {
            String key = "Loggers/" + logger.getNamespace();
            Level desired;

            if (!SmartDashboard.containsKey(key))
            {
                // First time this logger has been sent to SmartDashboard
                SmartDashboard.putString(key, logger.getLogLevel().name());
                desired = Level.DEBUG;
            }
            else
            {
                String choice = SmartDashboard.getString(key, "DEBUG");
                Level parsed = Level.valueOf(choice);
                if (parsed == null)
                {
                    mLogger.error("The choice '" + choice + "' for logger " + logger.getNamespace() + " isn't a valid value.");
                    desired = Level.DEBUG;
                }
                else
                {
                    desired = parsed;
                }
            }
            logger.setLogLevel(desired);
        }
    }
}
