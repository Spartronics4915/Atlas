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
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj.controller.PIDController;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.LED.BlingState;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.DriveStraightConstants;

import frc.util.CANProbe;
import java.util.ArrayList;
import java.util.jar.Manifest;
import java.util.jar.Attributes;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

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

  // If Arcade Controller requested, this should be enabled
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


    // add LED test to the smartdashboard
    SmartDashboard.putData("Test Blue LED", (Sendable) new SetBlingStateCommand(mLED, BlingState.BLUE));

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
        // TODO important: quick turn needs testing & validation!
        // Turn to 180 degrees, with a 5 second timeout
        new JoystickButton(mDriverController, OIConstants.kQuickTurnDriveStickButton)
              .whenPressed(new QuickTurnDrivetrain(mDrivetrain).withTimeout(5));

        // TODO important: drive straight needs testing & validation!
        new JoystickButton(mDriverController, OIConstants.kDriveStraightDriveStickButton).whenHeld(new PIDCommand(
                new PIDController(DriveStraightConstants.kP, DriveStraightConstants.kI, DriveStraightConstants.kD),
                // Close the loop on the turn rate
                () -> mDrivetrain.getIMUHeading().getDegrees(),
                // Setpoint is 0
                0,
                // Pipe the output to the turning controls
                output -> mDrivetrain.arcadeDrive(mDriverController.getY() * getScaledThrottle(), output),
                // Require the robot drive
                mDrivetrain
            ));


        // initialize launcher buttons
        new JoystickButton(mDriverController, OIConstants.kLaunchDriveStickButton)
                                .whenPressed(new ActivateLauncherCommandGroup(mHarvester, mLauncher, mLED));
        new JoystickButton(mDriverController, OIConstants.kWindLauncherDriveStickButton)
                                .whenPressed(new WindLauncher(mHarvester, mLauncher));

        // Release launcher solenoid -- used for releasing elastics
        new JoystickButton(mDriverController, OIConstants.kTestLauncherSolenoidDriveStickButton)
                                .whenPressed(new ReleaseLauncher(mHarvester, mLauncher).withTimeout(2.0));

        // initialize harvester buttons
        new JoystickButton(mDriverController, OIConstants.kHarvesterExtendDriveStickButton)
                                .whenPressed(new IntakeDownForPickup(mHarvester, mLauncher));
        new JoystickButton(mDriverController, OIConstants.kHarvesterRetractDriveStickButton)
                                .whenPressed(new IntakeUp(mHarvester, mLauncher));
        new JoystickButton(mDriverController, OIConstants.kHarvesterReleaseDriveStickButton)
                                .whenPressed(new IntakeExpelBall(mHarvester, mLauncher).withTimeout(3.0));

        // new JoystickButton(mDriverController, OIConstants.kHarvesterWheelsToggleDriveStickButton).whenPressed(new InstantCommand(mHarvester::toggleHarvesterWheels));
        new JoystickButton(mDriverController, OIConstants.kHarvesterWheelsToggleDriveStickButton)
                                .whileHeld(new InstantCommand(mHarvester::runHarvesterWheels))
                                .whenReleased(() -> mHarvester.stopHarversterWheels());
        new JoystickButton(mDriverController, OIConstants.kHarvesterWheelsStopDriveStickButton)
                                .whenPressed(new InstantCommand(mHarvester::stopHarversterWheels, mHarvester));
        new JoystickButton(mDriverController, OIConstants.kHarvesterWheelsStopDriveStickButton_2)
                                .whenPressed(new InstantCommand(mHarvester::stopHarversterWheels, mHarvester));
    }

    public static final double getScaledThrottle()
    {
        double MAX_THROTTLE = 1.0;
        double MIN_THROTTLE = 0.45;
        double scaledThrottle = (((mDriverController.getZ() * -1) + 1) * 0.5) * (MAX_THROTTLE - MIN_THROTTLE) + MIN_THROTTLE ;
        SmartDashboard.putNumber("throttle", scaledThrottle);

        return (scaledThrottle);
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
