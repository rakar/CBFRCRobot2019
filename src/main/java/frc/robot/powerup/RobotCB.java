package frc.robot.powerup;

//#region imports
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.core.assemblies.CBVictorArrayController;
import org.montclairrobotics.cyborg.core.behaviors.CBStdDriveBehavior;
import org.montclairrobotics.cyborg.core.controllers.CBDifferentialDriveController;
import org.montclairrobotics.cyborg.core.controllers.CBLiftController;
import org.montclairrobotics.cyborg.core.utils.CB2DVector;
import org.montclairrobotics.cyborg.core.utils.CBEnums;
import org.montclairrobotics.cyborg.core.utils.CBPIDErrorCorrection;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBHardwareAdapter;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBPDB;
import org.montclairrobotics.cyborg.devices.CBSpeedControllerFaultCriteria;
import org.montclairrobotics.cyborg.devices.CBTalonSRX;
import org.montclairrobotics.cyborg.mappers.CBArcadeDriveMapper;
import org.montclairrobotics.cyborg.mappers.CBMotorMonitorMapper;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.powerup.behaviors.AutoExample1;
import frc.robot.powerup.behaviors.IntakeLiftBehavior;
import frc.robot.powerup.behaviors.MainLiftBehavior;
import frc.robot.powerup.behaviors.OperatorBehavior;
import frc.robot.powerup.data.ControlData;
import frc.robot.powerup.data.RequestData;
import frc.robot.powerup.mappers.OperatorMapper;
import frc.robot.powerup.mappers.SensorMapper;
//#endregion

public class RobotCB extends Cyborg {
    // constants
    // joystick ports
    private final int driveStickID = 0;
    private final int operatorStickID = 1;

    //
    // This has been changed to "public static" from "private" to allow
    // for direct access in cases of NON-reusable mappers/behaviors
    // to avoid lengthy init code, that will always be robot specific
    // anyway. Reusable code, should of course use setter functions to
    // attach to devices.
    //

    //#region Device List...
    public static CBDeviceID 
    // modules
    pdb, navx,

    // driver controls
    driveXAxis, driveYAxis, gyroLockButton,

    // operator controls
    operXAxis, operYAxis, shootCubeButton, intakeLiftUpButton, intakeLiftDownButton, mainLiftUpButton, mainLiftDownButton,

    // drivetrain Motors
    dtFrontLeftMotor, dtFrontRightMotor, dtBackLeftMotor, dtBackRightMotor,

    // dt Encoders
    dtLeftEncoder, dtRightEncoder,

    //lift Motors
    mainLiftMotorFront, mainLiftMotorBack, intakeLiftMotor,

    // lift encoders
    mainLiftEncoder, intakeLiftEncoder,

    // lift limit switches
    mainLiftLimit,

    // intake motors
    intakeLeftMotor, intakeRightMotor,

    // dashboard choosers
    fieldPosition, autoSelection
    ;
    //#endregion

    public static RequestData requestData;
    public static ControlData controlData;

    public RobotCB() {
    }

    @Override
    public void cyborgInit() {

        // data init
        requestData = new RequestData();
        controlData = new ControlData();

        defineDevices();
        defineMappers();
        defineControllers();
        defineBehaviors();
    }

    @Override
    public void cyborgDisabledInit() {

    }

    @Override
    public void cyborgAutonomousInit() {
        switch(((RequestData)requestData).autoSelection) {
            case "auto1":
                this.addAutonomous(new AutoExample1(this));
                break;
            default:
                // just say no
                break;
        }
    }

    @Override
    public void cyborgTeleopInit() {

    }

    @Override
    public void cyborgTestInit() {

    }

