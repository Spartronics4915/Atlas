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

    private Harvester mHarvester = Harvester.getInstance();
    private Launcher mLauncher = Launcher.getInstance();

    private boolean shouldQuit = false;

    public ActivateLauncher() {
        requires(mLauncher);
    }

    protected void initialize()
    {
        Logger.info("Command: ActivateLauncher initialize");
        // If harvester is up, cancel the command & turn of harvester wheels
        if (mHarvester.isHarvesterUp() || !mLauncher.isBallPresent()) 
        {
            shouldQuit = true;
            return;
        }

        // extand/release the launcher pneumatics 
        mLauncher.launcherExtendSolenoid();

        setInterruptible(false);
    }

    protected void execute()
    {
        // if any motors running, turn them off
        if (!shouldQuit)
        {
            mHarvester.setWheelSpeed(0.0);
            mLauncher.stopLauncherWindingMotor();
        }
        // activating pneumatics is instantenous -- indicate ready to quit
        shouldQuit = true;
    }

    protected boolean isFinished()
    {
        if (shouldQuit)
        {
            Logger.info("Command:ActivateLauncher: isFinished True");
            return true;
        }
        return false;
    }

    protected void end()
    {
        Logger.info("Command:ActivateLauncher: time to end()");
    }

    protected void interrupted()
    {
        Logger.info("Command:ActivateLauncher: interrupted called, but wasn't interruptable -- why?!");
    }
}
