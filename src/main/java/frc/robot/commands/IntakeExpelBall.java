package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Launcher;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Runs harvester wheels back to release ball
 * Checks if ball is present before expelling & assumes intake is up,
 * which is ideal state for expelling balls
 * Automatically stops harvester wheels after timeout reached
 */
public class IntakeExpelBall extends CommandBase
{
    private Harvester mHarvester;
    private Launcher mLauncher;
    private boolean shouldAbort = false;

    public IntakeExpelBall(Harvester harvester, Launcher launcher)
    {
        mHarvester = harvester;
        mLauncher = launcher;
        addRequirements(mHarvester, mLauncher);
    }

    @Override
    public void initialize()
    {
        // do not run wheels when harverster is down
        if (mHarvester.isHarvesterDown())
        {
            shouldAbort = true;
        }
        else
        {
            shouldAbort = false;
        }
    }

    @Override
    public void execute()
    {
        if (!shouldAbort && mLauncher.isBallPresent())
        {
            mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterExpelWheelSpeed);
        }
    }

    @Override
    public boolean isFinished()
    {
        // keep running wheels until ball expelled
        if (shouldAbort || !mLauncher.isBallPresent())
        {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        mHarvester.stopHarversterWheels();
        Logger.info("Command: IntakeRelease is ended");
    }
}
