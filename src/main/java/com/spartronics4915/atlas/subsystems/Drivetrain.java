package com.spartronics4915.atlas.subsystems;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.commands.StopCommand;

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

    // This is public to stop logger proliferation by giving Commands a logger
    public Logger m_logger;

    /*
     * FIXME: I have the motors set up as if our drivetrain has two port
     * and two starboard motors, each side connected together. This assumes
     * that the drivetrain will look like that, because it's just an example
     * of how to use the new CANTalonFactory and set up these motors.
     * We don't know what our drivetrain will look like. Fix the motors
     * when we do.
     */

    // Port motors

    // Starboard motors

    public Drivetrain()
    {
        m_logger = new Logger("Drivetrain", Logger.Level.DEBUG);

        // Pretty much everything should go in the try block,
        // because certain initializations can throw exceptions
        // which we want to print, and because we want m_initalized
        // to be a correct value.
        try
        {
            // Initialize motors

            // This needs to go at the end. We *don't* set
            // m_initalized here (we only set it on faliure).
            m_logger.info("initialized successfully");
        }
        catch (Exception e)
        {
            m_logger.exception(e, false);
            m_initialized = false;
        }
    }

    @Override
    public void initDefaultCommand()
    {
        if (initialized())
        {
            setDefaultCommand(new StopCommand(this));
        }
    }

    @Override
    public void validate()
    {
    }

    public void stop()
    {
        // FIXME: Actually stop the motors
    }
}
