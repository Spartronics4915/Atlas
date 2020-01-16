package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Harvester;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * ActivateLauncher:
 *  - If the Harverster is up, it will end the command
 *  - Release launcher pneumatic to initiate launch
 *  - Note -- will only launch if the ball is present
 */
public class ActivateLauncher extends CommandBase
{

    private Harvester mHarvester;
    private Launcher mLauncher;

    public ActivateLauncher(Harvester harvester, Launcher launcher) {
        mHarvester = harvester;
        mLauncher = launcher;
        addRequirements(mHarvester, mLauncher);
    }

    @Override
    public void initialize()
    {
        Logger.info("Command: ActivateLauncher initialize");
        // If harvester is up, cancel the command & turn of harvester wheels
        if (!mHarvester.isHarvesterDown() || !mLauncher.isBallPresent())
        {
            Logger.info("Command:ActivateLauncher: Initialize quitting -- harvester is up or ball is not present");
            return;
        }

        // if any motors running, turn them off
        mHarvester.stopHarversterWheels();
        mLauncher.stopLauncherWindingMotor();

        // extand/release the launcher pneumatics
        mLauncher.launcherLaunchBall();
    }

    @Override
    public void execute()
    {
        // activating pneumatics is instantenous -- indicate ready to quit
        // if any motors running, turn them off
        mHarvester.stopHarversterWheels();
        mLauncher.stopLauncherWindingMotor();
    }

    @Override
    public boolean isFinished()
    {
        // TODO review timeout approach to ending command
        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("Command:ActivateLauncher: interrupted called, but wasn't interruptable -- why?!");
        }
        Logger.info("Command:ActivateLauncher: time to end()");
    }
}
