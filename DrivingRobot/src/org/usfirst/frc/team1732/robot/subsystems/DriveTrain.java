package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.DriveWithJoysticks;

import com.ctre.MotorControl.CANTalon;
import com.ctre.MotorControl.SmartMotorController.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static final String NAME = "Drive Train";

	public DriveTrain() {
		super(NAME);
		configureTalons();
	}

	private final CANTalon leftMaster = new CANTalon(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
	private final CANTalon left1 = new CANTalon(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
	private final CANTalon left2 = new CANTalon(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);

	private final CANTalon rightMaster = new CANTalon(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
	private final CANTalon right1 = new CANTalon(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
	private final CANTalon right2 = new CANTalon(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

	private void configureTalons() {
		System.out.println("YO, THE TALONS ARE CONFIGURING THOUGH");
		// reverses whole left side
		leftMaster.setInverted(true);
		left1.changeControlMode(TalonControlMode.Follower);
		left1.set(leftMaster.getDeviceID());
		left2.changeControlMode(TalonControlMode.Follower);
		left2.set(leftMaster.getDeviceID());

		// sets right motors to follow right master
		right1.changeControlMode(TalonControlMode.Follower);
		right1.set(rightMaster.getDeviceID());
		right2.changeControlMode(TalonControlMode.Follower);
		right2.set(rightMaster.getDeviceID());

		leftMaster.set(0);
		rightMaster.set(0);

		System.out.println("YOY MY BAD BOY IT MADE A FUNNY JUNKY");
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
		System.out.println("YO, I HEARD YOU LIKED INITIALIZING DEFAULT COMMANDS");
		System.out.println("SO I INITIALIZED YOUR DEFAULT COMMAND TO SUFFICE FOR YOUR LIKE OF INITIALIZING DEFAULT COMMANDS");
	}

	public void driveWithJoysticks(double left, double right) {
		System.out.println("YO, THE ROBOT IS DRIVING WITH JOYSTICKS AND SUCH");
		System.out.println(left + " " + right);
		leftMaster.set(left);
		rightMaster.set(right);
	}

}
