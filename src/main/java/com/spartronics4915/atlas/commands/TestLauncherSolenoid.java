package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.subsystems.Launcher;
import com.spartronics4915.atlas.subsystems.Harvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * WindLauncher:
 *  - Run launcher winding motor until limit switch is hit
 *  - Note: if Harvester is up, it will end the command
 */
public class TestLauncherSolenoid extends Command
{

    private Launcher mLauncher;
    private Harvester mHarvester;
    private boolean isAborted = false;

    public TestLauncherSolenoid() {
        mLauncher = Launcher.getInstance();
        mHarvester = Harvester.getInstance();
        requires(mLauncher);
    }

    @Override
    protected void initialize()
    {
        Logger.info("Command: TestLauncherSolenoid initialize");

        setInterruptible(false);

        // If harvester is up, cancel the command & turn of harvester wheels
        if (mHarvester.isHarvesterUp() || !mLauncher.isBallPresent()) 
        {
            Logger.info("Command:TestLauncherSolenoid: Initialize quitting -- harvester is up or ball is not present");
            isAborted = true;
            return;
        }
        isAborted = false;

        // waits before reengaging the gears 
        setTimeout(2.0);

        // launches the ball
        Logger.info("Cmd: Test Launch Activated");
        mLauncher.launcherLaunchBall();
    }

    @Override
    protected void execute()
    {
    }

    @Override
    protected boolean isFinished()
    {
        Logger.info("Command:TestLauncherSolenoid: isFinished True");
        if (isTimedOut() || isAborted)
        {
            Logger.info("Cmd Test Launcher isTimedout OR aborted");
            return true;
        }
        return false;
    }

    @Override
    protected void end()
    {
        Logger.info("Command:TestLauncherSolenoid: time to end()");
        mLauncher.launcherPrepareForWinding();
    }

    @Override
    protected void interrupted()
    {
        Logger.info("Command:TestLauncherSolenoid: interrupted called, but wasn't interruptable -- why?!");
    }
}
