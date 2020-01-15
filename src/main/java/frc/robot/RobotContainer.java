/*---------------------------oftware - may be modified and shared by FRC teams.-------------------------*/
/* Copyright (c) 2
------------------------018-2019 FIRST. All Rights Reserved.                        */
/* Open Source The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

import frc.robot.Constants.RobotMapConstants;
import frc.robot.Constants.OIConstants;

import frc.util.CANProbe;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.jar.Manifest;
import java.util.jar.Attributes;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final Drivetrain mDrivetrain;
  public final Launcher mLauncher;
  private Harvester mHarvester;
  private LED mLED;

  // The driver's controller
  public static final Joystick mDriverController = new Joystick(OIConstants.kDriveJoystickPort);
  // public static final Joystick mArcadeController = new Joystick(OIConstants.kArcadeStickPort);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // initialize the robot subsystems
    mDrivetrain = Drivetrain.getInstance();
    mLauncher = Launcher.getInstance();
    mHarvester = Harvester.getInstance();
    mLED = LED.getInstance();

    final CANProbe canProbe = CANProbe.getInstance();
    final ArrayList<String> canReport = canProbe.getReport();
    Logger.notice("CANDevicesFound: " + canReport);
    final int numDevices = canProbe.getCANDeviceCount();
    SmartDashboard.putString("CANBusStatus",
            numDevices == RobotMapConstants.kNumCANDevices ? "OK"
                : ("" + numDevices + "/" + RobotMapConstants.kNumCANDevices));

    // Configure the button bindings
    configureButtonBindings();

    // set default commands
    mDrivetrain.setDefaultCommand(new TeleopDrivetrain(mDrivetrain));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings()
  {
        // TODO Reimplement in Gradle
        // Version string and related information
        try (InputStream manifest = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"))
        {
            // build a version string
            final Attributes attributes = new Manifest(manifest).getMainAttributes();
            final String buildStr = "by: " + attributes.getValue("Built-By") +
                    "  on: " + attributes.getValue("Built-At") +
                    "  (" + attributes.getValue("Code-Version") + ")";
            SmartDashboard.putString("Build", buildStr);

            Logger.notice("=================================================");
            Logger.notice("Initialized in station " + SmartDashboard.getString("AllianceStation", "Blue"));
            Logger.notice(Instant.now().toString());
            Logger.notice("Built " + buildStr);
            Logger.notice("=================================================");

        }
        catch (final IOException e)
        {
            SmartDashboard.putString("Build", "version not found!");
            Logger.error("Build version not found!");
            Logger.logThrowableCrash(e);
        }

        // // initialize drivetrain buttons
        // new JoystickButton(mDriverController, OIConstants.kQuickTurnDriveStickButton).whileHeld(new QuickTurnDrivetrain());
        // // was: new JoystickButton(mDriverController, OIConstants.kDriveStraightDriveStickButton).whenPressed(new DriveStraightDrivetrain());
        // new JoystickButton(mDriverController, OIConstants.kDriveStraightDriveStickButton).whenHeld(new PIDCommand(
        //     new PIDController(DriveConstants.kP, DriveConstants.kI, DriveConstants.kD),
        //     // Close the loop on the turn rate
        //     mDrivetrain::getTurnRate,
        //     // Setpoint is 0
        //     0,
        //     // Pipe the output to the turning controls
        //     output -> mDrivetrain.arcadeDrive(mDriverController.getY(), output),
        //     // Require the robot drive
        //     mDrivetrain
        //   )
        // );


        // initialize launcher buttons
        // FIXME command group
        new JoystickButton(mDriverController, OIConstants.kLaunchDriveStickButton).whenPressed(new ActivateLauncherCommandGroup(mHarvester, mLauncher, mLED));
		    // new JoystickButton(mArcadeController, OIConstants.kLaunchArcadeStickButton).whenPressed(new ActivateLauncherCommandGroup(mHarvester, mLauncher, mLED));
        new JoystickButton(mDriverController, OIConstants.kWindLauncherDriveStickButton).whenPressed(new WindLauncherCommandGroup(mHarvester, mLauncher, mLED));
        // new JoystickButton(mArcadeController, OIConstants.kWindLauncherArcadeStickButton).whenPressed(new WindLauncherCommandGroup(mHarvester, mLauncher, mLED));

        // test buttons for launcher & winding motor
        // note: whileHeld repeatedly calls Command.schedule and
        // cancelled when button is released
        new JoystickButton(mDriverController, OIConstants.kTestLauncherWindingMotorDriveStickButton).whileHeld(new TestWindLauncherSpeed(mHarvester, mLauncher));
        new JoystickButton(mDriverController, OIConstants.kTestLauncherSolenoidDriveStickButton).whenPressed(new TestLauncherSolenoid(mHarvester, mLauncher).withTimeout(2.0));

        // initialize harvester buttons
        new JoystickButton(mDriverController, OIConstants.kHarvesterExtendDriveStickButton).whenPressed(new IntakeDown(mHarvester, mLauncher));
        // new JoystickButton(mArcadeController, OIConstants.kHarvesterExtendArcadeStickButton).whenPressed(new IntakeDown(mHarvester, mLauncher));
        new JoystickButton(mDriverController, OIConstants.kHarvesterRetractDriveStickButton).whenPressed(new IntakeUp(mHarvester, mLauncher).withTimeout(1.0));
        // new JoystickButton(mArcadeController, OIConstants.kHarvesterRetractArcadeStickButton).whenPressed(new IntakeUp(mHarvester, mLauncher));
        new JoystickButton(mDriverController, OIConstants.kHarvesterReleaseDriveStickButton).whenPressed(new IntakeRelease(mHarvester).withTimeout(3.0));
        // new JoystickButton(mArcadeController, OIConstants.kHarvesterReleaseArcadeStickButton).whenPressed(new IntakeRelease(mHarvester).withTimeout(3.0));

        // FIXME Interruptable for Harvester Wheel controls?
        new JoystickButton(mDriverController, OIConstants.kHarvesterWheelsToggleDriveStickButton).toggleWhenPressed(new ToggleHarvesterWheels(mHarvester));
        // new JoystickButton(mArcadeController, OIConstants.kHarvesterWheelsToggleArcadeStickButton).toggleWhenPressed(new ToggleHarvesterWheels(mHarvester));
        new JoystickButton(mDriverController, OIConstants.kHarvesterWheelsStopDriveStickButton).whenPressed(new InstantCommand(mHarvester::stopHarversterWheels, mHarvester));
        // new JoystickButton(mArcadeController, OIConstants.kHarvesterWheelsStopArcadeStickButton).whenPressed(new InstantCommand(mHarvester::stopHarversterWheels, mHarvester));
    }

    public static final double getScaledThrottle()
    {
        return Math.max(Math.min(1 - mDriverController.getZ(), 1), 0.4);
    }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Atlas does not support autonomous
    return null;
  }

  public void outputAllToSmartDashboard()
  {
      mLauncher.outputToSmartDashboard();
      mHarvester.outputToSmartDashboard();
  }
}
