package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Logger;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Launcher;
/**
 * CommandGroup to actuate wind launcher
 */
public class ActivateLauncherCommandGroup extends SequentialCommandGroup
{
    public ActivateLauncherCommandGroup(Harvester harvester, Launcher launcher, LED led)
    {
        Logger.info("CmdGroup: ActivateLauncher -- LED");
        // FIXME Add LED controls
        // addParallel(new SetBlingStateCommand(mLed, BlingState.SPARTRONICS_FADE));
        addCommands(
            new IntakeDownWithWheels(harvester),
            new ActivateLauncher(harvester, launcher).withTimeout(2.0),
            new WindLauncher(harvester, launcher).withTimeout(6.0)
        );
    }
}
