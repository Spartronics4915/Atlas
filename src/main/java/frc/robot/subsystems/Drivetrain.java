package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.Constants.RobotMapConstants;
import frc.robot.subsystems.SpartronicsSubsystem;
import frc.util.Rotation2d;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The subsystem that controls the Drivetrain.
 *
 * A note on motor naming:
 * We're doing port and starboard again. That's all that really matters here.
 * These directions are relative to the front of the robot, which removes as
 * much possible ambiguity as you can with directions (in this context).
 * Port and starboard refer respectively to left and right, relative to the
 * front of the robot.
 */
public class Drivetrain extends SpartronicsSubsystem
{
    private static Drivetrain sInstance = null;

    private DifferentialDrive mDifferentialDrive;

    // CTRE does a pass-by-reference thing, so we might as well not allocate a new array every time
    private double[] yawPitchRoll = new double[3];

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
            mLeftMotor = new Victor(RobotMapConstants.kLeftDriveMotorId);
            mRightMotor = new Victor(RobotMapConstants.kRightDriveMotorId);

            // Reverse motors
            mLeftMotor.setInverted(true);
            mRightMotor.setInverted(true);

            //Initialize IMU
            mIMU = new PigeonIMU(RobotMapConstants.kDriveTrainIMUID);

            // Do a song and dance to make the IMU work
            // FIXME imu clearStickyFaults is not supported!?
            // mIMU.clearStickyFaults(0); // 0 timeout means no timeout

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
    public void periodic()
    {
        SmartDashboard.putNumber("IMU Heading", this.getIMUHeading().getDegrees());
        SmartDashboard.putNumber("rightMotor Speed", mRightMotor.get());
        SmartDashboard.putNumber("leftMotor Speed", mLeftMotor.get());
    }

    public void stop()
    {
        mLeftMotor.set(0);
        mRightMotor.set(0);
    }

    public Rotation2d getIMUHeading()
    {
        mIMU.getYawPitchRoll(yawPitchRoll);
        SmartDashboard.putString("YawPitchRoll", "[0]: "+yawPitchRoll[0]+"; [1]: "+yawPitchRoll[1]+"; [2]: "+yawPitchRoll[2]+"; Fused: "+mIMU.getFusedHeading());
        return Rotation2d.fromDegrees(yawPitchRoll[0]);
    }

    public void arcadeDrive(double speed, double rotation)
    {
        mDifferentialDrive.arcadeDrive(speed, rotation);
    }
}
