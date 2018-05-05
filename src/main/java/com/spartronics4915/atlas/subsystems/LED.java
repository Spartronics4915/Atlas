package com.spartronics4915.atlas.subsystems;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.subsystems.SpartronicsSubsystem;

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
    // Port motors

    // Starboard motors

    public LED()
    {

        // Pretty much everything should go in the try block,
        // because certain initializations can throw exceptions
        // which we want to print, and because we want m_initalized
        // to be a correct value.
        try
        {
            // Initialize motors

            // This needs to go at the end. We *don't* set
            // m_initalized here (we only set it on faliure).
            logInitialized(true);
        }
        catch (Exception e)
        {
            logException("Couldn't initialize LED", e);
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
}
