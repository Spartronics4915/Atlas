
package com.spartronics4915.atlas;

import java.util.ArrayList;

import com.spartronics4915.atlas.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot
{

    public Logger mLogger;

    private Drivetrain mDrivetrain;
    private Launcher mLauncher;
    private Harvester mHarvester;
    private LED mLED;
    private OI m_oi;

    private Command mAutoCommand;

    @Override
    public void robotInit()
    {
        mLogger = new Logger("Robot", Logger.Level.DEBUG);
        mDrivetrain = new Drivetrain();
        mLauncher = new Launcher();
        mHarvester = new Harvester();
        mLED = new mLED();
        m_oi = new OI(this); // make sure OI goes last
    }

    @Override
    public void robotPeriodic()
    {
        // This is invoked in both Autonomous and TeleOp, in
        // addition to the autonomousPeriodic and TeleOp periodic.
    }

    public Drivetrain getDrivetrain()
    {
        return mDrivetrain;
    }

    @Override
    public void autonomousInit()
    {
        mAutoCommand = m_oi.getAutoCommand();
        if (mAutoCommand != null)
        {
            mAutoCommand.start();
        }
        else
        {
            mLogger.error("can't start autonomous command because it is null.");
        }
        mLogger.notice("autonomous initialized");
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
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
            mLogger.error("can't cancel a null autonomous command.");
        }
        // FIXME: Actually initialize TeleOp here
        mLogger.notice("teleop initalized.");
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic()
    {
        LiveWindow.run();
    }

}
