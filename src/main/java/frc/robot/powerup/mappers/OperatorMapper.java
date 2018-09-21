package frc.robot.powerup.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.mappers.CBTeleOpMapper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.powerup.RobotCB;
import frc.robot.powerup.data.RequestData;

public class OperatorMapper extends CBTeleOpMapper {

    private RequestData requestData = RobotCB.requestData; //(RequestData) Cyborg.requestData;

    private CBButton shootCubeButton;
    private CBButton intakeLiftUpButton;
    private CBButton intakeLiftDownButton;
    private CBButton mainLiftUpButton;
    private CBButton mainLiftDownButton;
    
    public OperatorMapper(Cyborg robot) {
        super(robot);
        shootCubeButton = Cyborg.hardwareAdapter.getButton(RobotCB.shootCubeButton);
        intakeLiftUpButton = Cyborg.hardwareAdapter.getButton(RobotCB.intakeLiftUpButton);
        intakeLiftDownButton = Cyborg.hardwareAdapter.getButton(RobotCB.intakeLiftDownButton);
        mainLiftUpButton = Cyborg.hardwareAdapter.getButton(RobotCB.mainLiftUpButton);
        mainLiftDownButton = Cyborg.hardwareAdapter.getButton(RobotCB.mainLiftDownButton);
    }

    @Override
    public void update() {
        requestData.shootCube      = shootCubeButton.getState();
        requestData.intakeLiftUp   = intakeLiftUpButton.getState();
        requestData.intakeLiftDown = intakeLiftDownButton.getState();
        requestData.mainLiftUp     = mainLiftUpButton.getState();
        requestData.mainLIftDown   = mainLiftDownButton.getState();

        SmartDashboard.putBoolean("intakeLiftUpButton", requestData.intakeLiftUp);
        SmartDashboard.putBoolean("intakeLiftDownButton", requestData.intakeLiftDown);
    }
}
