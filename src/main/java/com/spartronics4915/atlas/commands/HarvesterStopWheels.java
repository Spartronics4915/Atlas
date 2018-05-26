package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.subsystems.Harvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class HarvesterStopWheels extends Command
{
    private Harvester mHarvester;

    public HarvesterStopWheels()
    {
        mHarvester = Harvester.getInstance();
        requires(mHarvester);
    }

    @Override
    protected void initialize()
    {
        mHarvester.setWheelSpeed(0.0);
    }

    @Override
    protected void execute()
    {
        mHarvester.setWheelSpeed(0.0);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
