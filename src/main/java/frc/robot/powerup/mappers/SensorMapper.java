package frc.robot.powerup.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.mappers.CBSensorMapper;
import org.montclairrobotics.cyborg.utils.CBGameMode;
import org.montclairrobotics.cyborg.utils.CBTimingController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.powerup.RobotCB;
import frc.robot.powerup.data.RequestData;

import static org.montclairrobotics.cyborg.Cyborg.hardwareAdapter;

public class SensorMapper extends CBSensorMapper {

    private RequestData rd = RobotCB.requestData; //(RequestData)Cyborg.requestData;

    // create local copies of the devices one-time
    // for use in update method
    private CBEncoder mainLiftEncoder = hardwareAdapter.getEncoder(RobotCB.mainLiftEncoder);
    private CBDigitalInput mainLiftLimit = hardwareAdapter.getDigitalInput(RobotCB.mainLiftLimit);
    private CBEncoder intakeLiftEncoder = hardwareAdapter.getEncoder(RobotCB.intakeLiftEncoder);
    private CBEncoder drivetrainLeftEncoder = hardwareAdapter.getEncoder(RobotCB.dtLeftEncoder);
    private CBEncoder drivetrainRightEncoder = hardwareAdapter.getEncoder(RobotCB.dtRightEncoder);
    @SuppressWarnings("unchecked")
    private CBDashboardChooser<Character> fieldPosition = (CBDashboardChooser<Character>)hardwareAdapter.getDevice(RobotCB.fieldPosition);
    @SuppressWarnings("unchecked")
    private CBDashboardChooser<String> autoSelection = (CBDashboardChooser<String>)hardwareAdapter.getDevice(RobotCB.autoSelection);
    private CBNavX navx = hardwareAdapter.getNavX(RobotCB.navx);
    private CBTimingController dashboardTimer= new CBTimingController().setTiming(CBGameMode.anyPeriodic, 10);

    public SensorMapper(Cyborg robot) {
        super(robot);
    }

    @Override
    public void update() {
        
        // The main work is done here transferring values from 
        // the devices to RequestData
        rd.mainLiftEncoderValue = mainLiftEncoder.getDistance();
        rd.mainLiftLimitValue = mainLiftLimit.get();
        rd.drivetrainLeftEncoderValue = drivetrainLeftEncoder.getDistance();
        rd.drivetrainRightEncoderValue = drivetrainRightEncoder.getDistance();
        rd.drivetrainAverageEncoderValue = (rd.drivetrainLeftEncoderValue+rd.drivetrainRightEncoderValue)/2.0;
        rd.robotAngle = navx.getYaw();

        // FMS Data, Driver Station (Just to make things interesting lets try this pre-game only)
        if (Cyborg.isGameMode(CBGameMode.preGame)) {
            rd.gameSpecificMessage = DriverStation.getInstance().getGameSpecificMessage();
            rd.fieldPosition = fieldPosition.getSelected();
            rd.autoSelection = autoSelection.getSelected();
            rd.nearSwitchSide = rd.gameSpecificMessage.charAt(0);
        }

        if(dashboardTimer.update().getState()) {
            SmartDashboard.putBoolean("mainLiftLimit", rd.mainLiftLimitValue);
            SmartDashboard.putNumber("mainLiftEncoder", rd.mainLiftEncoderValue);
            SmartDashboard.putNumber("intakeLiftEncoder", intakeLiftEncoder.getDistance());
            SmartDashboard.putNumber("drivetrainLeftEncoder", rd.drivetrainLeftEncoderValue);
            SmartDashboard.putNumber("drivetrainRightEncoder", rd.drivetrainRightEncoderValue);
            SmartDashboard.putString("AutoSelectedEcho", rd.autoSelection);
            SmartDashboard.putNumber("FieldPositionEcho", rd.fieldPosition);
        }
    }
}
