package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Launcher;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class IntakeUp extends CommandBase
{
    private Harvester mHarvester;
    private Launcher mLauncher;
    private boolean shouldQuit;

    public IntakeUp(Harvester harvester, Launcher launcher)
    {
        mHarvester = harvester;
        mLauncher = launcher;
        shouldQuit = false;
        addRequirements(mHarvester);
    }

    @Override
    public void initialize()
    {
        mHarvester.stopHarversterWheels();

        // Only check IF launcher is unwound -- we may need to
        // pressuraize harvester
        // If launcher is not rewound -- do NOT retract pneumatics!
        if (!mLauncher.isLauncherRewound())
        {
            shouldQuit = true;
        }
        else
        {
            mHarvester.retractPneumatics();
        }
    }

    @Override
    public void execute()
    {
        if(!shouldQuit)
        {
            mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed);
        }
    }

    @Override
    public boolean isFinished()
    {
        if(shouldQuit)
        {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("Command: IntakeUp is interrupted");
            shouldQuit = true;
        }
        mHarvester.stopHarversterWheels();
        Logger.info("Command: IntakeUp is ended");
    }
}
