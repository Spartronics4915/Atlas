package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.Harvester;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Runs harvester wheels back to release ball
 * Automatically stops harvester wheels after timeout reached
 */
public class IntakeRelease extends CommandBase
{
    private Harvester mHarvester;

    public IntakeRelease(Harvester harvester)
    {
        mHarvester = harvester;
        addRequirements(mHarvester);
    }

    @Override
    public void initialize()
    {
        mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterExpelWheelSpeed);
    }

    @Override
    public void end(boolean isInterrupted)
    {
        mHarvester.stopHarversterWheels();
        Logger.info("Command: IntakeRelease is ended");
    }
}
