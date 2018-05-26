
package com.spartronics4915.atlas;

import com.spartronics4915.atlas.subsystems.Drivetrain;
import com.spartronics4915.atlas.commands.TeleopDrivetrain;
import com.spartronics4915.atlas.subsystems.*;

import com.spartronics4915.util.CANProbe;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public class Robot extends IterativeRobot
{
    private Drivetrain mDrivetrain;
    private Launcher mLauncher;
    private Harvester mHarvester;
    private LED mLED;
    private OI mOI;

    private Command mAutoCommand;

    @Override
    public void robotInit()
    {
        CANProbe canProbe = CANProbe.getInstance();
        ArrayList<String> canReport = canProbe.getReport();
        Logger.notice("CANDevicesFound: " + canReport);
        int numDevices = canProbe.getCANDeviceCount();
        SmartDashboard.putString("CANBusStatus",
                numDevices == RobotMap.kNumCANDevices ? "OK"
                    : ("" + numDevices + "/" + RobotMap.kNumCANDevices));

        mDrivetrain = Drivetrain.getInstance();
        mLauncher = Launcher.getInstance();
        mHarvester = Harvester.getInstance();
        mLED = LED.getInstance();
        mOI = new OI(this); // make sure OI goes last
    }

    @Override
    public void robotPeriodic()
    {
        // This is invoked in both Autonomous and TeleOp, in
        // addition to the autonomousPeriodic and TeleOp periodic.
    }

    @Override
    public void autonomousInit()
    {
        Logger.notice("Autonomous disabled");
    }

    @Override
    public void autonomousPeriodic()
    {
        Logger.notice("autonomousPeriodic: Autonomous disabled");
    }

    @Override
    public void teleopInit()
    {
        if (mAutoCommand != null)
        {
            mAutoCommand.cancel();
            mAutoCommand = null;
        }
        else
        {
            Logger.error("can't cancel a null autonomous command.");
        }

        // update subsystem defaults from the network tables as needed
        mLauncher.updateFromSmartDashboard();

        new TeleopDrivetrain().start(); // Start arcade drive

        Logger.notice("teleop initalized.");
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
        mLauncher.outputToSmartDashboard();
        mHarvester.outputToSmartDashboard();
    }

    @Override
    public void testPeriodic()
    {
        LiveWindow.run();
    }

}
