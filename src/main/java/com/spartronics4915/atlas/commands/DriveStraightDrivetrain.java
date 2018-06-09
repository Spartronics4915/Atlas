package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.OI;
import com.spartronics4915.atlas.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * DriveStraightDrivetrain runs the drivetrain straight forward,
 * with open loop forward velocity, and closed loop turning.
 */
public class DriveStraightDrivetrain extends Command implements PIDSource, PIDOutput
{

    private static final double kP = 0.5;
    private static final double kI = 0;
    private static final double kD = 15;
    private static final double kF = 0;
    private static final double kAllowedError = 0; // In degrees

    private Drivetrain mDrivetrain;
    private PIDController mPIDController;

    public DriveStraightDrivetrain()
    {
        mDrivetrain = Drivetrain.getInstance();
        requires(mDrivetrain);

        mPIDController = new PIDController(kP, kI, kD, kF, this, this);
        mPIDController.setOutputRange(-1, 1);
        mPIDController.setInputRange(0, 360); // Guaranteed by the Rotation2d class
        mPIDController.setAbsoluteTolerance(kAllowedError);
        mPIDController.setContinuous();
    }

    @Override
    protected void initialize()
    {
        // Stay at the same heading
        mPIDController.setSetpoint(mDrivetrain.getIMUHeading().getDegrees());

        // Actually start the controller
        mPIDController.enable();

        Logger.info("DriveStraightDrivetrain initialized");
    }

    @Override
    protected void execute()
    {
        mPIDController.enable();

        SmartDashboard.putNumber("IMU Heading", mDrivetrain.getIMUHeading().getDegrees());
    }

    @Override
    protected boolean isFinished()
    {
        if (Math.abs(OI.sDriveStick.getX()) > 0.3 || Math.abs(OI.sDriveStick.getY()) > 0.3) {
            return true;
        }
        return false;
    }

    @Override
    protected void end()
    {
        disable();
        Logger.info("DriveStraightDrivetrain ended");
    }

    @Override
    protected void interrupted()
    {
        disable();
        Logger.info("DriveStraightDrivetrain interrupted");
    }

    private void disable()
    {
        if (mPIDController.isEnabled())
        {
            mPIDController.reset();
            mPIDController.disable();
            assert(!mPIDController.isEnabled());
        }
        mDrivetrain.stop();
    }

    // PIDSource -----------------------------------------------------------------------
    @Override
    public void setPIDSourceType(PIDSourceType pidSource)
    {
        if (pidSource != PIDSourceType.kDisplacement)
        {
            Logger.error("DriveStraightDrivetrain only supports kDisplacement");
        }
    }

    @Override
    public PIDSourceType getPIDSourceType()
    {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet()
    {
        return mDrivetrain.getIMUHeading().getDegrees();
    }

    // PIDOutput -----------------------------------------------------------------------
    @Override
    public void pidWrite(double output)
    {
        mDrivetrain.arcadeDrive(OI.sDriveStick.getZ(), output);
    }
}
