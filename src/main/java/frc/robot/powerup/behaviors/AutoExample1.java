package frc.robot.powerup.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.behaviors.CBAutonomous;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CB2DVector;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTarget1D;

import frc.robot.powerup.data.RequestData;


public class AutoExample1 extends CBAutonomous {
    private RequestData rd;
    private double drivetrainAverageEncoderValueAtTransition;
    private final static int mainDriveDist = 1000; // this is a bad value!
    private final static int littleDriveDist = 500; // this is a bad value!
    private final static int mainLiftDist = 1000; // this is a bad value!
    private CBTarget1D leftTarget = new CBTarget1D().setTarget(90,4);
    private CBTarget1D rightTarget = new CBTarget1D().setTarget(-90,4);
    private CBStdDriveRequestData drd = (CBStdDriveRequestData)rd.drivetrain;
    private CBStdDriveRequestData intake = (CBStdDriveRequestData)rd.intake;
    private StateMachine stateMachine = new StateMachine();

    enum States {Start, ValidateLift, DriveAndLift, TurnLeft, TurnRight, DriveALittle, Eject, Done}

    private class StateMachine extends CBStateMachine<States> {
        StateMachine() {
            super(States.Start);
            setLoopMode(CBStateMachineLoopMode.Looping);
        }

        @Override
        public void calcNextState() {
            switch (currentState) {
                case Start:
                nextState = States.ValidateLift;
                    break;
                case ValidateLift:
                    if(rd.mainLiftLimitValue) {
                        if((rd.fieldPosition==rd.nearSwitchSide)) {
                            nextState = States.DriveAndLift;
                        } else {
                            nextState = States.Done;
                        }
                    }
                    break;
                case DriveAndLift:
                    if((rd.drivetrainAverageEncoderValue-drivetrainAverageEncoderValueAtTransition)>mainDriveDist
                            && rd.mainLiftEncoderValue>mainLiftDist) {
                        if(rd.fieldPosition=='L') {
                            nextState = States.TurnRight;
                        } else {
                            nextState = States.TurnLeft;
                        }
                    }
                    break;
                case TurnLeft:
                    if(leftTarget.isOnTarget()) {
                        nextState = States.DriveALittle;
                    }
                    break;
                case TurnRight:
                    if(rightTarget.isOnTarget()) {
                        nextState = States.DriveALittle;
                    }
                    break;
                case DriveALittle:
                    if((rd.drivetrainAverageEncoderValue-drivetrainAverageEncoderValueAtTransition)>littleDriveDist) {
                        nextState = States.Eject;
                    }
                    break;
                case Eject:
                    if(secondsInState>3) {
                        nextState = States.Done;
                    }
                    break;
                case Done:
                    break;
            }
        }

        @Override
        public void doTransition() {
            drd.active=true;
            drd.direction.setXY(0,0); // half speed forward (assuming power mode)
            drd.gyroLockValue = 0;
            drd.gyroLockActive = false;
            drd.rotation = 0;
            rd.mainLiftUp = false;
            rd.mainLIftDown = false;
            drivetrainAverageEncoderValueAtTransition = rd.drivetrainAverageEncoderValue;
        }

        @Override
        protected void doCurrentState() {
            //SmartDashboard.putString("do Current State:", currentState.name());
            switch (currentState) {
                case Start:
                    drd.active = true;
                    drd.direction = new CB2DVector(0,0);
                    drd.rotation = 0;
                    intake.active=true;
                    intake.direction.setXY(0,-.20); // bad value - Draw cube in?
                    intake.rotation = 0;
                    break;
                case ValidateLift:
                    rd.mainLiftUp = false;
                    rd.mainLIftDown = true;
                    intake.active=true;
                    intake.direction.setXY(0,-.20); // bad value - Draw cube in?
                    intake.rotation = 0;
                    break;
                case DriveAndLift:
                    if((rd.drivetrainAverageEncoderValue-drivetrainAverageEncoderValueAtTransition)>mainDriveDist) {
                        drd.active=true;
                        drd.direction.setXY(0,.5); // bad value - half speed forward (assuming power mode)
                        drd.gyroLockValue = 0;
                        drd.gyroLockActive = true;
                        drd.rotation = 0;
                    }
                    else {
                        drd.active=true;
                        drd.direction.setXY(0,0); // half speed forward (assuming power mode)
                        drd.gyroLockValue = 0;
                        drd.gyroLockActive = false;
                        drd.rotation = 0;
                    }
                    if (rd.mainLiftEncoderValue>mainLiftDist) {
                        rd.mainLiftUp = true;
                        rd.mainLIftDown = false;
                    } else {
                        rd.mainLiftUp = false;
                        rd.mainLIftDown = false;
                    }
                    break;
                case TurnLeft:
                    drd.active=true;
                    drd.direction.setXY(0,0); // half speed forward (assuming power mode)
                    drd.gyroLockValue = 0;
                    drd.gyroLockActive = false;
                    drd.rotation = .3; // bad value
                    break;
                case TurnRight:
                    drd.active=true;
                    drd.direction.setXY(0,0); // half speed forward (assuming power mode)
                    drd.gyroLockValue = 0;
                    drd.gyroLockActive = false;
                    drd.rotation = -.3; // bad value
                    break;
                case DriveALittle:
                    drd.active=true;
                    drd.direction.setXY(0,.25); // bad value - quarter speed forward (assuming power mode)
                    drd.gyroLockValue = 0;
                    drd.gyroLockActive = false;
                    drd.rotation = 0;
                    break;
                case Eject:
                    rd.shootCube = true;
                    break;
                case Done:
                    rd.shootCube = false;
                    break;
            }
        }
    }

    public AutoExample1(Cyborg robot) {
        super(robot);
        rd = ((RequestData)Cyborg.requestData);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        stateMachine.update();
    }
}
