package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.Robot;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.subsystems.Harvester;
import com.spartronics4915.atlas.subsystems.Launcher;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class IntakeDown extends Command
{
    private Harvester mHarvester;
    private Launcher mLauncher;

    private boolean shouldStop = true;

    public IntakeDown()
    {
        mHarvester = Harvester.getInstance();
        mLauncher = Launcher.getInstance();
        requires(mHarvester);
    }

    @Override
    protected void initialize()
    {
        if (mLauncher.isBallPresent())
        {
            shouldStop = true;
        }
        else
        {
            shouldStop = false;
        }
    }

    @Override
    protected void execute()
    {

        // Based on ball presence, run wheels continously or not w/ diff speeds
        if (shouldStop)
        {
            mHarvester.setWheelSpeed(RobotMap.kHarvesterIntakeWheelSpeed / 2);
        }
        else
        {
            mHarvester.setWheelSpeed(RobotMap.kHarvesterIntakeWheelSpeed);
        }

        // no need to check the harvester position -- just extend the intake
        mHarvester.extendPneumatics();
    }

    @Override
    protected boolean isFinished()
    {
        if (shouldStop && mHarvester.isHarvesterDown())
        {
            mHarvester.setWheelSpeed(0.0);
            return true;
        }
        else if (mLauncher.isBallPresent())
        {
            // return true only if intake successfully picked up the ball
            Logger.info("Command: IntakeDown picked up a ball -- time to stop");
            mHarvester.setWheelSpeed(0.0);
            return true;
        }

        Logger.info("Command: IntakeDown is should NOT finish!");
        return false;
    }

    @Override
    protected void end()
    {
        Logger.info("Command: IntakeDown is ended");
        if (mLauncher.isBallPresent())
        {
            // stop wheels only if the intake successfully picked up the ball
            Logger.info("Command: IntakeDown picked up a ball -- time to stop");
            mHarvester.setWheelSpeed(0.0);
        }
    }

    @Override
    protected void interrupted()
    {
        Logger.info("Command: IntakeDown is interrupted");
        end();
    }
}
