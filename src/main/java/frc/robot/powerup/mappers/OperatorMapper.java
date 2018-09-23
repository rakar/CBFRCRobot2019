package frc.robot.powerup.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.mappers.CBTeleOpMapper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.powerup.RobotCB;
import frc.robot.powerup.data.RequestData;

import static org.montclairrobotics.cyborg.Cyborg.hardwareAdapter;

public class OperatorMapper extends CBTeleOpMapper {

    private RequestData rd = RobotCB.requestData; //(RequestData) Cyborg.requestData;

    private CBButton shootCubeButton = hardwareAdapter.getButton(RobotCB.shootCubeButton);
    private CBButton intakeLiftUpButton = hardwareAdapter.getButton(RobotCB.intakeLiftUpButton);
    private CBButton intakeLiftDownButton = hardwareAdapter.getButton(RobotCB.intakeLiftDownButton);
    private CBButton mainLiftUpButton = hardwareAdapter.getButton(RobotCB.mainLiftUpButton);
    private CBButton mainLiftDownButton = hardwareAdapter.getButton(RobotCB.mainLiftDownButton);
    
    public OperatorMapper(Cyborg robot) {
        super(robot);
    }

    @Override
    public void update() {
        rd.shootCube      = shootCubeButton.getState();
        rd.intakeLiftUp   = intakeLiftUpButton.getState();
        rd.intakeLiftDown = intakeLiftDownButton.getState();
        rd.mainLiftUp     = mainLiftUpButton.getState();
        rd.mainLIftDown   = mainLiftDownButton.getState();

        SmartDashboard.putBoolean("intakeLiftUpButton", rd.intakeLiftUp);
        SmartDashboard.putBoolean("intakeLiftDownButton", rd.intakeLiftDown);
    }
}
