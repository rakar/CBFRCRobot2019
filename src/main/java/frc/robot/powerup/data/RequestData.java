package frc.robot.powerup.data;

import org.montclairrobotics.cyborg.data.CBRequestData;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;

public class RequestData extends CBRequestData {

    // operator
    public boolean shootCube;
    public boolean intakeLiftUp;
    public boolean intakeLiftDown;
    public boolean mainLiftUp;
    public boolean mainLIftDown;

    // sensors
    public double mainLiftEncoderValue;
    public boolean mainLiftLimitValue;
    public double drivetrainLeftEncoderValue;
    public double drivetrainRightEncoderValue;
    public double drivetrainAverageEncoderValue;
    public double robotAngle;

    // FMS Data
    public String gameSpecificMessage;
    public char fieldPosition;
    public String autoSelection;
    public char nearSwitchSide;


    // crazy second drivetrain for intake
    public CBStdDriveRequestData intake = new CBStdDriveRequestData();

}
