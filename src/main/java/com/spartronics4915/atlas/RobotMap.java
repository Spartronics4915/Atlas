package com.spartronics4915.atlas;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * NO CODE HERE! Only constants!
 */
public class RobotMap
{
    //CAN Devices
    public static final int kNumPigeons = 1;
    public static final int kNumPCMs = 1;
    public static final int kNumCANDevices = kNumPCMs + kNumPigeons;

    //Solenoid IDs
    public static final int kHarvesterRetractSolenoidId = 0; //PCM 0
	public static final int kHarvesterExtendSolenoidId = 1; // PCM 1
	public static final int kLaunchSolenoidId = 3; //PCM 3
	
	//Motor IDs
	public static final int kLeftDriveMotorId = 0;
    public static final int kRightDriveMotorId = 1;
	public static final int kHarvesterCollectionMotorId = 3;
	public static final int kLauncherWindingMotorId = 4;
    
    //Analog IDs
    public static final int kBallPresentSensorId = 0; //Analog port 0
    
    //DIO IDs
    public static final int kHarvesterTopMagneticSwitchId = 0; //DIO 0
    public static final int kHarvesterBottomMagneticSwitchId = 1; //DIO 1
    public static final int klauncherRewoundSwitchId = 3; //DIO 3
    
}
