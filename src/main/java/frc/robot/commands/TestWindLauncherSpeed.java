package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Harvester;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * TestWindLauncherSpeed:
 *  - Runs the launcher winding motor while the button is held
 *  - Test mode only! It will read & update values from smart dashboard
 *  - Important: from whileHeld() testing, only start() and cancel() is getting called
 */
public class TestWindLauncherSpeed extends CommandBase
{

    private Harvester mHarvester;
    private Launcher mLauncher;

    private double mSpeed;
    private boolean amRunning = false;


    public TestWindLauncherSpeed(Harvester harvester, Launcher launcher) {
        mHarvester = harvester;
        mLauncher = launcher;
        addRequirements(mLauncher);
    }

    @Override
    public void initialize()
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
        // check if safe to wind the launcher -- rewound regardless of harvester state
        if (mLauncher.isLauncherRewound()) {
            mLauncher.stopLauncherWindingMotor();
            return;
        }

        // update mSpeed from the latest values
        mHarvester.stopHarversterWheels();;
        mSpeed = mLauncher.readFromSmartDashboard();
        Logger.info("Command: TestWindLauncherSpeed is = " +  mSpeed);
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
    public void execute()
    {
        // Important: whileHeld() doesn't call execute()
        Logger.info("Command: TestWindLauncherSpeed execute() testing whileHeld");
    }

    @Override
    public boolean isFinished()
    {
        // Important: whileHeld() doesn't call isFinished()
        Logger.info("Command:TestWindLauncherSpeed: isFinished() True");
        return true;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        // Important: whileHeld() doesn't call end()
        if (isInterrupted)
        {
            Logger.info("Command:TestWindLauncherSpeed: interrupted called");
        }

        mLauncher.stopLauncherWindingMotor();
        Logger.info("Command:TestWindLauncherSpeed: is ended");
    }
}
