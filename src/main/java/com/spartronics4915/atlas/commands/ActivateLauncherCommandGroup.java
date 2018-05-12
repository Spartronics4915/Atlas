package com.spartronics4915.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * CommandGroup to actuate wind launcher
 */
public class ActivateLauncherCommandGroup extends CommandGroup
{
    public ActivateLauncherCommandGroup()
    {
        // TODO: ensure harvester is extended
        // addSequential(new ExtendHarvester());
        addSequential(new ActivateLauncher());
    }
}
