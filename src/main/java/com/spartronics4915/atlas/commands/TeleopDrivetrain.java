package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.OI;
import com.spartronics4915.atlas.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        mDrivetrain.driveOpenLoop(
            Math.max(Math.min(OI.sDriveStick.getX() - OI.sDriveStick.getY(), 1), -1),
            Math.max(Math.min(OI.sDriveStick.getX() + OI.sDriveStick.getY(), 1), -1)
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
