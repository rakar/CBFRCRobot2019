# CBFRCRobot2019
VSCode Project for FRC 2019 w/ cyborg

This is an pre-season test of the new official VS Code toolchain. 
In addition to the VS Code install prescribed by First, I'm currently also using GitLens to make Git integration reasonable. 
In order to add cyborg to the default iterative project there are several steps. 
* Create a libs folder in the root of the project and copy the cyborg .jar file into it. 
* Edit Gradle build and edit the dependencies section adding the last line (referencing libs and *.jar):
```
// Defining my dependencies. In this case, WPILib (+ friends), CTRE Toolsuite (Talon SRX)
// and NavX.
dependencies {
    compile wpilib()
    compile ctre()
    compile navx()
    compile fileTree(dir: "libs", include: ['*.jar'])
}
```
* Add your own cyborg robot class (RobotCB in this project). I do this by making the delibate mistakes of simply typing
```java
package frc.robot;

public class RobotCB extends Cyborg {
}
```
And then using the "yellow light bulb" (twice) to do the rest. 
```java
package frc.robot;

import org.montclairrobotics.cyborg.Cyborg;

public class RobotCB extends Cyborg {

    @Override
    public void cyborgAutonomousInit() {

    }

    @Override
    public void cyborgDisabledInit() {

    }

    @Override
    public void cyborgInit() {

    }

    @Override
    public void cyborgTeleopInit() {

    }

    @Override
    public void cyborgTestInit() {

    }
}
```
* Finally, take the scary jump of faith and replace the Robot class with:
```java
package frc.robot;
public class Robot extends RobotCB {
}
```
