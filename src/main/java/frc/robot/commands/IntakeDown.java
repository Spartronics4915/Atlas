package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Launcher;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Extends the intake in order to pick up ball.
 * The harvester wheels runs until launcher confirms ball is present in
 * the subsystem.
 */
public class IntakeDown extends CommandBase
{
    private Harvester mHarvester;
    private Launcher mLauncher;

    private boolean shouldStop = true;

    public IntakeDown(Harvester harvester, Launcher launcher)
    {
        mHarvester = harvester;
        mLauncher = launcher;           // confirms ball is harvested
        addRequirements(mHarvester);
    }

    @Override
    public void initialize()
    {
        if (mLauncher.isBallPresent())
        {
            shouldStop = true;
        }
        else
        {
            shouldStop = false;
        }
    }

    @Override
    public void execute()
    {

        // Based on ball presence, run wheels continously or not w/ diff speeds
        if (shouldStop)
        {
            mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed / 2);
        }
        else
        {
            mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed);
        }

        // no need to check the harvester position -- just extend the intake
        mHarvester.extendPneumatics();
    }

    @Override
    public boolean isFinished()
    {
        // FIXME review shouldStop objective -- when to reset its value
        // (did we say don't ever stop?)
        if (shouldStop && mHarvester.isHarvesterDown())
        {
            mHarvester.stopHarversterWheels();
            return true;
        }
        else if (mLauncher.isBallPresent())
        {
            // return true only if intake successfully picked up the ball
            Logger.info("Command: IntakeDown picked up a ball -- time to stop");
            mHarvester.stopHarversterWheels();
            return true;
        }

        Logger.info("Command: IntakeDown should NOT finish!");
        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("Command: IntakeDown is interrupted");
        }

        if (mLauncher.isBallPresent())
        {
            // stop wheels only if the intake successfully picked up the ball
            Logger.info("Command: IntakeDown picked up a ball -- time to stop");
            mHarvester.stopHarversterWheels();
        }
        Logger.info("Command: IntakeDown is ended");
    }
}
