package com.spartronics4915.atlas.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.commands.StopCommand;
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
            
            //Initialize IMU
            mIMU = new PigeonIMU(RobotMap.kDriveTrainIMUID);

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
            setDefaultCommand(new StopCommand());
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

    /*
    Although we could return the same DifferentialDrive here,
    reuse could cause bugs (especially with inversion or deadbands).
    This also allows us to have different settings for different
    commands.

    One could argue that this allows multiple differential drives
    to fight, but that was already a possibility with other methods,
    and we defer stopping conflicts like this to WPILib's scheduler
    (see the Command.requires method).
    */
    public DifferentialDrive getNewDifferentialDrive()
    {
        DifferentialDrive diffDrive = new DifferentialDrive(mLeftMotor, mRightMotor);
        diffDrive.setMaxOutput(1); 

        return diffDrive;
    }
}
