/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class RobotMapConstants {
        //Speeds
        public static final double kHarvesterIntakeWheelSpeed = -0.75;
        public static final double kHarvesterExpelWheelSpeed = 0.6;

        //CAN Devices
        public static final int kNumPigeons = 1;
        public static final int kNumPCMs = 1;
        public static final int kNumCANDevices = kNumPCMs + kNumPigeons;

        //Solenoid IDs
        public static final int kHarvesterRetractSolenoidId = 0; //PCM 0
        public static final int kHarvesterExtendSolenoidId = 1; // PCM 1
        public static final int kLaunchExtendSolenoidId = 2; //PCM 2
        public static final int kLaunchRetractSolenoidId = 3; //PCM 3

        //Motor IDs
        public static final int kLeftDriveMotorId = 0;
        public static final int kRightDriveMotorId = 1;
        public static final int kHarvesterCollectionMotorId = 2;
        public static final int kLauncherWindingMotorId = 3;

        //Analog IDs
        public static final int kBallPresentSensorId = 0; //Analog port 0

        //DIO IDs
        public static final int kHarvesterTopMagneticSwitchId = 0; //DIO 0
        public static final int kHarvesterBottomMagneticSwitchId = 1; //DIO 1
        public static final int klauncherRewoundSwitchId = 2; //DIO 2

        //CAN IDs
        public static final int kDriveTrainIMUID = 2;
    }

    /**
     * This class is the glue that binds the controls on the physical operator
     * interface to the commands and command groups that allow control of the robot.
     */
    public static final class OIConstants
    {
        public static final int kDriveJoystickPort = 0;

        // drivetrain controls
        public static final int kQuickTurnDriveStickButton = 9;
        public static final int kDriveStraightDriveStickButton = 8;

        // launcher controls
        public static final int kLaunchDriveStickButton = 7;            // was: 2
        public static final int kWindLauncherDriveStickButton = 11;     // was: 3

        // harvester controls
        public static final int kHarvesterExtendDriveStickButton = 2;           // was: 5
        public static final int kHarvesterRetractDriveStickButton = 3;          // was; 4
        public static final int kHarvesterReleaseDriveStickButton = 4;          // was: 10
        public static final int kHarvesterWheelsToggleDriveStickButton = 1;
        public static final int kHarvesterWheelsStopDriveStickButton = 5;       // was: 7 
        public static final int kHarvesterWheelsStopDriveStickButton_2 = 10;    // yes - 2nd button for convinience

        // launcher test controls -- not on ArcadeStick due to the 'mode' button
        public static final int kTestLauncherSolenoidDriveStickButton = 6;     // was: 11

        // Arcade button assignments -- unused currently
        // public static final int kArcadeStickPort = 1;
        // public static final int kLaunchArcadeStickButton = 2;
        // public static final int kWindLauncherArcadeStickButton = 4;
        // public static final int kHarvesterExtendArcadeStickButton = 1;
        // public static final int kHarvesterRetractArcadeStickButton = 3;
        // public static final int kHarvesterReleaseArcadeStickButton = 6;
        // public static final int kHarvesterWheelsToggleArcadeStickButton = 5;
        // public static final int kHarvesterWheelsStopArcadeStickButton = 7;
    }

    /**
     * Drive constants for PID controls
     */
    public static final class DriveConstants
    {
        public static final double kP = 0; // Was 0.4
        public static final double kI = 0.2;
        public static final double kD = 0;
        public static final double kAllowedError = 0; // In degrees
    }
}
