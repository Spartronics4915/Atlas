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
 * QuickTurnDrivetrain turns the drivetrain 180 degrees in a closed
 * loop mode using the IMU.
 */
public class QuickTurnDrivetrain extends Command implements PIDSource, PIDOutput
{

    private static final double kP = 0.02;
    private static final double kI = 0;
    private static final double kD = 0;
    private static final double kF = 0;
    private static final double kAllowedError = 10; // In degrees

    private Drivetrain mDrivetrain;
    private PIDController mPIDController;

    public QuickTurnDrivetrain()
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
        // Rotate current heading by 180 degrees
        mPIDController.setSetpoint(mDrivetrain.getIMUHeading().rotateBy(Rotation2d.fromDegrees(180)).getDegrees());

        // Actually start the controller
        mPIDController.enable();

        Logger.info("QuickTurnDrivetrain initialized");
    }

    @Override
    protected void execute()
    {
        mPIDController.enable();
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        disable();
        Logger.info("QuickTurnDrivetrain ended");
    }

    @Override
    protected void interrupted()
    {
        disable();
        Logger.info("QuickTurnDrivetrain interrupted");
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
        mDrivetrain.arcadeDrive(0, output);
    }
}
