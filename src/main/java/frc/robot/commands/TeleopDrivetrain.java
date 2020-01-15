package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * TeleopDrivetrain runs the drivetrain using a drive stick.
 */
public class TeleopDrivetrain extends CommandBase
{

    private Drivetrain mDrivetrain;

    public TeleopDrivetrain(Drivetrain drivetrain)
    {
        mDrivetrain = drivetrain;
        addRequirements(mDrivetrain);
    }

    @Override
    public void initialize()
    {
        Logger.info("TeleopDrivetrain initialized");
    }

    @Override
    public void execute()
    {
        // getZ is the throttle
        mDrivetrain.arcadeDrive(RobotContainer.mDriverController.getY() * RobotContainer.getScaledThrottle(),
                                RobotContainer.mDriverController.getX() * -1); // Steering is reversed!
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("TeleopDrivetrain interrupted");
        }
        mDrivetrain.stop();
        Logger.info("TeleopDrivetrain ended");
    }
}
