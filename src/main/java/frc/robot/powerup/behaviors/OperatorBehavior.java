package frc.robot.powerup.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.behaviors.CBBehavior;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import frc.robot.powerup.RobotCB;
import frc.robot.powerup.data.ControlData;
import frc.robot.powerup.data.RequestData;

public class OperatorBehavior extends CBBehavior {

    private RequestData rd = RobotCB.requestData; //(RequestData) Cyborg.requestData;
    private ControlData cd = RobotCB.controlData; //(ControlData) Cyborg.controlData;
    private CBStdDriveControlData intake = cd.intake;

    public OperatorBehavior(Cyborg robot) {
        super(robot);
    }

    @Override
    public void update() {
        super.update();

        if(rd.shootCube) {
            intake.active=true;
            intake.direction.setXY(0,1); // bad value - shoot cube?
            intake.rotation = 0;
        } else {
            if(!intake.active) {
                intake.active = true;
                intake.direction.setXY(0, -.20); // bad value - Draw cube in?
                intake.rotation = 0;
            }
        }
    }
}
