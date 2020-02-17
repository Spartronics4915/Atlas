package frc.robot.subsystems;

import frc.robot.Logger;
import frc.robot.subsystems.SpartronicsSubsystem;

import java.util.Arrays;
import com.fazecast.jSerialComm.SerialPort;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The subsystem that controls the LED. Communicating to the Arduino by giving a
 * byte using Serial to the wanted BlingState to be read by the Arduino to show
 * the given Bling style wanted.
 *
 * Based off the Arduino code under Spartronics4915/Bling/Roborio_functionality
 */
public class LED extends SpartronicsSubsystem {
    /**
     * jSerialComm is used for SerialPort communication to identify Arduino device.
     * while on linux systems we can use `lsusb -v` to get USB info, this does not
     * work on roboRIO. On Linux: "Arduino SA Uno R3 (CDC ACM)"
     *
     * See enumerateAvailablePorts() for more info. Note, @robotInit(),
     * prints out a list of available serial ports on the system.
     *
     * LED subsystem only 'write' to the port, writeBytes() method is used.
     */

    // output from kPortDescription is the output from the .getPortDescription()
    private static final String kPortDescription = "USB-Based Serial Port";
    private SerialPort mBlingPort = null;

    private static LED sInstance = null;
    private static BlingState mBlingState;

    public static LED getInstance() {
        if (sInstance == null) {
            sInstance = new LED();
            mBlingState = BlingState.OFF;
        }
        return sInstance;
    }

    private LED() {
        try {
            mBlingPort = Arrays.stream(SerialPort.getCommPorts()).filter(
                    (SerialPort p) -> p.getPortDescription().equals(kPortDescription) && !p.isOpen()).findFirst()
                    .orElseThrow(() -> new RuntimeException("Device not found: " + kPortDescription));

            mBlingPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            mBlingPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
            mBlingPort.openPort();

            logInitialized(true);
        } catch (Exception e) {
            logException("LED: Couldn't initialize SerialPort", e);
            logInitialized(false);
        }

    }

    @Override
    public void periodic() {
        SmartDashboard.putString("LED state:", mBlingState.toString());
    }

    /**
     * This enum is giving the possible styles we can have the Arduino express.
     */
    public enum BlingState {
        OFF("0"), // call constructor w/ bling code
        DISABLED("1"), // disabled
        INTAKE_UP("2"), // intake_up
        INTAKE_DOWN("3"), // intake_down
        LAUNCH("4"); // semicolon to state more to follow

        private final String blingCode;

        BlingState(String code) {
            this.blingCode = code;
        }

        public byte[] getBlingMessage() {
            return this.blingCode.getBytes();
        }
    }

    /**
     * This will go through what we want the bling to do and express that style of
     * bling.
     */
    public void setBlingState(BlingState blingState) {
        if (!isInitialized()) {
            return;
        }

        // save current blingState for smartdashboard display
        mBlingState = blingState;

        // convert state to byte message and sent to serial port
        byte[] message = blingState.getBlingMessage();
        mBlingPort.writeBytes(message, message.length);
        Logger.notice("LED: setBlingState is set to: " + blingState.name());
    }

    /**
     * Generate a list of available serial ports on the system; example output:
     * DEBUG Port Listing Start: -----------
     * DEBUG ttyUSB1|USB-to-Serial Port (pl2303)|USB-to-Serial Port (pl2303)
     * DEBUG ttyUSB2|USB-to-Serial Port (ftdi_sio)|TTL232R-3V3
     * DEBUG ttyUSB0|USB-to-Serial Port (cp210x)|CP2104 USB to UART Bridge Controller
     * DEBUG ttyS1|Physical Port S1|Physical Port S1
     * DEBUG ttyACM0|USB-Based Serial Port|USB-Based Serial Port
     * DEBUG Port Listing End: -----------
     */
    public void enumerateAvailablePorts() {
        Logger.debug("Port Listing Start: ============================================");
        SerialPort ports[] = SerialPort.getCommPorts();
        if (ports.length == 0) {
            Logger.debug("ERROR: No available serial ports found!");
            return;
        }
        for (SerialPort port : ports) {
            String info = port.getSystemPortName() + "|" + port.getDescriptivePortName() + "|"
                    + port.getPortDescription() + "\n";
            Logger.debug(info);
        }
        Logger.debug("Port Listing End: ==============================================");
    }
}
