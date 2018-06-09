package com.spartronics4915.atlas;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.spartronics4915.atlas.commands.*;
import com.spartronics4915.atlas.subsystems.LED;
import com.spartronics4915.atlas.subsystems.Launcher;
import com.spartronics4915.atlas.subsystems.Harvester;

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
    private Harvester mHarvester;
    private LED mLED;

    private static final int kDriveJoystickPort = 1;
    private static final int kArcadeStickPort = 2;

    // drivetrain controls
    private static final int kQuickTurnDriveStickButton = 9;
    private static final int kDriveStraightDriveStickButton = 8;

    // launcher controls 
    private static final int kLaunchDriveStickButton = 2;
    private static final int kLaunchArcadeStickButton = 2;
    private static final int kWindLauncherDriveStickButton = 3;
    private static final int kWindLauncherArcadeStickButton = 4;

    // harvester controls 
    private static final int kHarvesterExtendDriveStickButton = 5;
    private static final int kHarvesterExtendArcadeStickButton = 1;
    private static final int kHarvesterRetractDriveStickButton = 4;
    private static final int kHarvesterRetractArcadeStickButton = 3;
    private static final int kHarvesterReleaseDriveStickButton = 10;
    private static final int kHarvesterReleaseArcadeStickButton = 6;
    private static final int kHarvesterWheelsToggleDriveStickButton = 1;
    private static final int kHarvesterWheelsToggleArcadeStickButton = 5;
    private static final int kHarvesterWheelsStopDriveStickButton = 7;
    private static final int kHarvesterWheelsStopArcadeStickButton = 7;

    // launcher test controls -- not on ArcadeStick due to the 'mode' button
    private static final int kTestLauncherWindingMotorDriveStickButton = 6;
    private static final int kTestLauncherSolenoidDriveStickButton = 11;

    public static final Joystick sDriveStick = new Joystick(kDriveJoystickPort);
    public static final Joystick sArcadeStick = new Joystick(kArcadeStickPort);

    // add harvester buttons -- extend & retract
    // add harvester wheel buttons -- trigger style forward & reverse
    
    public OI(Robot robot)
    {
        mRobot = robot;
        mHarvester = Harvester.getInstance();

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

        // initialize joystick buttons
        JoystickButton quickTurnDrivetrain = new JoystickButton(sDriveStick, kQuickTurnDriveStickButton);
        JoystickButton driveStraightDrivetrain = new JoystickButton(sDriveStick, kDriveStraightDriveStickButton);

        JoystickButton launchCommandGroupButtonOnDriveStick = new JoystickButton(sDriveStick, kLaunchDriveStickButton);
        JoystickButton launchCommandGroupButtonOnArcadeStick = new JoystickButton(sArcadeStick, kLaunchArcadeStickButton);
        JoystickButton windCommandGroupButtonOnDriveStick = new JoystickButton(sDriveStick, kWindLauncherDriveStickButton);
        JoystickButton windCommandGroupButtonOnArcadeStick = new JoystickButton(sArcadeStick, kWindLauncherArcadeStickButton);

        // for testing of the winding motor
        JoystickButton testLauncherWindingMotorButtonOnDriveStick = new JoystickButton(sDriveStick, kTestLauncherWindingMotorDriveStickButton);
        JoystickButton testLauncherSolenoidButtonOnDriveStick = new JoystickButton(sDriveStick, kTestLauncherSolenoidDriveStickButton);

        JoystickButton intakeDownButtonOnDriveStick = new JoystickButton(sDriveStick, kHarvesterExtendDriveStickButton);
        JoystickButton intakeDownButtonOnArcadeStick = new JoystickButton(sArcadeStick, kHarvesterExtendArcadeStickButton);
        JoystickButton intakeUpButtonOnDriveStick = new JoystickButton(sDriveStick, kHarvesterRetractDriveStickButton);
        JoystickButton intakeUpButtonOnArcadeStick = new JoystickButton(sArcadeStick, kHarvesterRetractArcadeStickButton);
        JoystickButton intakeReleaseButtonOnDriveStick = new JoystickButton(sDriveStick, kHarvesterReleaseDriveStickButton);
        JoystickButton intakeReleaseButtonOnArcadeStick = new JoystickButton(sArcadeStick, kHarvesterReleaseArcadeStickButton);
        JoystickButton toggleHarvesterWheelsButtonOnDriveStick = new JoystickButton(sDriveStick, kHarvesterWheelsToggleDriveStickButton);
        JoystickButton toggleHarvesterWheelsButtonOnArcadeStick = new JoystickButton(sArcadeStick, kHarvesterWheelsToggleArcadeStickButton);
        JoystickButton stopHarvesterWheelsButtonOnDriveStick = new JoystickButton(sDriveStick, kHarvesterWheelsStopDriveStickButton);
        JoystickButton stopHarvesterWheelsButtonOnArcadeStick = new JoystickButton(sArcadeStick, kHarvesterWheelsStopArcadeStickButton);

        // get instance to LED system
        mLED = LED.getInstance();

        initDrivetrainOI();

        // initialize drivetrain buttons
        quickTurnDrivetrain.whileHeld(new QuickTurnDrivetrain());
        driveStraightDrivetrain.whenPressed(new DriveStraightDrivetrain());

        // initialize launcher buttons
        launchCommandGroupButtonOnDriveStick.whenPressed(new ActivateLauncherCommandGroup(mLED));
		launchCommandGroupButtonOnArcadeStick.whenPressed(new ActivateLauncherCommandGroup(mLED));
        windCommandGroupButtonOnDriveStick.whenPressed(new WindLauncherCommandGroup(mLED));
        windCommandGroupButtonOnArcadeStick.whenPressed(new WindLauncherCommandGroup(mLED));

        // test buttons for launcher
        //testLauncherWindingMotor.whenPressed(new TestWindLauncherSpeed());
        testLauncherWindingMotorButtonOnDriveStick.whileHeld(new TestWindLauncherSpeed());
        testLauncherSolenoidButtonOnDriveStick.whenPressed(new TestLauncherSolenoid());

        // initialize harvester buttons
        intakeDownButtonOnDriveStick.whenPressed(new IntakeDown());
        intakeDownButtonOnArcadeStick.whenPressed(new IntakeDown());
        intakeUpButtonOnDriveStick.whenPressed(new IntakeUp());
        intakeUpButtonOnArcadeStick.whenPressed(new IntakeUp());
        intakeReleaseButtonOnDriveStick.whenPressed(new IntakeRelease());
        intakeReleaseButtonOnArcadeStick.whenPressed(new IntakeRelease());
        toggleHarvesterWheelsButtonOnDriveStick.toggleWhenPressed(new ToggleHarvesterWheels());
        toggleHarvesterWheelsButtonOnArcadeStick.toggleWhenPressed(new ToggleHarvesterWheels());
        stopHarvesterWheelsButtonOnDriveStick.whenPressed(new HarvesterStopWheels());
        stopHarvesterWheelsButtonOnArcadeStick.whenPressed(new HarvesterStopWheels());
    }

    private void initDrivetrainOI()
    {
        // Initalize the drivetrain
    }

    public static double getScaledThrottle()
    {
        return Math.max(Math.min(1-OI.sDriveStick.getZ(),1),0.4);
    }

}
