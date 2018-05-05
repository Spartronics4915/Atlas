
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

    public Logger m_logger;

    private Drivetrain m_drivetrain;
    private OI m_oi;

    private Command m_autoCommand;

    @Override
    public void robotInit()
    {
        m_logger = new Logger("Robot", Logger.Level.DEBUG);
        m_drivetrain = new Drivetrain();
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
        return m_drivetrain;
    }

    @Override
    public void autonomousInit()
    {
        m_autoCommand = m_oi.getAutoCommand();
        if (m_autoCommand != null)
        {
            m_autoCommand.start();
        }
        else
        {
            m_logger.error("can't start autonomous command because it is null.");
        }
        m_logger.notice("autonomous initialized");
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        if (m_autoCommand != null)
        {
            m_autoCommand.cancel();
            m_autoCommand = null;
        }
        else
        {
            m_logger.error("can't cancel a null autonomous command.");
        }
        // FIXME: Actually initialize TeleOp here
        m_logger.notice("teleop initalized.");
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
