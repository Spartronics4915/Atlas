package frc.robot.commands;

import frc.robot.Logger;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LED.BlingState;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * The way to switch the BlingState on the Arduino.
 */
public class SetBlingStateCommand extends CommandBase
{

    private LED mLED;
    private BlingState mBlingState;

    public SetBlingStateCommand(LED led_system, BlingState blingState)
    {
        mLED = led_system;
        addRequirements(mLED);
        mBlingState = blingState;
    }

    @Override
    public void initialize()
    {
        Logger.info("SetBlingStateCommand initialized: " + mBlingState.toString());
        mLED.setBlingState(mBlingState);
    }

    @Override
    public void execute()
    {
        //doesn't need to do anything
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }

    @Override
    public void end(boolean isInterrupted)
    {
    }
}
