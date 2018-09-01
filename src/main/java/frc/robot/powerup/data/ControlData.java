package frc.robot.powerup.data;

import org.montclairrobotics.cyborg.data.CBControlData;
import org.montclairrobotics.cyborg.data.CBLiftControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;

public class ControlData extends CBControlData {

    //public CBTriState intakeStatus;
    //public CBTriState intakeLiftStatus;
    //public CBTriState mainLiftStatus;

    public CBLiftControlData mainLift = new CBLiftControlData();
    public CBLiftControlData intakeLift = new CBLiftControlData();

    // intake, yeah intake
    public CBStdDriveControlData intake = new CBStdDriveControlData();
}
