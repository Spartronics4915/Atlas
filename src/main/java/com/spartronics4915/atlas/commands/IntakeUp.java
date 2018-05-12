package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.subsystems.Harvester;
import com.spartronics4915.atlas.subsystems.Launcher;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class IntakeUp extends Command
{
    private Harvester mHarvester;
    private boolean shouldQuit;

    public IntakeUp()
    {
        mHarvester = Harvester.getInstance();
        shouldQuit = false;
        requires(mHarvester);
    }

    protected void initialize()
    {
        mHarvester.setWheelSpeed(0.0);
        
        if(mHarvester.isHarvesterUp())
        {
            shouldQuit = true;
        }
        else
        {
            mHarvester.retractPneumatics();
        }
        setTimeout(1.0);
    }

    protected void execute()
    {
        if(!shouldQuit)
        {
            mHarvester.setWheelSpeed(RobotMap.kHarvesterIntakeWheelSpeed);
        }
    }

    protected boolean isFinished()
    {
        if(shouldQuit)
        {
            return true;
        }
        else
        {
            return isTimedOut();
        }
    }

    protected void end()
    {
        mHarvester.setWheelSpeed(0.0);
    }

    protected void interrupted()
    {
        end();
    }
}
