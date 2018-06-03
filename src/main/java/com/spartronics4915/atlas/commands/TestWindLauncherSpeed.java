package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.subsystems.Launcher;
import com.spartronics4915.atlas.subsystems.Harvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * TestWindLauncherSpeed:
 *  - Runs the launcher winding motor while the button is held
 *  - Test mode only! It will read & update values from smart dashboard
 *  - Important: from whileHeld() testing, only start() and cancel() is getting called
 */
public class TestWindLauncherSpeed extends Command
{

    private Harvester mHarvester;
    private Launcher mLauncher;

    private double mSpeed;
    private boolean amRunning = false;


    public TestWindLauncherSpeed() {
        mHarvester = Harvester.getInstance();
        mLauncher = Launcher.getInstance();
        requires(mLauncher);
    }

    @Override
    protected void initialize()
    {
        // Important: whileHeld() does not call initialize()
        Logger.info("Command: TestWindLauncherSpeed initialize");
    }

    /** 
     *  Read the dashboard value and update the speeds
     */
    public void start()
    {
        if (!amRunning)
        {
            Logger.info("Command: TestWindLauncherSpeed start() testing whileHeld");
            amRunning = true;

            // retract the launcher pneumatics
            mLauncher.launcherPrepareForWinding();
        }
        // check if safe to wind the launcher
        if (mHarvester.isHarvesterUp() || mLauncher.isLauncherRewound()) {
            mLauncher.stopLauncherWindingMotor();
            return;
        }
        
        // update mSpeed from the latest values
        mHarvester.setWheelSpeed(0.0);
        mSpeed = mLauncher.readFromSmartDashboard();
        //mSpeed = mLauncher.getLauncherWindingMotorSetSpeed();
        mLauncher.startLauncherWindingMotor(mSpeed);
    }

    public void cancel()
    {
        Logger.info("Command: TestWindLauncherSpeed cancel() testing whileHeld");
        mLauncher.stopLauncherWindingMotor();
        Logger.info("Command: TestWindLauncherSpeed -- if needed, make the code change for default winder speed");
        amRunning = false;
    }

    @Override
    protected void execute()
    {
        // Important: whileHeld() doesn't call execute()
        Logger.info("Command: TestWindLauncherSpeed execute() testing whileHeld");
    }

    @Override
    protected boolean isFinished()
    {
        // Important: whileHeld() doesn't call isFinished()
        Logger.info("Command:TestWindLauncherSpeed: isFinished() True");
        return true;
    }

    @Override
    protected void end()
    {
        // Important: whileHeld() doesn't call end()
        Logger.info("Command:TestWindLauncherSpeed: time to end()");
        mLauncher.stopLauncherWindingMotor();
    }

    @Override
    protected void interrupted()
    {
        Logger.info("Command:TestWindLauncherSpeed: interrupted called");
    }
}
