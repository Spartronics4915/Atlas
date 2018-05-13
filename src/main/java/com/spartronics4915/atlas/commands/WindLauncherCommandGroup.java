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
        // TODO: ensure harvester is extended
        // addSequential(new ExtendHarvester());
        addSequential(new WindLauncher(mLed, BlingState.FAST_FLASHING));
    }
}
