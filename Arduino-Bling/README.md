# Integrating NeoPixel with Arduino & Robot Code
This directory hosts the source code to interface roborio/wpilib robot fuctionality with
NeoPixel/Arduino.

## High-level code flow
1. Robot code sets the desired LED blink pattern
2. LED subsystem sends the required blink pattern over the serial port
   to the Arduino
3. Arduino code frequently checks the serial port to determine if a new
   blink pattern is required --> if so, it changes the blink pattern

## How to interface robot code w/ Arduino for LED animation
Robot code interfaces to Arduino via serial port communication. The
commands to change LED animation pattern is sent as an octet byte over
the serial.

Robot code:
* `LED` subsystem:
  * specifies the blink patterns --> see `LED::BlinkState`
  * methods to send the blink code over the serial port --> see
    `LED::setBlinkState()`. Note, this method is also used to set blink
    state changes when `SetBlinkStateCommand` cannot be used.
* `SetBlinkStateCommand` Command for incorporating blink state change
  requests to robot code.

### Specifying Bling States
Bling states indicates different bling annimation patterns. As a general
rule, for easy maintenance, states should use *names* that reflect robot behavior over
bling patterns.

```
  public enum BlingState {
        OFF("0"),           // turn off the LED animation -- not used in robot code
        DISABLED("1"),      // animation for when robot is in disabled state
        INTAKE_UP("2"),     // animation when intake is up
        INTAKE_DOWN("3"),   // animation when intake is down for ball pickup
        LAUNCH("4")         // animation when ball is launched
        ;                   // semicolon to state more to follow

        ...
```

### Setting Bling States in robot code
There are two ways to set new bling states in code:

* Via `SetBlingStateCommand` command
```
// See RobotContainer.java
        new JoystickButton(mDriverController, OIConstants.kHarvesterExtendDriveStickButton)
                                .whenPressed(new IntakeDownForPickup(mHarvester, mLauncher)
                                .alongWith(new SetBlingStateCommand(mLED, BlingState.INTAKE_DOWN)));
```
* Via `LED::setBlingState()` method -- note: this method is in the
  RobotContainer.java, with the call from Robot.java.
```
// Method in RobotContainer.java
  public void setBlingState(BlingState blingState)
  {
    mLED.setBlingState(blingState);
  }
```

```
// Robot.java for setting bling state while in disabled mode
  @Override
  public void disabledInit() {
    Logger.notice("@disabledInit: Requested BlingState.DISABLED");
    m_robotContainer.setBlingState(BlingState.DISABLED);
  }
```

## Arduino Code
Arduino code supports:
* Listening over the serial port for blink state changes
* Blink patterns
* Interrupt code that handles blink animation and listens on the serial port
  for state changes
