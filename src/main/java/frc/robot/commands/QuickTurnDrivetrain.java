package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import frc.robot.Constants.DriveTurnConstants;
import frc.robot.subsystems.Drivetrain;

/**
 * A command that will turn the robot by 180
 */
public class QuickTurnDrivetrain extends PIDCommand {

  public QuickTurnDrivetrain(Drivetrain drive) {
    super(
        new PIDController(DriveTurnConstants.kP, DriveTurnConstants.kI, DriveTurnConstants.kD),
        // Close loop on heading
        () -> drive.getIMUHeading().getDegrees(),
        // Set reference to target
        180,
        // Pipe output to turn robot
        output -> drive.arcadeDrive(0, output),
        // Require the drive
        drive);

    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(DriveTurnConstants.kTurnToleranceDeg, DriveTurnConstants.kTurnRateToleranceDegPerS);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
}