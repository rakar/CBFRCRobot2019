package frc.robot.powerup.data;

import org.montclairrobotics.cyborg.core.data.CBRequestData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveRequestData;

/**
 * This class holds the data used by the Behaviors to decide what the robot should do. It is the
 * inbound interface between the Hardware and Logic Layers.
 *
 * Define and initialize all of the fields required to pass information to the Behaviors.
 */
public class RequestData extends CBRequestData {

    /**
     * Drivetrain
     */
    public CBStdDriveRequestData drivetrain = new CBStdDriveRequestData();

    /**
     * These fields represent requests that would be made of the robot by the operator.
     */
    public boolean shootCube;
    public boolean intakeLiftUp;
    public boolean intakeLiftDown;
    public boolean mainLiftUp;
    public boolean mainLIftDown;
    public CBStdDriveRequestData intake = new CBStdDriveRequestData();

    /**
     * These fields represent hardware sensors.
     */
    public double mainLiftEncoderValue;
    public boolean mainLiftLimitValue;
    public double drivetrainLeftEncoderValue;
    public double drivetrainRightEncoderValue;
    public double drivetrainAverageEncoderValue;
    public double robotAngle;

    /**
     * These fields represent information gathered from the FMS/Dashboard
     */
    public String gameSpecificMessage;
    public char fieldPosition;
    public String autoSelection;
    public char nearSwitchSide;

    public RequestData() {
    }
}
