package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.subsystems.Launcher;
import com.spartronics4915.atlas.subsystems.Harvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * ActivateLauncher:
 *  - If the Harverster is up, it will end the command
 *  - Release launcher pneumatic to initiate launch
 *  - Note -- will only launch if the ball is present
 */
public class ActivateLauncher extends Command
{

    private Harvester mHarvester;
    private Launcher mLauncher;

    public ActivateLauncher() {
        mHarvester = Harvester.getInstance();
        mLauncher = Launcher.getInstance();
        requires(mLauncher);
    }

    @Override
    protected void initialize()
    {
        Logger.info("Command: ActivateLauncher initialize");
        // If harvester is up, cancel the command & turn of harvester wheels
        if (!mHarvester.isHarvesterDown() || !mLauncher.isBallPresent()) 
        {
            Logger.info("Command:ActivateLauncher: Initialize quitting -- harvester is up or ball is not present");
            return;
        }

        // if any motors running, turn them off
        mHarvester.setWheelSpeed(0.0);
        mLauncher.stopLauncherWindingMotor();

        // extand/release the launcher pneumatics 
        mLauncher.launcherLaunchBall();

        setInterruptible(false);
    }

    @Override
    protected void execute()
    {
        // activating pneumatics is instantenous -- indicate ready to quit
    }

    @Override
    protected boolean isFinished()
    {
        Logger.info("Command:ActivateLauncher: isFinished True");
        return true;
    }

    @Override
    protected void end()
    {
        Logger.info("Command:ActivateLauncher: time to end()");
    }

    protected void interrupted()
    {
        Logger.info("Command:ActivateLauncher: interrupted called, but wasn't interruptable -- why?!");
    }
}
