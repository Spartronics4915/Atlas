package com.spartronics4915.atlas.subsystems;

import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;

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
 *      - TBD -- check IF ball is present before launching
 *  - Rewind 
 *      - requires the harvester to be extended -- check IF the extended limit switch is activated 
 *      - launchActivate to set(false) 
 *      - run the launcherWindingMotor UNTIL the rewind limit switch is activated 
 *      - TBD -- set timeout for the safety
 * system -- needs experimenting
 * 
 */
public class Launcher extends SpartronicsSubsystem
{
    // Launcher subsystem
    private static SpeedController mLauncherWindingMotor;    // winding motor
    private static DigitalInput mLauncherRewound;            // limit switch to detect IF rewound complete
    private static DoubleSolenoid mLauncherActivate;         // pneumatic for launching ball    
    private static AnalogInput mBallPresent;                 // sensor to detect presence of the ball
    private double mLauncherWindingMotorSpeed=0.5;           // IMPORTANT: test winding motor direction 

    public Launcher()
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
            mLauncherActivate = new DoubleSolenoid(kLaunchExtendSolenoidId, kLaunchRetractSolenoidId);
            mBallPresent = new AnalogInput(kBallPresentSensorId);

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
    
    public void stop()
    {
        // FIXME: Actually stop the motors
    }

    /** 
     * Winding motor controls 
     */
    public void startLauncherWindingMotor() {
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

    public double getLauncherWindingMotorSetSpeed() {
        return mLauncherWindingMotorSpeed;
    }

    public void setLauncherWindingMotorSpeed(double speed)
    {
        mLauncherWindingMotorSpeed = speed;
    }

    /**
     * Limit switch status -- returns true when pushed
     */
    public boolean isLauncherRewound() {
        return mLauncherRewound.get();
    }

    /**
     * Pneumatics to activate launcher: retract & extend & stop 
     *  Stop: Stops filling the cylinder. Will not retract it, but will allow it to be pushed back
     */
    public void launcherRetractSolenoid()
    {
        mLauncherActivate.set(DoubleSolenoid.Value.kReverse);
    }

    public void launcherExtendSolenoid() 
    {
        mLauncherActivate.set(DoubleSolenoid.Value.kForward);
    }

    public void launcherStopSolenoid() 
    {
        mLauncherActivate.set(DoubleSolenoid.Value.kForward);
    }
    

}
