package com.spartronics4915.atlas.subsystems;

import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * The subsystem that controls the LED.
 * Communicating to the Arduino by giving
 * a byte using Serial to the wanted blingstate to be
 * read by the Arduino to show the given Bling style wanted.
 * 
 * Based off the Arduino code under Spartronics4915/Bling/Roborio_functionality
 */
public class LED extends SpartronicsSubsystem
{
    private SerialPort mBling;


    private static LED sInstance = null;

    public static LED getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new LED();
        }
        return sInstance;
    }
/**
 * This enum is giving the possible styles we can have the Arduino express.
 */
    public enum BlingState
    {
    		PURPLE,
    		DEFAULT,
    		BLUE,
    		YELLOW,
    		RED,
    		GREEN,
    		SPARTRONICS_FADE,
    		FADING,
    		FLASHING,
    		FAST_FLASHING,
    		RESET
    }
    /**
     * this will change the style of the bling code we wanted based on input from the driver.
     */
    private final byte[] kPurple = "a".getBytes();
    private final byte[] kDefault = "0".getBytes();
    private final byte[] kBlue = "1".getBytes();
    private final byte[] kYellow = "2".getBytes();
    private final byte[] kRed = "3".getBytes();
    private final byte[] kGreen = "4".getBytes();
    private final byte[] kSpartronics_Fade = "5".getBytes();
    private final byte[] kFading = "6".getBytes();
    private final byte[] kFlashing = "9".getBytes();
    private final byte[] kFast_Flashing = "7".getBytes(); 
    private final byte[] kReset = "8".getBytes();
    
    private LED()
    {
        try
        {
        	mBling = new SerialPort(9600, SerialPort.Port.kUSB);

            logInitialized(true);
        }
        catch (Exception e)
        {
            logException("Couldn't initialize LED", e);
            logInitialized(false);
        }
    }
    /**
     * This will go through what we want the bling to do and express that style of bling.
     */
    public void setBlingState(BlingState blingState)
    {
        if (!isInitialized()) 
        {
            return;
        }
        byte[] message = kSpartronics_Fade;
        switch(blingState)
        {
        case DEFAULT:
        		message = kDefault;
        		break;
        case YELLOW:
        		message = kYellow;
        		break;
        case PURPLE:
        		message = kPurple;
        		break;
        case BLUE:
        		message = kBlue;
        		break;
        case RED:
        		message = kRed;
        		break;
        case GREEN:
        		message = kGreen;
        		break;
        case SPARTRONICS_FADE:
        		message = kSpartronics_Fade;
        		break;
        case FADING:
        		message = kFading;
        		break;
        case FLASHING:
        		message = kFlashing;
        		break;
        case FAST_FLASHING:
        		message = kFast_Flashing;
        		break;
        case RESET:
        		message = kReset;
        		break;
        }
        mBling.write(message, message.length);
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
}
