package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Launcher;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Command to raise the intake -- Requires the launcher to be rewound
 * for the command to operate.
 *   - If launcher unwound, do NOT execute any code and just end the command
 *   - run the wheels by default, as we may not properly detect ball
 */
public class IntakeUp extends CommandBase
{
    private Harvester mHarvester;
    private Launcher mLauncher;
    private boolean shouldAbort;

    public IntakeUp(Harvester harvester, Launcher launcher)
    {
        mHarvester = harvester;
        mLauncher = launcher;
        shouldAbort = false;
        addRequirements(mHarvester, mLauncher);
    }

    @Override
    public void initialize()
    {
        // If launcher is not rewound -- do NOT retract pneumatics!
        if (!mLauncher.isLauncherRewound() || mHarvester.isHarvesterUp())
        {
            shouldAbort = true;
        }
        else
        {
            shouldAbort = false;
            mHarvester.retractPneumatics();
        }
    }

    @Override
    public void execute()
    {
        mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed / 2);
    }

    @Override
    public boolean isFinished()
    {
        if ((shouldAbort) || mHarvester.isHarvesterUp())
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
        }
        mHarvester.stopHarversterWheels();
        Logger.info("Command: IntakeUp is ended");
    }
}
