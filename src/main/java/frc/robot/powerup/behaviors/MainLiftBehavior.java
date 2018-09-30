package frc.robot.powerup.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.behaviors.CBBehavior;
import frc.robot.powerup.RobotCB;
import frc.robot.powerup.data.ControlData;
import frc.robot.powerup.data.RequestData;

public class MainLiftBehavior extends CBBehavior {
    private RequestData rd = RobotCB.requestData; //(RequestData)Cyborg.requestData;
    private ControlData cd = RobotCB.controlData; //(ControlData)Cyborg.controlData;

    public MainLiftBehavior(Cyborg robot) {
        super(robot);

        // These are essentially static configurations of the lift
        // they can be set once and are generally never changed
        cd.mainLift.normDown = -0.5;
        cd.mainLift.slowDown = -0.2;
        cd.mainLift.normUp = 1.0;
        cd.mainLift.slowUp = 0.5;

        cd.mainLift.target.setActive(false);
        cd.mainLift.bottomMargin.setTarget(1000,10);
        cd.mainLift.bottomMargin.setActive(true);
        cd.mainLift.topMargin.setTarget(4000,10);
        cd.mainLift.topMargin.setActive(true);
        cd.mainLift.topEncoderLimit.setTarget(5000,0);
        cd.mainLift.topEncoderLimit.setActive(true);
    }

    @Override
    public void update() {
        super.update();

        cd.mainLift.requestUp = rd.mainLiftUp;
        cd.mainLift.requestDown = rd.mainLIftDown;
    }
}
