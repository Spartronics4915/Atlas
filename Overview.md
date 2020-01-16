# RobotMap

## Diagnostic Dashboard
* For all motors implement a low speed forward & reverse test
  * IMPORTANT for the launcher -- see below
* For all switches implement a status display -- switch activated and/or pushed

## System wide
```java
// Compressor -- auto handled by the PCM
```

## Drive Train
```java
// Drive train motors -- 4 Victors, connected in parallel two 2 PWM channels
    public static final int kLeftDriveMotorId = 0;
    public static final int kRightDriveMotorId = 1;

// Instantiation & reference will be something like the following
    public static SpeedController drivetrainLeftMotor;
    public static SpeedController drivetrainRightMotor;
    drivetrainLeftMotor = new Victor(kLeftDriveMotorId);
    drivetrainRightMotor = new Victor(kRightDriveMotorId);

// IMU for 180 -- nice to have
```

## Harvester
Harvester double solonoid, limit switches, motor for ball pick up/spit
```java
/*
    Harvester extended/retracted via DoubleSolenoid
     -- Pressure Control Module (PCM) Channels ----
*/
    public static final int kHarvesterRetractSolenoidId = 0;    //PCM 0
    public static final int kHarvesterExtendSolenoidId = 1;     //PCM 1

// Instantiation & reference will be something like the following
// rename the 'anglers'?
    public static DoubleSolenoid harvesterArms;
    // forwardChannel, reverseChannel
    harvesterArms = new DoubleSolenoid(kHarvesterExtendSolenoidId, kHarvesterRetractSolenoidId);
```

```java
/*
    harvester wheel for pickup & eject ball is via Talon motor controller on a PWM channel
*/
    public static final int kHarvesterCollectionMotorId = 3;    //PCM 3

    public static SpeedController harvesterCollectionMotor;
    harvesterCollectionMotor = new Talon(kHarvesterCollectionMotorId);
```

```java
/*
    Magnetic switches will be wired appropriately -- they will act as a single switch for the program
    Magnetic switches are wired as normally open
        return 1: while open
        return 0: when closed
*/
    public static final int kHarvesterRetractedSwitchId = 0;    //DIO 0
    public static final int kHarvesterExtendedSwitchId = 1;    //DIO 1

    public static DigitalInput harvesterRetracted;
    public static DigitalInput harvesterExtended;
    harvesterRetracted = new DigitalInput(kharvesterRetractedSwitchId);
    harvesterExtended = new DigitalInput(kharvesterExtendedSwitchId);
```

## Launcher
Launcher will test if ball is present before launching.
IMPORTANT: launcher winder can only operate in one direction -- anything else will burn the motor. Dashboard diagnostics need to run low speed test to determine correct operational direction.

```java
/*
    Distance sensor will be used to identify if the ball is present or not
    @randy -- pls review this section
*/
```java
    public static final int kBallPresentSensorId = 0;     // analog port 0

// Instantiation & reference will be something like the following
    public static AnalogInput ballPresent;
    ballPresent = new AnalogInput(kBallPresentSensorId);

```java
/*
        Launcher launched/rewound via DoubleSolonoid
    */
    public static final int kLaunchRetractSolenoidId = 3;    //PCM 3
    public static final int kLaunchExtendSolenoidId = 4;     //PCM 4

// Instantiation & reference will be something like the following
    public static Solenoid launcherActivate;
    // forwardChannel, reverseChannel
    launcherActivate = new DoubleSolenoid(kLaunchExtendSolenoidId, kLaunchRetractSolenoidId);
```

```java
/*
    Launcher will depend on the limit switch to stop the winding motor. The limit switches will be wired so that programmatically they act as a single limit switch. Rewind switch is normally closed.
        return 0: when not pushed
        return 1: when pushed
*/
    public static final int klauncherRewoundSwitchId = 3;    // DIO 3

 // Instantiation & reference will be something like the following
    public static DigitalInput launcherRewound;
    launcherRewound = new DigitalInput(klauncherRewoundSwitchId);
```

```java
/*
    launcher motor will rewound the launcher. IMPORTANT -- motor can only operate one direction!
*/
    public static final int kLauncherWindingMotorId = 4;    //PCM 4

    public static SpeedController launcherWindingMotor;
    launcherWindingMotor = new Talon(kLauncherWindingMotorId);
```

# OI Controls
**Important: AS controls are disabled in 2020 codebase -- use joystick connected to port 0**
* Harvester
    * Buttons DS:2 -- intake down
        * command completes when limit switch is reached
    * Buttons DS:3 -- intake up
        * command completes when 1sec timeout reached
    * Buttons DS:4 -- spits the ball
        * runs harvester wheels for 6 secs
    * Buttons DS:1 -- run harvester wheels for ball intake
        * While held, runs the wheels. When released, stops wheels
    * Buttons DS:5 and DS:10 -- stop harvester wheels

* Launcher
    * Buttons DS:7 - Launch ball command group
        * harvester extends
        * launches only IF ball present
    * Buttons DS:11 -- Rewind launcher command group
        * runs the launcherWindingMotor UNTIL the rewind limit switch is activated
        * uses a set timeout for the safety
    * Buttons DS:6 -- test button to activate/deactivate launcher
      solenoid
        * used for releasing elastic bands
        * activates launcher, waits 2 secs. and deactivates launcher
        * requires ball/hand presence to trick the ball present sensor & harvester to be down

* Drive train
    * Throttle controls from min (0.45) to max (1.0) drive speed
    * **Important follow is NOT tested!**
        * Drive straight while held: Button DS: 8
        * 180 turn: Button DS: 9

