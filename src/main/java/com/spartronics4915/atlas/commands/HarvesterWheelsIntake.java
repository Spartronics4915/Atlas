package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.subsystems.Harvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class HarvesterWheelsIntake extends Command
{
    private boolean interruptionFinish = false;

    private Harvester mHarvester;

    public HarvesterWheelsIntake()
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
        mHarvester.setWheelSpeed(RobotMap.kHarvesterIntakeWheelSpeed);
    }

    protected boolean isFinished()
    {
        return interruptionFinish;
    }

    protected void end()
    {
        //Stop wheels?
    }

    protected void interrupted()
    {
        interruptionFinish = true;
    }
}
