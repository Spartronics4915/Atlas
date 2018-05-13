package com.spartronics4915.atlas;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.spartronics4915.atlas.commands.*;
import com.spartronics4915.atlas.subsystems.LED;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
    private Robot mRobot;
    private LED mLED;

    private static final int kDriveJoystickPort = 1;
    private static final int kArcadeStickPort = 2;

    // launcher controls TODO: Fix button
    private static final int kLaunchDriveStickButton = 2;
    private static final int kLaunchArcadeStickButton = 2;
    private static final int kWindLauncherDriveStickButton = 3;
    private static final int kWindLauncherArcadeStickButton = 3;

    // harvester controls TODO: Fix button
    private static final int kHarvesterExtendDriveStickButton = 4;
    private static final int kHarvesterExtendArcadeStickButton = 4;
    private static final int kHarvesterRetractDriveStickButton = 5;
    private static final int kHarvesterRetractArcadeStickButton = 5;
    private static final int kHarvesterWheelsForwardDriveStickButton = 6;
    private static final int kHarvest6rWheelsReverseDriveStickButton = 7;

    // add harvester buttons -- extend & retract
    // add harvester wheel buttons -- trigger style forward & reverse
    
    public OI(Robot robot)
    {
        mRobot = robot;

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

        // initialize joysticks
        Joystick driveStick = new Joystick(kDriveJoystickPort);
        Joystick arcadeStick = new Joystick(kArcadeStickPort);

        // get instance to LED system
        mLED = LED.getInstance();

        initDrivetrainOI();

        // initialize launchers buttons
		JoystickButton launchCommandGroupButtonOnDriveStick = new JoystickButton(driveStick, kLaunchDriveStickButton);
        launchCommandGroupButtonOnDriveStick.whenPressed(new ActivateLauncherCommandGroup(mLED));
        JoystickButton launchCommandGroupButtonOnArcadeStick = new JoystickButton(arcadeStick, kLaunchArcadeStickButton);
		launchCommandGroupButtonOnArcadeStick.whenPressed(new ActivateLauncherCommandGroup(mLED));

        JoystickButton windCommandGroupButtonOnDriveStick = new JoystickButton(driveStick, kWindLauncherDriveStickButton);
        windCommandGroupButtonOnDriveStick.whenPressed(new WindLauncherCommandGroup(mLED));
        JoystickButton windCommandGroupButtonOnArcadeStick = new JoystickButton(arcadeStick, kLaunchArcadeStickButton);
        windCommandGroupButtonOnArcadeStick.whenPressed(new WindLauncherCommandGroup(mLED));
    }

    private void initDrivetrainOI()
    {
        // Initalize the drivetrain
    }
}
