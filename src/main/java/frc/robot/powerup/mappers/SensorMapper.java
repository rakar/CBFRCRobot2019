package frc.robot.powerup.mappers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.powerup.RobotCB;
import frc.robot.powerup.data.RequestData;

import org.montclairrobotics.cyborg.CBGameMode;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.*;
import org.montclairrobotics.cyborg.mappers.CBSensorMapper;

import static org.montclairrobotics.cyborg.Cyborg.gameMode;

public class SensorMapper extends CBSensorMapper {

    private RequestData rd;

    CBHardwareAdapter ha = Cyborg.hardwareAdapter;
    private CBEncoder mainLiftEncoder= ha.getEncoder(RobotCB.mainLiftEncoder);
    private CBDigitalInput mainLiftLimit = ha.getDigitalInput(RobotCB.mainLiftLimit);
    private CBEncoder intakeLiftEncoder = ha.getEncoder(RobotCB.intakeLiftEncoder);
    private CBEncoder drivetrainLeftEncoder = ha.getEncoder(RobotCB.dtLeftEncoder);
    private CBEncoder drivetrainRightEncoder = ha.getEncoder(RobotCB.dtRightEncoder);
    @SuppressWarnings("unchecked")
    private CBDashboardChooser<Character> fieldPosition = (CBDashboardChooser<Character>)ha.getDevice(RobotCB.fieldPosition);
    @SuppressWarnings("unchecked")
    private CBDashboardChooser<String> autoSelection = (CBDashboardChooser<String>)ha.getDevice(RobotCB.autoSelection);
    private CBNavX navx = ha.getNavX(RobotCB.navx);

    public SensorMapper(Cyborg robot) {
        super(robot);
        this.robot = robot;
        rd = (RequestData)Cyborg.requestData;
    }

    @Override
    public void update() {
        rd.mainLiftEncoderValue = mainLiftEncoder.getDistance();
        rd.mainLiftLimitValue = mainLiftLimit.get();
        rd.drivetrainLeftEncoderValue = drivetrainLeftEncoder.getDistance();
        rd.drivetrainRightEncoderValue = drivetrainRightEncoder.getDistance();
        rd.drivetrainAverageEncoderValue = (rd.drivetrainLeftEncoderValue+rd.drivetrainRightEncoderValue)/2.0;
        rd.robotAngle = navx.getYaw();

        // FMS Data, Driver Station (Just to make things interesting lets try this pre-game only)
        if ((gameMode & CBGameMode.preGame)!=0) {
            rd.gameSpecificMessage = DriverStation.getInstance().getGameSpecificMessage();
            rd.fieldPosition = fieldPosition.getSelected();
            rd.autoSelection = autoSelection.getSelected();
            rd.nearSwitchSide = rd.gameSpecificMessage.charAt(0);
        }

        SmartDashboard.putBoolean("mainLiftLimit", rd.mainLiftLimitValue);
        SmartDashboard.putNumber("mainLiftEncoder", rd.mainLiftEncoderValue);
        SmartDashboard.putNumber("intakeLiftEncoder", intakeLiftEncoder.getDistance());
        SmartDashboard.putNumber("drivetrainLeftEncoder", rd.drivetrainLeftEncoderValue);
        SmartDashboard.putNumber("drivetrainRightEncoder", rd.drivetrainRightEncoderValue);
        SmartDashboard.putString("AutoSelectedEcho", rd.autoSelection);
        SmartDashboard.putNumber("FieldPositionEcho", rd.fieldPosition);
    }
}
