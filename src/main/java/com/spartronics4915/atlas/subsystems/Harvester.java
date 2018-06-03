package com.spartronics4915.atlas.subsystems;

import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;
import com.spartronics4915.atlas.subsystems.LED.BlingState;
import com.spartronics4915.atlas.commands.HarvesterStopWheels;
import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.util.Util;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private DigitalInput mTopMagneticSwitch; //detects if intake is up: the physical switch is lower than the other one
    private DigitalInput mBottomMagneticSwitch; //detects if intake is down: the physical switch is higher than the other one

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
            
            outputToSmartDashboard();

            // This needs to go at the end. We *don't* set
            // m_initalized here (we only set it on failure).
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
        return !mBottomMagneticSwitch.get();
    }

    public boolean isHarvesterUp()
    {
        return !mTopMagneticSwitch.get();
    }

    public boolean areWheelsStopped()
    {
        System.out.println("Harvester wheel speed == " + mCollectionMotor.get());
        return mCollectionMotor.get() == 0.0;
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
        LED.getInstance().setBlingState(BlingState.RED);
        LED.getInstance().setBlingState(BlingState.FADING);
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
            // setDefaultCommand(new HarvesterStopWheels());
        }
    }

    public void stop()
    {
        setWheelSpeed(0.0);
        stopPneumatics(); //TODO: is this correct??
    }

    public String getHarvesterSolenoidState()
    {
        DoubleSolenoid.Value state = mHarvesterArms.get();
        if (state == DoubleSolenoid.Value.kOff)
            return "kOff";
        else if (state == DoubleSolenoid.Value.kReverse)
            return "kReverse";
        else if (state == DoubleSolenoid.Value.kForward)
            return "kForward";
        else
            return "Unexpected Error: check mHarvesterArms";
    }

    /**
     * SmartDashboard experience
     */
    public void outputToSmartDashboard()
    {
        // Update network tables for the launcher
        SmartDashboard.putNumber("mHarvesterCollectionMotorCurrentSpeed", mCollectionMotor.get());
        SmartDashboard.putBoolean("mHarvesterTopMagneticSwitchTriggered", mTopMagneticSwitch.get());
        SmartDashboard.putBoolean("mHarvesterBottomMagneticSwitchTriggered", mBottomMagneticSwitch.get());
        SmartDashboard.putBoolean("mHarvesterIsHarvesterDown", isHarvesterDown());
        SmartDashboard.putBoolean("mHarvesterIsHarvesterUp", isHarvesterUp());
        SmartDashboard.putString("mHarvesterSolenoidState", getHarvesterSolenoidState());
    }
}
