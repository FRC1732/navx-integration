package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.DriveWithJoysticks;

//import com.ctre.MotorControl.SmartMotorController.TalonControlMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static final String NAME = "Drive Train";

	//CONTROL SHIFT F IS YOUR FRIEND
	
	public DriveTrain() {
		super(NAME);
		configureTalons();
	}

	private final TalonSRX leftMaster = new TalonSRX(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
	private final TalonSRX left1 = new TalonSRX(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
	private final TalonSRX left2 = new TalonSRX(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);

	private final TalonSRX rightMaster = new TalonSRX(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
	private final TalonSRX right1 = new TalonSRX(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
	private final TalonSRX right2 = new TalonSRX(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

	private void configureTalons() {
		System.out.println("YO, THE TALONS ARE CONFIGURING THOUGH");
		// reverses whole left side
		leftMaster.setInverted(true);
		left1.setInverted(true);
		left2.setInverted(true);
		rightMaster.setInverted(false);
		left1.set(ControlMode.Follower, leftMaster.getDeviceID());
		left2.set(ControlMode.Follower, leftMaster.getDeviceID());

		// sets right motors to follow right master
		// right1.changeControlMode(TalonControlMode.Follower);
		right1.set(ControlMode.Follower, rightMaster.getDeviceID());
		// right2.changeControlMode(TalonControlMode.Follower);
		right2.set(ControlMode.Follower, rightMaster.getDeviceID());

		leftMaster.set(ControlMode.PercentOutput, 0);
		rightMaster.set(ControlMode.PercentOutput, 0);

		System.out.println("YOY MY BAD BOY IT MADE A FUNNY JUNKY");
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
		// System.out.println("YO, I HEARD YOU LIKED INITIALIZING DEFAULT
		// COMMANDS");
		// System.out.println("SO I INITIALIZED YOUR DEFAULT COMMAND TO SUFFICE
		// FOR YOUR LIKE OF INITIALIZING DEFAULT COMMANDS");
	}
	
	private static final double multiplier = 0.7;
	
	public void driveWithJoysticks(double left, double right) {
		// System.out.println("YO, THE ROBOT IS DRIVING WITH JOYSTICKS AND
		// SUCH");
		//System.out.println(left + " " + right);
		leftMaster.set(ControlMode.PercentOutput, multiplier*left);
		rightMaster.set(ControlMode.PercentOutput, multiplier*right);
	}
	
	//AUTON
	private static final double autoSpped = 100;
	public void rotate(double d){
		leftMaster.set(ControlMode.PercentOutput, -Math.min(d/autoSpped, 0.5));
		rightMaster.set(ControlMode.PercentOutput, Math.min(d/autoSpped, 0.5));
    }

}
