package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.subsystems.Harvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class HarvesterWheelsExpel extends Command
{

    private boolean interruptionFinish = false;

    private Harvester mHarvester;

    public HarvesterWheelsExpel()
    {
        mHarvester = Harvester.getInstance();
        requires(mHarvester);
    }

    @Override
    protected void initialize()
    {
        interruptionFinish = false;
        mHarvester.setWheelSpeed(0.0);
    }

    @Override
    protected void execute()
    {
        mHarvester.setWheelSpeed(RobotMap.kHarvesterExpelWheelSpeed);
    }

    @Override
    protected boolean isFinished()
    {
        return interruptionFinish;
    }

    @Override
    protected void end()
    {
        //stop wheels?
    }

    @Override
    protected void interrupted()
    {
        interruptionFinish = true;
        isFinished();
    }
}
