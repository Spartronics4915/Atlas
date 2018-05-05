package com.spartronics4915.atlas.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.commands.StopCommand;
import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;
import com.spartronics4915.util.Rotation2d;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

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
    // Motors
    public SpeedController mLeftMotor;
    public SpeedController mRightMotor;

    //IMU
    public PigeonIMU mIMU;

    public Drivetrain()
    {
        
        try
        {
            // Initialize motors
            mLeftMotor = new Victor(RobotMap.kLeftDriveMotorId);
            mRightMotor = new Victor(RobotMap.kRightDriveMotorId);
            
            //Initialize IMU
            mIMU = new PigeonIMU(RobotMap.kDriveTrainIMUID);

            // This needs to go at the end. We *don't* set
            // mInitalized here (we only set it on faliure).
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
            setDefaultCommand(new StopCommand(this));
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

    public void driveOpenLoop(double left, double right)
    {
        mLeftMotor.set(left);
        mRightMotor.set(right);
    }
}
