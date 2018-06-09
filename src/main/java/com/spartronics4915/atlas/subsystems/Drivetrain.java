package com.spartronics4915.atlas.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.commands.DriveStraightDrivetrain;
import com.spartronics4915.atlas.commands.StopCommand;
import com.spartronics4915.atlas.commands.TeleopDrivetrain;
import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;
import com.spartronics4915.util.Rotation2d;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The subsystem that controls the Drivetrain.
 *
 * A note on motor naming:
 * We're doing port and starboard again. That's all that really matters here.s
 * These directions are relative to the front of the robot, which removes as
 * much possible ambiguity as you can with directions (in this context).
 * Port and starboard refer respectively to left and right, relative to the
 * front of the robot.
 */
public class Drivetrain extends SpartronicsSubsystem
{
    private static Drivetrain sInstance = null;

    private DifferentialDrive mDifferentialDrive;

    // Motors
    public Victor mLeftMotor;
    public Victor mRightMotor;

    // IMU
    public PigeonIMU mIMU;

    public static Drivetrain getInstance() {
        if (sInstance == null)
        {
            sInstance = new Drivetrain();
        }
        return sInstance;
    }

    private Drivetrain()
    {
        try
        {
            // Initialize motors
            mLeftMotor = new Victor(RobotMap.kLeftDriveMotorId);
            mRightMotor = new Victor(RobotMap.kRightDriveMotorId);
            
            // Reverse motors
            mLeftMotor.setInverted(true);
            mRightMotor.setInverted(true);

            //Initialize IMU
            mIMU = new PigeonIMU(RobotMap.kDriveTrainIMUID);

            // Setup the differential drive
            mDifferentialDrive = new DifferentialDrive(mLeftMotor, mRightMotor);

            logInitialized(true);
        }
        catch (Exception e)
        {
            logException("Couldn't initialize Drivetrain", e);
            logInitialized(false);
        }
    }

    @Override
    public void initDefaultCommand()
    {
        if (isInitialized())
        {
            setDefaultCommand(new TeleopDrivetrain());
        }
    }

    public void stop()
    {
        mLeftMotor.set(0);
        mRightMotor.set(0);
    }

    public Rotation2d getIMUHeading()
    {
        double[] ypr = new double[3];
        mIMU.getYawPitchRoll(ypr);
        return Rotation2d.fromDegrees(ypr[0]);
    }

    public void arcadeDrive(double speed, double rotation)
    {
        mDifferentialDrive.arcadeDrive(speed, rotation);
    }
}
