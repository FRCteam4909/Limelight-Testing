/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.operator.controllers.BionicF310;
import frc.robot.operator.generic.BionicJoystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  static Vision vision;
  private static DifferentialDrive differentialDrive;
  private static BionicJoystick driverGamepad;

  private final double kP = 0.07;

  // 2,1 Left
  // 4,4 Right

  @Override
  public void robotInit() {
    driverGamepad = new BionicF310(0, 0.15, 0);
    vision = new Vision();
    vision.setLights(1);

    differentialDrive = new DifferentialDrive(
      new SpeedControllerGroup(new WPI_TalonSRX(2), new WPI_VictorSPX(1)),
      new SpeedControllerGroup(new WPI_TalonSRX(4), new WPI_VictorSPX(4))
    );

  }

  @Override
  public void robotPeriodic() {
    vision.updateVisionDashboard();

    if(driverGamepad.getRawButton(6))
      vision.setLights(3);
    else
      vision.setLights(1);
    
    if(driverGamepad.getRawButton(5)){
      differentialDrive.curvatureDrive(
        -driverGamepad.getThresholdAxis(BionicF310.LY),
        vision.getXOffset() * kP,
        false
      );
    } else {
      differentialDrive.arcadeDrive(
        -driverGamepad.getThresholdAxis(BionicF310.LY),
        driverGamepad.getThresholdAxis(BionicF310.RX)
      );
    }
  }
}
