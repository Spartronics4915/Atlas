package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.subsystems.LED;
import com.spartronics4915.atlas.subsystems.LED.BlingState;

import edu.wpi.first.wpilibj.command.Command;

/**
 * The way to switch the BlingState on the Arduino
 */
public class SetBlingStateCommand extends Command
{

    private LED mLED;
    private BlingState mBlingState;

    public SetBlingStateCommand(LED led_system, BlingState blingState)
    {
        mLED = led_system;
        requires(led_system);
        mBlingState = blingState;
    }

    protected void initialize()
    {
        Logger.info("SetBlingStateCommand initialized");
        mLED.setBlingState(mBlingState);
    }
    
    protected void execute()
    {
        //doesn't need to do anything
    }

    protected boolean isFinished()
    {
        return true;
    }

    protected void end()
    {
        Logger.info("SetBlingStateCommand ended");
    }

    protected void interrupted()
    {
        Logger.info("SetBlingStateCommand interrupted");
    }
}
