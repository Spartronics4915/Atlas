package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Launcher;

/**
 * CommandGroup to actuate wind launcher
 */
public class WindLauncherCommandGroup extends SequentialCommandGroup
{
    public WindLauncherCommandGroup(Harvester harvester, Launcher launcher, LED led)
    {
        addCommands(
            // FIXME add LED state
            // addSequential(new SetBlingStateCommand(mLed, BlingState.FAST_FLASHING));
            // ensure harvester is extended
            new IntakeDown(harvester, launcher),
            new WindLauncher(harvester, launcher)
        );
    }
}
