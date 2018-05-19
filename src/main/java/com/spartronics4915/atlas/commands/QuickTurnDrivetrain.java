package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.subsystems.Drivetrain;
import com.spartronics4915.util.Rotation2d;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * QuickTurnDrivetrain runs the drivetrain using a drive stick.
 */
public class QuickTurnDrivetrain extends Command implements PIDSource, PIDOutput
{

    private static final float kP = 1;
    private static final float kI = 0;
    private static final float kD = 0;
    private static final float kF = 0;
    private static final float kAllowedError = 3; // In degrees

    private Drivetrain mDrivetrain;
    private PIDController mPIDController;

    public QuickTurnDrivetrain()
    {
        mDrivetrain = Drivetrain.getInstance();
        requires(mDrivetrain);

        mPIDController = new PIDController(kP, kI, kD, kF, this, this);
        mPIDController.setOutputRange(-1, 1);
        mPIDController.setInputRange(0, 360); // Guranteed by the Rotation2d class
        mPIDController.setAbsoluteTolerance(kAllowedError);

        mPIDController.setSetpoint(mDrivetrain.getIMUHeading().rotateBy(Rotation2d.fromDegrees(180)).getDegrees());
    }

    @Override
    protected void initialize()
    {
        mPIDController.enable();
        Logger.info("QuickTurnDrivetrain initialized");
    }

    @Override
    protected void execute()
    {
        SmartDashboard.putNumber("IMU Value", mDrivetrain.getIMUHeading().getDegrees());
        SmartDashboard.putNumber("PID Setpoint", mPIDController.getSetpoint());
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        if (mPIDController.isEnabled())
        {
            mPIDController.reset();
            assert(!mPIDController.isEnabled());
        }
        mDrivetrain.stop();
        Logger.info("QuickTurnDrivetrain ended");
    }

    @Override
    protected void interrupted()
    {
        Logger.info("QuickTurnDrivetrain interrupted");
    }

    // PIDSource -----------------------------------------------------------------------
    @Override
    public void setPIDSourceType(PIDSourceType pidSource)
    {
        if (pidSource != PIDSourceType.kDisplacement)
        {
            Logger.error("QuickTurnDrivetrain only supports kDisplacement");
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
        mDrivetrain.driveOpenLoop(output, -output);
    }
}
