package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.OI;
import com.spartronics4915.atlas.subsystems.Drivetrain;

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
        // getZ is the throttle
        mDrivetrain.arcadeDrive(OI.sDriveStick.getY()*OI.getScaledThrottle(), OI.sDriveStick.getX()*-1); // Steering is reversed!
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        mDrivetrain.stop();
        Logger.info("TeleopDrivetrain ended");
    }

    @Override
    protected void interrupted()
    {
        mDrivetrain.stop();
        Logger.info("TeleopDrivetrain interrupted");
    }
}
