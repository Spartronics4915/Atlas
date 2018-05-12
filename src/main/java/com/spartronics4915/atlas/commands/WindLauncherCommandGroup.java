package com.spartronics4915.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * CommandGroup to actuate wind launcher
 */
public class WindLauncherCommandGroup extends CommandGroup
{
    public WindLauncherCommandGroup()
    {
        // TODO: ensure harvester is extended
        // addSequential(new ExtendHarvester());
        addSequential(new WindLauncher());
    }
}
