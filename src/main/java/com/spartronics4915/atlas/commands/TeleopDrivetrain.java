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

    private static final double kDeadZone = 0.1;

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
        double left = OI.sDriveStick.getX() - OI.sDriveStick.getY();
        double right = OI.sDriveStick.getX() + OI.sDriveStick.getY();
        if (Math.abs(left) < kDeadZone) {
            left = 0;
        }
        if (Math.abs(right) > kDeadZone) {
            right = 0;
        }

        mDrivetrain.driveOpenLoop(
            Math.max(Math.min(left, 1), -1),
            Math.max(Math.min(right, 1), -1) // Values should be clamped between -1 and 1
        );
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
        Logger.info("TeleopDrivetrain interrupted");
    }
}
