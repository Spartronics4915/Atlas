package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.subsystems.Harvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class IntakeDownWithWheels extends Command
{
    private boolean interruptionFinish = false;

    private Harvester mHarvester;

    public IntakeDownWithWheels()
    {
        mHarvester = Harvester.getInstance();
        //setTimeout(1.0);
        setInterruptible(false);
        requires(mHarvester);
    }

    @Override
    protected void initialize()
    {
        //Logger.info("Command: IntakeDownWithWheels initialize");
        mHarvester.extendPneumatics();
    }

    @Override
    protected void execute()
    {
        // give a gentle push to the ball towards the launcher in case it gets stuck
        mHarvester.setWheelSpeed(RobotMap.kHarvesterIntakeWheelSpeed/2);
    }

    @Override
    protected boolean isFinished()
    {
        return mHarvester.isHarvesterDown();
    }

    @Override
    protected void end()
    {
        mHarvester.setWheelSpeed(0.0);
    }

    @Override
    protected void interrupted()
    {
        Logger.info("Command: IntakeDownWithWheels interrupted -- why??");
        end();
    }
}
