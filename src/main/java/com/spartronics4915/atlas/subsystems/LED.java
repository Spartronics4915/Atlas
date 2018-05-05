package com.spartronics4915.atlas.subsystems;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * The subsystem that controls the LED.
 *
 * A note on motor naming:
 * We're doing port and starboard again. That's all that really matters here.s
 * These directions are relative to the front of the robot, which removes as
 * much possible ambiguity as you can with directions (in this context).
 * Port and starboard refer respectively to left and right, relative to the
 * front of the robot.
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

    private enum BlingState
    {

    }

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

    public void setBlingState(BlingState blingState)
    {
        switch(blingState)
        {
            
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
}
