package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.Harvester;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class IntakeDownWithWheels extends CommandBase
{
    private Harvester mHarvester;

    public IntakeDownWithWheels(Harvester harvester)
    {
        mHarvester = harvester;
        addRequirements(mHarvester);
    }

    @Override
    public void initialize()
    {
        //Logger.info("Command: IntakeDownWithWheels initialize");
        mHarvester.extendPneumatics();
    }

    @Override
    public void execute()
    {
        // give a gentle push to the ball towards the launcher in case it gets stuck
        mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed/2);
    }

    @Override
    public boolean isFinished()
    {
        return mHarvester.isHarvesterDown();
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("Command: IntakeDownWithWheels interrupted -- why??");
        }
        mHarvester.stopHarversterWheels();
    }
}
