package frc.robot.powerup.data;

import org.montclairrobotics.cyborg.core.data.CBControlData;
import org.montclairrobotics.cyborg.core.data.CBLiftControlData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveControlData;

/**
 * This class holds the data used by the Controllers to 
 * actually control the hardware. It is the interface 
 * between the Logic and Hardware Layers. 
 * Define and initialize all of the fields required to
 * pass information to the controllers.
 * 
 * The driveData field is inherited from CBControlData
 * so it only needs to be initialized here. This is done
 * to ease the creation of standard drivetrain behaviors 
 * and controllers that can rely on the existance of 
 * this field. 
 */
public class ControlData extends CBControlData {

    /**
         * Initialize the driveData field with standard
         * drive control data
         */
    public CBStdDriveControlData drivetrain = new CBStdDriveControlData();

    /**
     * The CBLiftControlData class is pre-defined to provide
     * data for the CBLiftController class and as such is 
     * a drop-in which allows us to rapidly implement a variety 
     * of lift (or other reciprocating) configurations.
     * 
     * It is used here twice since the robot being implemented
     * has two independant lifts. 
     */
    public CBLiftControlData mainLift = new CBLiftControlData();
    public CBLiftControlData intakeLift = new CBLiftControlData();

    /**
     * The intake control data is using a drivetrain control data
     * class since the required action for the intake strongly
     * parallels that of a drivetrain (forward, back, turn left, 
     * turn right).
     */
    public CBStdDriveControlData intake = new CBStdDriveControlData();

    public ControlData() {
    }
}
