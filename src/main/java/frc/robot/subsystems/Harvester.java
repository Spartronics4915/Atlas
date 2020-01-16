package frc.robot.subsystems;

import frc.robot.subsystems.SpartronicsSubsystem;
import frc.robot.subsystems.LED.BlingState;
import frc.robot.Constants.RobotMapConstants;
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
	private DigitalInput mTopMagneticSwitch;    //detects if intake is up: the physical switch is lower than the other one
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
    @Override
    public void periodic()
    {
        mCollectionMotor.set(mCollectionMotor.get());
    }


    private Harvester()
    {

        // Pretty much everything should go in the try block,
        // because certain initializations can throw exceptions
        // which we want to print, and because we want m_initalized
        // to be a correct value.
        try
        {
        	mHarvesterArms = new DoubleSolenoid(RobotMapConstants.kHarvesterExtendSolenoidId, RobotMapConstants.kHarvesterRetractSolenoidId);
        	mCollectionMotor = new Talon(RobotMapConstants.kHarvesterCollectionMotorId);
        	mTopMagneticSwitch = new DigitalInput(RobotMapConstants.kHarvesterTopMagneticSwitchId);
            mBottomMagneticSwitch = new DigitalInput(RobotMapConstants.kHarvesterBottomMagneticSwitchId);

            // Read and set intake state
            if (isHarvesterDown())
            {
                extendPneumatics();
            }
            else
            {
                retractPneumatics();
            }

            outputToSmartDashboard();

            // This needs to go at the end. We *don't* set
            // m_initalized here (we only set it on failure).
            logInitialized(true);
        }
        catch (final Exception e)
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
        mHarvesterArms.set(DoubleSolenoid.Value.kForward);
        LED.getInstance().setBlingState(BlingState.SPARTRONICS_FADE);
    }

    public void retractPneumatics()
    {
        mHarvesterArms.set(DoubleSolenoid.Value.kReverse);
        LED.getInstance().setBlingState(BlingState.BLUE);
        LED.getInstance().setBlingState(BlingState.FLASHING);
    }

    public void stopPneumatics()
    {
        mHarvesterArms.set(DoubleSolenoid.Value.kOff);
        LED.getInstance().setBlingState(BlingState.RED);
        LED.getInstance().setBlingState(BlingState.FADING);
    }

    public void setWheelSpeed(final double speed)
    {
        mCollectionMotor.set(speed);
    }

    public void stopHarversterWheels()
    {
        mCollectionMotor.set(0.0);
    }

    public void stop()
    {
        stopHarversterWheels();
        stopPneumatics(); //TODO: is this correct??
    }

    // save the internal state of the collection motors
    public void runHarvesterWheels()
    {
        // only toggle if harvester is down
        if (this.isHarvesterDown())
        {
            setWheelSpeed(RobotMapConstants.kHarvesterIntakeWheelSpeed);
        }
    }

    public String getHarvesterSolenoidState()
    {
        final DoubleSolenoid.Value state = mHarvesterArms.get();
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
