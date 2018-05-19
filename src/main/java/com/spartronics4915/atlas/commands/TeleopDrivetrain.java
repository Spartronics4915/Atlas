package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * TeleopDrivetrain runs the drivetrain using a drive stick.
 */
public class TeleopDrivetrain extends Command
{

    private Drivetrain mDrivetrain;

    public TeleopDrivetrain()
    {
        mDrivetrain = Drivetrain.getInstance();
        requires(mDrivetrain);
    }

    @Override
    protected void initialize()
    {
        Logger.info("TeleopDrivetrain initialized");
    }

    @Override
    protected void execute()
    {
        mDrivetrain.stop();
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        Logger.info("TeleopDrivetrain ended");
    }

    @Override
    protected void interrupted()
    {
        Logger.info("TeleopDrivetrain interrupted");
    }
}
