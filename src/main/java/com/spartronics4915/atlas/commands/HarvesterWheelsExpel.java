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

    protected void initialize()
    {
        mHarvester.setWheelSpeed(0.0);
    }

    protected void execute()
    {
        mHarvester.setWheelSpeed(RobotMap.kHarvesterExpelWheelSpeed);
    }

    protected boolean isFinished()
    {
        return interruptionFinish;
    }

    protected void end()
    {
        //stop wheels?
    }

    protected void interrupted()
    {
        interruptionFinish = true;
    }
}
