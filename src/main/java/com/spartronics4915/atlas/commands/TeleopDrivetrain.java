package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.OI;
import com.spartronics4915.atlas.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * TeleopDrivetrain runs the drivetrain using a drive stick.
 */
public class TeleopDrivetrain extends Command
{

    private Drivetrain mDrivetrain;
    private DifferentialDrive mDifferentialDrive;

    private static final double kDeadband = 0.1;

    public TeleopDrivetrain()
    {
        mDrivetrain = Drivetrain.getInstance();
       
        mDifferentialDrive = mDrivetrain.getNewDifferentialDrive();
        mDifferentialDrive.setDeadband(kDeadband);

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
        mDifferentialDrive.arcadeDrive(OI.sDriveStick.getY(), OI.sDriveStick.getX());
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
