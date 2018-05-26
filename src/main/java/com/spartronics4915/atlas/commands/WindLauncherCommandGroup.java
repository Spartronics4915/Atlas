package com.spartronics4915.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.spartronics4915.atlas.subsystems.LED;
import com.spartronics4915.atlas.subsystems.LED.BlingState;

/**
 * CommandGroup to actuate wind launcher
 */
public class WindLauncherCommandGroup extends CommandGroup
{
    public WindLauncherCommandGroup(LED mLed)
    {
        addSequential(new SetBlingStateCommand(mLed, BlingState.FAST_FLASHING));
        // ensure harvester is extended
        addSequential(new IntakeDown());
        addSequential(new WindLauncher());
    }
}
