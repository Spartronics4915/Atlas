package com.spartronics4915.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.spartronics4915.atlas.Logger;
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
        Logger.info("CmdGroup: ActivateLauncher -- LED");
        addParallel(new SetBlingStateCommand(mLed, BlingState.SPARTRONICS_FADE));
        addSequential(new IntakeDownWithWheels());
        addSequential(new ActivateLauncher());
        addSequential(new WindLauncher());
    }
}
