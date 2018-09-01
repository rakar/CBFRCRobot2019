package frc.robot.powerup.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.mappers.CBTeleOpMapper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.powerup.data.RequestData;

public class OperatorMapper extends CBTeleOpMapper {

    private RequestData requestData = (RequestData) Cyborg.requestData;

    private CBButton shootCubeButton;
    private CBButton intakeLiftUpButton;
    private CBButton intakeLiftDownButton;
    private CBButton mainLiftUpButton;
    private CBButton mainLiftDownButton;
    
    public OperatorMapper(Cyborg robot) {
        super(robot);
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

    public OperatorMapper setShootCubeButton(CBDeviceID buttonID) {
        shootCubeButton = Cyborg.hardwareAdapter.getButton(buttonID);
        return this;
    }

    public OperatorMapper setIntakeLiftUpButton(CBDeviceID buttonID) {
        intakeLiftUpButton = Cyborg.hardwareAdapter.getButton(buttonID);
        return this;
    }

    public OperatorMapper setIntakeLiftDownButton(CBDeviceID buttonID) {
        intakeLiftDownButton = Cyborg.hardwareAdapter.getButton(buttonID);
        return this;
    }

    public OperatorMapper setMainLiftUpButton(CBDeviceID buttonID) {
        mainLiftUpButton = Cyborg.hardwareAdapter.getButton(buttonID);
        return this;
    }

    public OperatorMapper setMainLiftDownButton(CBDeviceID buttonID) {
        mainLiftDownButton = Cyborg.hardwareAdapter.getButton(buttonID);
        return this;
    }
}
