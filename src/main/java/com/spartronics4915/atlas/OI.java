package com.spartronics4915.atlas;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
    private Robot mRobot;

    public OI(Robot robot)
    {
        mRobot = robot;

        initAutoOI();
        initDrivetrainOI();

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

            Logger.notice("=================================================");
            Logger.notice("Initialized in station " + SmartDashboard.getString("AllianceStation", "Blue"));
            Logger.notice(Instant.now().toString());
            Logger.notice("Built " + buildStr);
            Logger.notice("=================================================");

        }
        catch (IOException e)
        {
            SmartDashboard.putString("Build", "version not found!");
            Logger.error("Build version not found!");
            Logger.logThrowableCrash(e);
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
}
