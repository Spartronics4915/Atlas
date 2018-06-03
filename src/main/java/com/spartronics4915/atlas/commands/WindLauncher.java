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
public class WindLauncher extends Command
{

    private Harvester mHarvester;
    private Launcher mLauncher;

    public WindLauncher() {
        mHarvester = Harvester.getInstance();
        mLauncher = Launcher.getInstance();
        requires(mLauncher);
    }

    @Override
    protected void initialize()
    {
        Logger.info("Command: WindLauncher initialize");
        // Riyadth says we don't need to check for Harvester -- the launcher will wind down w/o problem

        // retract the launcher pneumatics 
        mLauncher.launcherPrepareForWinding();
        
        // set a safety timeout if something goes wrong w/ switch
        setTimeout(6.0);

        setInterruptible(false);
    }

    @Override
    protected void execute()
    {
        // start winding the motors
        mHarvester.setWheelSpeed(0.0);
        mLauncher.startLauncherWindingMotor();
    }

    @Override
    protected boolean isFinished()
    {
        if (isTimedOut() || mLauncher.isLauncherRewound())
        {
            Logger.info("Command:WindLauncher: isFinished True");
            return true;
        }
        return false;
    }

    @Override
    protected void end()
    {
        Logger.info("Command:WindLauncher: time to end()");
        mLauncher.stopLauncherWindingMotor();
    }

    @Override
    protected void interrupted()
    {
        Logger.info("Command:WindLauncher: interrupted called, but wasn't interruptable -- why?!");
        end();
    }
}
