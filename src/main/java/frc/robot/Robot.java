/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LED.BlingState;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand; // important: Atlas does not have autonomous mode

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    System.out.println("In robotInit");
    m_robotContainer = new RobotContainer();

    // print out available serial ports for information
    LED.getInstance().enumerateAvailablePorts();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    m_robotContainer.outputAllToSmartDashboard();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    System.out.println("In disabledInit");
    Logger.notice("@disabledInit: Requested BlingState.DISABLED");
    LED.getInstance().setBlingState(BlingState.DISABLED);
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    LED.getInstance().setBlingState(BlingState.DISABLED);
    m_autonomousCommand = null;
    Logger.notice("Autonomous disabled");
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    LED.getInstance().setBlingState(BlingState.OFF);
    Logger.notice("autonomousPeriodic: Autonomous disabled");
  }

  @Override
  public void teleopInit() {
    // stop any autonomous commands
    Logger.notice("@ teleopInit...");
    if (m_autonomousCommand != null) {
      Logger.error("Error: there shouldn't be an autonomous command!");
      m_autonomousCommand.cancel();
      m_autonomousCommand = null;
    }

    Logger.notice("@ teleopInit: Requested BlingState.INTAKE_UP");
    LED.getInstance().setBlingState(BlingState.INTAKE_UP);

    // FIXME what is the need to update from smartdashboard?
    // update subsystem defaults from the network tables as needed
    m_robotContainer.mLauncher.updateFromSmartDashboard();

    Logger.notice("teleop initalized.");
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void testInit() {
    Logger.notice("@ testInit: Requested BlingState.LAUNCH");
    LED.getInstance().setBlingState(BlingState.LAUNCH);
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}