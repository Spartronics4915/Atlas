package com.spartronics4915.atlas.subsystems;

import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;
import com.spartronics4915.atlas.subsystems.LED.BlingState;
import com.spartronics4915.atlas.commands.HarvesterStopWheels;
import com.spartronics4915.atlas.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

/**
 * The subsystem that controls the Harvester.
 *
 * A note on motor naming:
 * We're doing port and starboard again. That's all that really matters here.s
 * These directions are relative to the front of the robot, which removes as
 * much possible ambiguity as you can with directions (in this context).
 * Port and starboard refer respectively to left and right, relative to the
 * front of the robot.
 */
public class Harvester extends SpartronicsSubsystem
{
	
	private DoubleSolenoid mHarvesterArms;
	private SpeedController mCollectionMotor;
	private DigitalInput mTopMagneticSwitch;
    private DigitalInput mBottomMagneticSwitch;
    private boolean isStopWheelsRunning = false;

    private static Harvester sInstance = null;

    public static Harvester getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new Harvester();
        }
        return sInstance;
    }

    private Harvester()
    {

        // Pretty much everything should go in the try block,
        // because certain initializations can throw exceptions
        // which we want to print, and because we want m_initalized
        // to be a correct value.
        try
        {
        	mHarvesterArms = new DoubleSolenoid(RobotMap.kHarvesterExtendSolenoidId, RobotMap.kHarvesterRetractSolenoidId);
        	mCollectionMotor = new Talon(RobotMap.kHarvesterCollectionMotorId);
        	mTopMagneticSwitch = new DigitalInput(RobotMap.kHarvesterTopMagneticSwitchId);
            mBottomMagneticSwitch = new DigitalInput(RobotMap.kHarvesterBottomMagneticSwitchId);
        	
            // This needs to go at the end. We *don't* set
            // m_initalized here (we only set it on faliure).
            logInitialized(true);
        }
        catch (Exception e)
        {
            logException("Couldn't initialize harvester", e);
            logInitialized(false);
        }
    }

    /*
    FROM OLD 2014 CODE:
    if Harvester is fully up, Hardware returns
        FALSE for BOTTOM magnetic switch
        TRUE for TOP magnetic switch
    if the Harvester is fully down/out/extended, Hardware returns:
        TRUE for BOTTOM magnetic switch
        FALSE for TOP magnetic switch
    
    if the Harvester is in-between, Hardware returns:
        TRUE FOR BOTH
    */

    public boolean isHarvesterDown()
    {
        return !mBottomMagneticSwitch.get(); // TODO: is this correct
    }

    public boolean isHarvesterUp()
    {
        return !mTopMagneticSwitch.get(); //TODO:  is this correct
    }

    public boolean getIsStopWheelsRunning()
    {
        return isStopWheelsRunning;
    }

    public void setIsStopWheelsRunning(boolean b)
    {
        isStopWheelsRunning = b;
    }

    public void extendPneumatics()
    {
        mHarvesterArms.set(DoubleSolenoid.Value.kForward); //TODO: is this correct
        LED.getInstance().setBlingState(BlingState.SPARTRONICS_FADE);
    }

    public void retractPneumatics()
    {
        mHarvesterArms.set(DoubleSolenoid.Value.kReverse);  //TODO: is this correct
        LED.getInstance().setBlingState(BlingState.BLUE);
        LED.getInstance().setBlingState(BlingState.FLASHING);
    }

    public void stopPneumatics()
    {
        mHarvesterArms.set(DoubleSolenoid.Value.kOff);  //TODO: is this correct
        LED.getInstance().setBlingState(BlingState.RESET);

    }

    public void setWheelSpeed(double speed)
    {
        mCollectionMotor.set(speed); // TODO: is this correct
    }

    @Override
    public void initDefaultCommand()
    {
        if (isInitialized())
        {
            setDefaultCommand(new HarvesterStopWheels());
        }
    }

    public void stop()
    {
        setWheelSpeed(0.0);
        stopPneumatics(); //TODO: is this correct??
    }
}
