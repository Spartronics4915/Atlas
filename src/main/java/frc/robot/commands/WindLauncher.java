package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Harvester;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * WindLauncher:
 *  - Run launcher winding motor until limit switch is hit
 *  - Note: if Harvester is up, it will lower harvester
 *  - Note: if ball is present, it will end the command
 */
public class WindLauncher extends CommandBase
{

    private Harvester mHarvester;
    private Launcher mLauncher;
    private boolean shouldAbort = false;

    public WindLauncher(Harvester harvester, Launcher launcher) {
        mHarvester = harvester;
        mLauncher = launcher;
        addRequirements(mLauncher, mHarvester);
    }

    @Override
    public void initialize()
    {
        Logger.info("Command: WindLauncher initialize");
        // Riyadth says we don't need to check for Harvester -- the launcher will wind down w/o problem

        if (mLauncher.isBallPresent())
        {
            shouldAbort = true;
            return;
        }
        else
        {
            shouldAbort = false;
        }
        // bring harvester down
        mHarvester.stopHarversterWheels();

        // retract the launcher pneumatics
        Logger.info("Cmd: WindLauncher: set the DoubleSolenoid.Value.kReverse");
        mLauncher.launcherPrepareForWinding();
    }

    @Override
    public void execute()
    {
        // start winding the motors
        mLauncher.startLauncherWindingMotor();
    }

    @Override
    public boolean isFinished()
    {
        if (shouldAbort || mLauncher.isLauncherRewound())
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
            Logger.info("Command:WindLauncher: interrupted called, but should be interruptable -- why?!");
        }
        mLauncher.stopLauncherWindingMotor();
        Logger.info("Command:WindLauncher: is ended");
    }

}
