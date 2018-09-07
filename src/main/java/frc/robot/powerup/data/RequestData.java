package frc.robot.powerup.data;

import org.montclairrobotics.cyborg.data.CBRequestData;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;

/**
 * This class holds the data used by the Behaviors to 
 * decide what the robot should do. It is the interface 
 * between the Hardware and Logic Layers. 
 * Define and initialize all of the fields required to
 * pass information to the Behaviors.
 * 
 * The driveData field is inherited from CBRequestData
 * so it only needs to be initialized here. This is done
 * to ease the creation of standard drivetrain mappers 
 * and behaviors that can rely on the existance of 
 * this field. 
 */
public class RequestData extends CBRequestData {

    /**
     * Drivetrain
     */
    public CBStdDriveRequestData drivetrain = new CBStdDriveRequestData();

    /**
     * These fields represent requests that would be
     * made of the robot by the operator.  
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
     * These fields represent information 
     * gathered from the FMS/Dashboard 
     */
    public String gameSpecificMessage;
    public char fieldPosition;
    public String autoSelection;
    public char nearSwitchSide;

    public RequestData() {
    }
}
