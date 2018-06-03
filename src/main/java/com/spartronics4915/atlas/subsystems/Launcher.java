package com.spartronics4915.atlas.subsystems;

import com.spartronics4915.atlas.Logger;

import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.commands.StopCommand;
import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;
import com.spartronics4915.util.SpartIRSensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import com.spartronics4915.atlas.subsystems.LED;
import com.spartronics4915.atlas.subsystems.LED.BlingState;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The subsystem that controls the Launcher.
 *
 * Launcher will test if ball is present before launching. IMPORTANT: launcher
 * winder can only operate in one direction -- anything else will burn the
 * motor. Dashboard diagnostics need to run low speed test to determine correct
 * operational direction. 
 *  - Launch 
 *      - requires the harvester to be extended -- check IF the extended limit switch is activated 
 *          - do we want to automatically extend the harvester if it is closed? (and wait for limit switch?)
 *      - launchActivate to set(true) 
 *      - checks IF ball is present before launching
 *  - Rewind 
 *      - requires the harvester to be extended -- check IF the extended limit switch is activated 
 *      - launchActivate to set(false) 
 *      - run the launcherWindingMotor UNTIL the rewind limit switch is activated 
 *      - set timeout for the safety handled in the commands
 * 
 */
public class Launcher extends SpartronicsSubsystem
{
    private static Launcher sInstance = null;

    // Launcher subsystem
    private static Talon mLauncherWindingMotor;              // winding motor
    private static DigitalInput mLauncherRewound;            // limit switch to detect IF rewound complete
    private static DoubleSolenoid mLauncherActivate;         // pneumatic for launching ball    
    private double mLauncherWindingMotorSpeed = 0.5;         // IMPORTANT: test winding motor direction 
    private final double kLauncherWindingMaxSpeed = 0.75;    // Safety limit for the motor
    private SpartIRSensor mBallPresentSensor = null;         // sensor to detect presence of the ball


    // Subsystems are a singleton
    public static Launcher getInstance() 
    {
        if (sInstance == null) 
        {
            sInstance = new Launcher();
        }
        return sInstance;
    }

    private Launcher()
    {
        // Pretty much everything should go in the try block,
        // because certain initializations can throw exceptions
        // which we want to print, and because we want m_initalized
        // to be a correct value.
        try
        {
            // Initialize launcher components
            mLauncherWindingMotor = new Talon(RobotMap.kLauncherWindingMotorId);
            mLauncherRewound = new DigitalInput(RobotMap.klauncherRewoundSwitchId);
            mLauncherActivate = new DoubleSolenoid(RobotMap.kLaunchExtendSolenoidId, RobotMap.kLaunchRetractSolenoidId);
            mBallPresentSensor = new SpartIRSensor(RobotMap.kBallPresentSensorId);

            // update smartdashboard
            outputToSmartDashboard();
            
            // This needs to go at the end. We *don't* set
            // m_initalized here (we only set it on faliure).
            logInitialized(true);
        }
        catch (Exception e)
        {
            logException("Couldn't initialize Launcher", e);
            logInitialized(false);
        }
    }

    @Override
    public void initDefaultCommand()
    {
        if (isInitialized())
        {
            // setDefaultCommand(null); FIXME: Set a default command!
        }
    }
    
    /** 
     * Winding motor controls 
     */
    public void startLauncherWindingMotor() 
    {
        mLauncherWindingMotor.set(mLauncherWindingMotorSpeed);
    }

    public void startLauncherWindingMotor(double speed)
    {
        mLauncherWindingMotor.set(speed);
    }
     
    public void stopLauncherWindingMotor()
    {
        mLauncherWindingMotor.set(0.0);
    }

    public double getLauncherWindingMotorCurrentSpeed()
    {
        return mLauncherWindingMotor.get();
    }

    public double getLauncherWindingMotorSetSpeed() 
    {
        return mLauncherWindingMotorSpeed;
    }

    public void setLauncherWindingMotorSpeed(double speed)
    {
        mLauncherWindingMotorSpeed = speed;
    }

    /**
     * Limit switch status -- returns true when pushed
     */
    public boolean isLauncherRewound() 
    {
        return mLauncherRewound.get();
    }

    /**
     * Pneumatics to activate launcher: retract & extend & stop 
     *  Stop: Stops filling the cylinder. Will not retract it, but will allow it to be pushed back
     */
    // prepares for winding
    public void launcherPrepareForWinding()
    {
        mLauncherActivate.set(DoubleSolenoid.Value.kReverse);
        LED.getInstance().setBlingState(BlingState.YELLOW);
    }

    // Extend launches the ball
    public void launcherLaunchBall() 
    {
        mLauncherActivate.set(DoubleSolenoid.Value.kForward);
        LED.getInstance().setBlingState(BlingState.BLUE);
    }

    public String getLauncherSolenoidState()
    {
        DoubleSolenoid.Value state = mLauncherActivate.get();
        if (state == DoubleSolenoid.Value.kOff)
            return "kOff";
        else if (state == DoubleSolenoid.Value.kReverse)
            return "ready to wind";
        else if (state == DoubleSolenoid.Value.kForward)
            return "launched";
        else
            return "Unexpected Error: check launcherSolenoid";
    }
    
    /** 
     * Ball sensor methods for detecting ball & sensor distances
     */
    public boolean isBallPresent()
    {
        return mBallPresentSensor.isTargetAcquired();
    }

    public double getBallRangeSensorDistance()
    {
        return mBallPresentSensor.getDistance();
    }

    /**
     * SmartDashboard experience
     */
    public void outputToSmartDashboard()
    {
        // Update network tables for the launcher
        SmartDashboard.putNumber("mLauncherWindingMotorDefaultSpeed", getLauncherWindingMotorSetSpeed());
        SmartDashboard.putNumber("mLauncherWindingMotorCurrentSpeed", mLauncherWindingMotor.get());
        SmartDashboard.putBoolean("mLauncherRewoundSwitchTriggered", mLauncherRewound.get());
        SmartDashboard.putString("mLauncherSolenoidState", getLauncherSolenoidState());
        SmartDashboard.putBoolean("mBallPresent", isBallPresent());
        SmartDashboard.putNumber("mDistanceToBall", mBallPresentSensor.getDistance());
    }

    public void updateFromSmartDashboard()
    {
        // extract defaults from network tables
        Double speed = SmartDashboard.getNumber("mLauncherWindingMotorDefaultSpeed", mLauncherWindingMotorSpeed);
        if (speed > kLauncherWindingMaxSpeed)
        {
            Logger.info("Launcher: updateFromSmartDashboard -- speed > allowed max speed");
            speed = kLauncherWindingMaxSpeed;
        }
        setLauncherWindingMotorSpeed(speed);
    }

    public double readFromSmartDashboard()
    {
        // extract changes from network tables but don't store it
        return SmartDashboard.getNumber("mLauncherWindingMotorDefaultSpeed", mLauncherWindingMotorSpeed);
    }
}
