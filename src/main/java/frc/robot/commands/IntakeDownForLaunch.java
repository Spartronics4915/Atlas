package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Launcher;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * IntakeDownForLaunch runs the harvester wheels to keep the ball in
 * while pneumatics extended.
 */
public class IntakeDownForLaunch extends CommandBase
{
    private Harvester mHarvester;
    private Launcher mLauncher;

    public IntakeDownForLaunch(Harvester harvester, Launcher launcher)
    {
        mHarvester = harvester;
        mLauncher = launcher;
        addRequirements(mHarvester);
    }

    @Override
    public void initialize()
    {
        mHarvester.extendPneumatics();
    }

    @Override
    public void execute()
    {
        // give a gentle push to the ball towards launcher in case it gets stuck
        if (mLauncher.isBallPresent())
        {
            mHarvester.setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed/2);
        }
    }

    @Override
    public boolean isFinished()
    {
        return mHarvester.isHarvesterDown();
    }

    @Override
    public void end(boolean isInterrupted)
    {
        if (isInterrupted)
        {
            Logger.info("Command: IntakeDownWithWheels interrupted");
        }
        mHarvester.stopHarversterWheels();
        Logger.info("Command: IntakeDownWithWheels is ended");
    }
}
