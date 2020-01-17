package frc.robot.subsystems;

import frc.robot.Logger;
import frc.robot.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The subsystem that controls the LED.
 * Communicating to the Arduino by giving
 * a byte using Serial to the wanted BlingState to be
 * read by the Arduino to show the given Bling style wanted.
 *
 * Based off the Arduino code under Spartronics4915/Bling/Roborio_functionality
 */
public class LED extends SpartronicsSubsystem
{
    private SerialPort mBling;

    private static LED sInstance = null;
    private static BlingState mBlingState;

    public static LED getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new LED();
            mBlingState = BlingState.OFF;
        }
        return sInstance;
    }

    private LED()
    {
        try
        {
        	mBling = new SerialPort(9600, SerialPort.Port.kUSB);

            logInitialized(true);
        }
        catch (Exception e)
        {
            logException("LED: Couldn't initialize SerialPort", e);
            logInitialized(false);
        }
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putString("LED state:", mBlingState.toString());
    }

/**
 * This enum is giving the possible styles we can have the Arduino express.
 */
    public enum BlingState {
        OFF("0"),            // call constructor w/ bling code
        DISABLED("1"),
        INTAKE_UP("2"),
        INTAKE_DOWN("3"),
        LAUNCH("4")
        ;                    // semicolon to state more to follow

        private final String blingCode;

        BlingState(String code)
        {
            this.blingCode = code;
        }

        public byte[] getBlingMessage()
        {
            return this.blingCode.getBytes();
        }
    }

    /**
     * This will go through what we want the bling to do and express that style of bling.
     */
    public void setBlingState(BlingState blingState)
    {
        if (!isInitialized())
        {
            return;
        }

        // save current blingState for smartdashboard display
        mBlingState = blingState;

        // convert state to byte message and sent to serial port
        byte[] message = blingState.getBlingMessage();
        mBling.write(message, message.length);
        Logger.notice("LED: setBlingState is set to: " + blingState.name());
    }
}