    private void defineDevices() {
        // Configure Hardware Adapter and Devices
        hardwareAdapter = new CBHardwareAdapter(this);
        //ha = hardwareAdapter;

        pdb = hardwareAdapter.add(
                new CBPDB()
        );

        navx = hardwareAdapter.add(
                new CBNavX(SPI.Port.kMXP)
        );

        // setup drivetrain
        dtFrontLeftMotor = hardwareAdapter.add(
                new CBTalonSRX(1)
                        .setDeviceName("DriveTrain", "FrontLeft")
                        .setPowerSource(pdb, 0)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 30)
                        )
        );
        dtFrontRightMotor = hardwareAdapter.add(
                new CBTalonSRX(7)
                        .setDeviceName("DriveTrain", "FrontRight")
                        .setPowerSource(pdb, 1)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 30)
                        )
        );
        dtBackLeftMotor = hardwareAdapter.add(
                new CBTalonSRX(3)
                        .setDeviceName("DriveTrain", "BackLeft")
                        .setPowerSource(pdb, 2)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 30)
                        )
        );
        dtBackRightMotor = hardwareAdapter.add(
                new CBTalonSRX(8)
                        .setDeviceName("DriveTrain", "BackRight")
                        .setPowerSource(pdb, 3)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 30)
                        )
        );
        final double inchesPerTick = 96 / 4499;
        dtLeftEncoder = hardwareAdapter.add(
                new CBEncoder(1, 0, CounterBase.EncodingType.k4X, false, inchesPerTick)
        );
        dtRightEncoder = hardwareAdapter.add(
                new CBEncoder(3, 2, CounterBase.EncodingType.k4X, false, inchesPerTick)
        );


        // setup main lift
        mainLiftMotorFront = hardwareAdapter.add(
                new CBTalonSRX(4)
                        .setDeviceName("MainLift", "Front")
                        .setPowerSource(pdb, 4)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 20)
                        )
        );
        mainLiftMotorBack = hardwareAdapter.add(
                new CBTalonSRX(2)
                        .setDeviceName("MainLift", "Back")
                        .setPowerSource(pdb, 5)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 20)
                        )
        );
        mainLiftEncoder = hardwareAdapter.add(
                new CBEncoder(4, 5, CounterBase.EncodingType.k4X, false, 1)
        );
        mainLiftLimit = hardwareAdapter.add(
                new CBDigitalInput(9)
        );


        // setup intake lift
        intakeLiftMotor = hardwareAdapter.add(
                new CBTalonSRX(9)
                        .setDeviceName("Intake", "LiftMotor")
                        .setPowerSource(pdb, 6)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 20)
                        )
        );
        intakeLiftEncoder = hardwareAdapter.add(
                new CBEncoder(intakeLiftMotor, FeedbackDevice.QuadEncoder, false, 1)
        );


        // setup intake motors
        intakeLeftMotor = hardwareAdapter.add(
                new CBTalonSRX(10)
                        .setDeviceName( "Intake", "LeftMotor")
                        .setPowerSource(pdb, 8)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 30)
                        )
        );
        intakeRightMotor = hardwareAdapter.add(
                new CBTalonSRX(5)
                        .setDeviceName("Intake", "RightMotor")
                        .setPowerSource(pdb, 9)
                        .setSpeedControllerFaultCriteria(
                                new CBSpeedControllerFaultCriteria()
                                        .setBasic(.1, 1, 30)
                        )
        );

        // driver controls
        driveXAxis = hardwareAdapter.add(
                new CBAxis(driveStickID, 1)
                        .setDeadzone(0.1)
                        .setScale(-1.0)
        );
        driveYAxis = hardwareAdapter.add(
                new CBAxis(driveStickID, 0)
                        .setDeadzone(0.1)
                        .setScale(-1.0)
        );
        gyroLockButton = hardwareAdapter.add(
                new CBButton(driveStickID, 1)
        );

        // operator controls
        operXAxis = hardwareAdapter.add(
                new CBAxis(operatorStickID, 1)
                        .setDeadzone(0.1)
                        .setScale(-1.0)
        );

        operYAxis = hardwareAdapter.add(
                new CBAxis(operatorStickID, 0)
                        .setDeadzone(0.1)
                        .setScale(-1.0)
        );

        shootCubeButton = hardwareAdapter.add(
                new CBButton(operatorStickID, 1)
        );
        intakeLiftUpButton = hardwareAdapter.add(
                new CBButton(operatorStickID, 3)
        );
        intakeLiftDownButton = hardwareAdapter.add(
                new CBButton(operatorStickID, 2)
        );
        mainLiftUpButton = hardwareAdapter.add(
                new CBButton(operatorStickID, 4)
        );
        mainLiftDownButton = hardwareAdapter.add(
                new CBButton(operatorStickID, 5)
        );

        // dashboard elements
        fieldPosition = hardwareAdapter.add(
                new CBDashboardChooser<Character>("Field Position")
                        .addChoice("left",'L')
                        .addChoice("center", 'C')
                        .addChoice("right",'R')
        );
        autoSelection = hardwareAdapter.add(
                new CBDashboardChooser<String>("Autonomous")
                        .addDefault("NONE!!!", "none")
                        .addChoice("Auto1", "auto1")

        );
    }

    private void defineMappers() {
        // setup teleop mappers
        this.addTeleOpMapper(
                new CBArcadeDriveMapper(this, requestData.drivetrain)
                        .setAxes(driveYAxis, null, driveXAxis)
                        // TODO: the following line commented because the mode above was changed to Power (from speed)
                        // TODO: Tune Axis Scales
                        //.setAxisScales(0, 40, 90) // no strafe, 40 inches/second, 90 degrees/second
                        .setGyroLockButton(gyroLockButton)
        );

        // Here is a hack:
        // create a second "drivetrain" to operate the intake
        // because they work the same way...
        this.addTeleOpMapper(
                new CBArcadeDriveMapper(this, requestData.intake)
                        .setAxes(operYAxis, null, operXAxis)
        );

        this.addTeleOpMapper(
                new OperatorMapper(this)
        );

        // setup sensor mapper(s)
        this.addSensorMapper(
                new SensorMapper(this)
        );

        this.addSensorMapper(
                new CBMotorMonitorMapper(this)
                        .add(dtFrontLeftMotor)
                        .add(dtFrontRightMotor)
                        .add(dtBackLeftMotor)
                        .add(dtBackRightMotor)
                        .add(mainLiftMotorBack)
                        .add(mainLiftMotorFront)
                        .add(intakeLiftMotor)
        );
    }

    private void defineControllers() {
        // setup robot controllers
        this.addRobotController(
                new CBDifferentialDriveController(this, controlData.drivetrain)
                        .addLeftDriveModule(
                                new CBDriveModule(new CB2DVector(-1, 0), 0)                                        
                                        .addSpeedControllerArray(
                                                new CBVictorArrayController()
                                                        .setDriveMode(CBEnums.CBDriveMode.Power)
                                                        .addSpeedController(dtFrontLeftMotor)
                                                        .addSpeedController(dtBackLeftMotor)
                                                        .setEncoder(dtLeftEncoder)
                                                        .setErrorCorrection(
                                                                new CBPIDErrorCorrection()
                                                                        .setConstants(new double[]{1.5, 0, 0.0015}
                                                                        )
                                                        )
                                        )
                        )
                        .addRightDriveModule(
                                new CBDriveModule(new CB2DVector(1, 0), 180)
                                        .addSpeedControllerArray(
                                                new CBVictorArrayController()
                                                        .setDriveMode(CBEnums.CBDriveMode.Power)
                                                        .addSpeedController(dtFrontRightMotor)
                                                        .addSpeedController(dtBackRightMotor)
                                                        .setEncoder(dtRightEncoder)
                                                        .setErrorCorrection(
                                                                new CBPIDErrorCorrection()
                                                                        .setConstants(new double[]{1.5, 0, 0.0015}
                                                                        )
                                                        )
                                        )
                        )
        );

        // yup again with the second drivetrain for the intake
        this.addRobotController(
                new CBDifferentialDriveController(this, controlData.intake)
                        .addLeftDriveModule(
                                new CBDriveModule(new CB2DVector(-6, 0), 0)
                                        .addSpeedControllerArray(
                                                new CBVictorArrayController()
                                                        .setDriveMode(CBEnums.CBDriveMode.Power)
                                                        .addSpeedController(intakeLeftMotor)
                                        )
                        )
                        .addRightDriveModule(
                                new CBDriveModule(new CB2DVector(6, 0), -180)
                                        .addSpeedControllerArray(
                                                new CBVictorArrayController()
                                                        .setDriveMode(CBEnums.CBDriveMode.Power)
                                                        .addSpeedController(intakeRightMotor)
                                        )
                        )
        );

        // main lift controller definition
        this.addRobotController(
                // hardware configurations are done here.
                // there are other "soft" configurations done in the mapper
                // that include margins (which trigger slow motion)
                // and in this case a encoder based top limit
                new CBLiftController(this, 
                        controlData.mainLift, 
                        new CBVictorArrayController()
                                .addSpeedController(mainLiftMotorFront)
                                .setDriveMode(CBEnums.CBDriveMode.Power)
                        )
                        // setData allows you to pick a CBLinearControllerData variable
                        // in controlData to use for this lift. There might be several
                        // lift controllers and each one would be controlled by a different
                        // CBLinearControllerData object in controlData.
                        // Moved into constructor...
                        //.setData(controlData.mainLift)
                        // set a lower limit switch this is a hard limit
                        .setBottomLimit(mainLiftLimit)
                        // set the encoder for the lift
                        .setEncoder(mainLiftEncoder)
                        // attach a speed controller array to drive the lift
                        // Moved into constructor...
                        //.setSpeedControllerArray(
                        //        new CBVictorArrayController()
                        //                .addSpeedController(mainLiftMotorFront)
                        //                .setDriveMode(CBEnums.CBDriveMode.Power)
                        //)
        );

        // intake lift controller definition
        this.addRobotController(
                // hardware configurations are done here.
                // there are other "soft" configurations done in the mapper
                // that include margins (which trigger slow motion)
                // and in this case an encoder based top limit
                new CBLiftController(this,
                        controlData.intakeLift,
                        new CBVictorArrayController()
                                .addSpeedController(intakeLiftMotor)
                                .setDriveMode(CBEnums.CBDriveMode.Power)
                        )
                        // setData allows you to pick a CBLinearControllerData variable
                        // in controlData to use for this lift. There might be several
                        // lift controllers and each one would be controlled by a different
                        // CBLinearControllerData object in controlData.
                        //.setData(controlData.intakeLift)
                        // set a lower limit switch this is a hard limit
                        //.setBottomLimit(mainLiftLimit)
                        // set the encoder for the lift
                        .setEncoder(intakeLiftEncoder)
                        // attach a speed controller array to drive the lift
                        //.setSpeedControllerArray(
                        //        new CBVictorArrayController()
                        //                .addSpeedController(intakeLiftMotor)
                        //                .setDriveMode(CBEnums.CBDriveMode.Power)
                        //)
        );
    }

    private void defineBehaviors() {
        // setup behaviors
        this.addBehavior(
                new CBStdDriveBehavior(this, 
                        requestData.drivetrain, 
                        controlData.drivetrain)
        );
        this.addBehavior(new MainLiftBehavior(this));
        this.addBehavior(new IntakeLiftBehavior(this));

        // while this looks like a drivetrain, its an intake.
        this.addBehavior(
                new CBStdDriveBehavior(this, 
                        requestData.intake, 
                        controlData.intake)
        );

        this.addBehavior(new OperatorBehavior(this));
    }
}