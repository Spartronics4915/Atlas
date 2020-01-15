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
 *
 * Note: for demonstration purposes and in the hopes to extend battery
 * life, once the ball is acquired, code just stops the wheels while
 * leaving intake down. This allows the driver to easily eject the ball
 * or perform launch operation.
 */
public class IntakeDownForPickup extends CommandBase
{
    private Harvester mHarvester;
    private Launcher mLauncher;

    public IntakeDownForPickup(Harvester harvester, Launcher launcher)
    {
        mHarvester = harvester;
        mLauncher = launcher;           // confirms ball is harvested
        addRequirements(mHarvester);
    }

    @Override
    public void initialize()
    {
        // no need to check the harvester position -- just extend the intake
        mHarvester.extendPneumatics();
    }

    @Override
    public void execute()
    {
        // Ball's presence determines how fast we run the wheels
        if (mLauncher.isBallPresent())
        {
            // Ball is present: run the wheels slowly to keep the ball within the intake
            mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed / 2);
        }
        else
        {
            // Hunting for ball: run the wheels faster to harvest
            mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed);
        }
    }

    @Override
    public boolean isFinished()
    {
        if (mLauncher.isBallPresent())
        {
            // return true only if intake successfully picked up the ball
            Logger.info("Command: IntakeDown picked up a ball -- time to stop");
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean isInterrupted)
    {
        mHarvester.stopHarversterWheels();
        Logger.info("Command: IntakeDown is ended");
    }
}
