package com.spartronics4915.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.spartronics4915.atlas.commands.SetBlingStateCommand;
import com.spartronics4915.atlas.subsystems.LED;
import com.spartronics4915.atlas.subsystems.LED.BlingState;
/**
 * CommandGroup to actuate wind launcher
 */
public class ActivateLauncherCommandGroup extends CommandGroup
{
    public ActivateLauncherCommandGroup(LED mLed)
    {
        addSequential(new SetBlingStateCommand(mLed, BlingState.SPARTRONICS_FADE));
        // TODO: ensure harvester is extended
        // addSequential(new ExtendHarvester());
        addSequential(new ActivateLauncher());
    }
}
