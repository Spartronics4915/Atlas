package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.Harvester;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class ToggleHarvesterWheels extends CommandBase
{
    private final Harvester mHarvester;

    public ToggleHarvesterWheels(Harvester subsystem)
    {
        mHarvester = subsystem;
        addRequirements(mHarvester);
    }

	@Override
    public void initialize()
    {
        // toggles the wheels on/off
        if (mHarvester.areWheelsStopped() == false)
        {
            Logger.info("Command: ToggleHarvesterWheels initialize -- running... toggle stop");
            mHarvester.setWheelSpeed(0.0);
        }
        else
        {
            Logger.info("Command: ToggleHarvesterWheels initialize -- stopped... toggle running");
            if (!mHarvester.isHarvesterUp())
            {
                mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed);
            }
        }
    }

    @Override
    public boolean isFinished()
    {
        // return false -- as we don't want to let the default harvester command to stop wheels
        // command will finish when trigger pressed & wheels stops
        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("Command: ToggleHarvesterWheels interrupted");
        }
        Logger.info("Command: ToggleHarvesterWheels ended");
        mHarvester.setWheelSpeed(0.0);
    }
}
