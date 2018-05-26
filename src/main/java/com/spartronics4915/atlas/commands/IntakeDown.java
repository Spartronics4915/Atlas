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
public class IntakeDown extends Command
{
    private Harvester mHarvester;
    private Launcher mLauncher;

    public IntakeDown()
    {
        mHarvester = Harvester.getInstance();
        mLauncher = Launcher.getInstance();
        requires(mHarvester);
    }

    @Override
    protected void initialize()
    {
        if(mHarvester.isHarvesterUp())
        {
            mHarvester.extendPneumatics();
        }
    }

    @Override
    protected void execute()
    {
        mHarvester.setWheelSpeed(0.0);
        mLauncher.stopLauncherWindingMotor();
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
        end();
    }
}
