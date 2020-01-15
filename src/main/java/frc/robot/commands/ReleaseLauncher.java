package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Harvester;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * WindLauncher:
 *  - Run launcher winding motor until limit switch is hit
 *  - Note: if Harvester is up, it will end the command
 *    This mode is used to release the launcher to release elastics
 *      - it will not rewind afterwards!
 */
public class ReleaseLauncher extends CommandBase
{
    private Launcher mLauncher;
    private Harvester mHarvester;
    private boolean isAborted = false;

    public ReleaseLauncher(Harvester harvester, Launcher launcher) {
        mLauncher = launcher;
        mHarvester = harvester;
        addRequirements(mLauncher, mHarvester);
    }

    @Override
    public void initialize()
    {
        Logger.info("Command: ReleaseLauncher initialize");

        // If harvester is up or ball is present, cancel the command & turn of harvester wheels
        if (mHarvester.isHarvesterUp() || mLauncher.isBallPresent())
        {
            Logger.info("Command:TestLauncherSolenoid: Initialize quitting -- harvester is up or ball is not present");
            isAborted = true;
            return;
        }

        // launches the ball
        Logger.info("Cmd: Test Launch Activated");
        mLauncher.launcherLaunchBall();
    }

    @Override
    public boolean isFinished()
    {
        if (isAborted)
        {
            Logger.info("Cmd Test Launcher aborted");
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("Command:ReleaseLauncher interrupted called, but wasn't interruptable -- why?!");
        }
        // leave the launcher ready for winding
        mLauncher.launcherPrepareForWinding();
        Logger.info("Command:ReleaseLauncher is ended");
    }
}
