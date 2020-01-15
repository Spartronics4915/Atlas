package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Harvester;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * WindLauncher:
 *  - Run launcher winding motor until limit switch is hit
 *  - Note: if Harvester is up, it will end the command
 */
public class WindLauncher extends CommandBase
{

    private Harvester mHarvester;
    private Launcher mLauncher;

    public WindLauncher(Harvester harvester, Launcher launcher) {
        mHarvester = harvester;
        mLauncher = launcher;
        addRequirements(mLauncher);
    }

    @Override
    public void initialize()
    {
        Logger.info("Command: WindLauncher initialize");
        // Riyadth says we don't need to check for Harvester -- the launcher will wind down w/o problem

        // retract the launcher pneumatics
        mLauncher.launcherPrepareForWinding();
    }

    @Override
    public void execute()
    {
        // start winding the motors
        mHarvester.stopHarversterWheels();
        mLauncher.startLauncherWindingMotor();
    }

    @Override
    public boolean isFinished()
    {
        if (mLauncher.isLauncherRewound())
        {
            Logger.info("Command:WindLauncher: isFinished True");
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("Command:WindLauncher: interrupted called, but wasn't interruptable -- why?!");
        }
        mLauncher.stopLauncherWindingMotor();
        Logger.info("Command:WindLauncher: is ended");
    }

}
