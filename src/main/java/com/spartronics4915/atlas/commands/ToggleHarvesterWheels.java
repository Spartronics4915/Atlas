package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.subsystems.Harvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class ToggleHarvesterWheels extends Command
{
    private boolean areStopped;
    private Harvester mHarvester;

    public ToggleHarvesterWheels()
    {
        mHarvester = Harvester.getInstance();
        areStopped = mHarvester.areWheelsStopped();

        setInterruptible(true);
        requires(mHarvester);
    }

    @Override
    protected void initialize()
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
                mHarvester.setWheelSpeed(RobotMap.kHarvesterIntakeWheelSpeed);
            }
        }
    }

    @Override
    protected void execute()
    {
        // nothing to see -- all done in initialization
    }

    @Override
    protected boolean isFinished()
    {
        // return false -- as we don't want to let the default harvester command to stop wheels
        // command will finish when trigger pressed & wheels stops
        return false;
    }

    @Override
    protected void end()
    {
        Logger.info("Command: ToggleHarvesterWheels ended");
        mHarvester.setWheelSpeed(0.0);
    }

    @Override
    protected void interrupted()
    {
        // as command interruptable, any harvester action will stop the wheels
        Logger.info("Command: ToggleHarvesterWheels interrupted");
        end();
    }
}
