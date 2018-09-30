package frc.robot.powerup.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.behaviors.CBBehavior;
import frc.robot.powerup.RobotCB;
import frc.robot.powerup.data.ControlData;
import frc.robot.powerup.data.RequestData;

public class IntakeLiftBehavior extends CBBehavior {
    private RequestData rd = RobotCB.requestData; // (RequestData)Cyborg.requestData;
    private ControlData cd = RobotCB.controlData; //(ControlData)Cyborg.controlData;

    public IntakeLiftBehavior(Cyborg robot) {
        super(robot);

        // These are essentially static configurations of the lift
        // they can be set once and are generally never changed
        cd.intakeLift.normDown = -0.5;
        cd.intakeLift.slowDown = -0.1;
        cd.intakeLift.normUp = 0.5;
        cd.intakeLift.slowUp = 0.25;

        cd.intakeLift.target.setActive(false);
        cd.intakeLift.bottomMargin.setTarget(20000,10);
        cd.intakeLift.bottomMargin.setActive(true);
        cd.intakeLift.topMargin.setTarget(120000,10);
        cd.intakeLift.topMargin.setActive(true);
        cd.intakeLift.topEncoderLimit.setTarget(140000,0);
        cd.intakeLift.topEncoderLimit.setActive(true);
    }

    @Override
    public void update() {
        super.update();

        cd.intakeLift.requestUp = rd.intakeLiftUp;
        cd.intakeLift.requestDown = rd.intakeLiftDown;
    }
}
