package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class StopCommand extends Command
{

    private Drivetrain mDrivetrain;

    public StopCommand(Drivetrain drivetrain)
    {
        mDrivetrain = drivetrain;
        requires(drivetrain);
    }

    protected void initialize()
    {
        mDrivetrain.m_logger.info("StopCommand initialized");
    }

    protected void execute()
    {
        mDrivetrain.stop();
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
        mDrivetrain.m_logger.info("StopCommand ended");
    }

    protected void interrupted()
    {
        mDrivetrain.m_logger.info("StopCommand interrupted");
    }
}
