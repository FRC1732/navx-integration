package org.usfirst.frc.team1732.robot.subsystems;

import org.omg.CORBA.TRANSACTION_UNAVAILABLE;
import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.DriveWithArcade;
import org.usfirst.frc.team1732.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.commands.DriveWithStick;
import org.usfirst.frc.team1732.robot.commands.TurnToAngle;

//import com.ctre.MotorControl.SmartMotorController.TalonControlMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static final String NAME = "Drive Train";
	private TurnToAngle angleControl;

	// CONTROL SHIFT F IS YOUR FRIEND

	public DriveTrain() {
		super(NAME);
		configureTalons();
		angleControl = new TurnToAngle(0, false);
	}

	private final TalonSRX leftMaster = new TalonSRX(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
	private final TalonSRX left1 = new TalonSRX(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
	private final TalonSRX left2 = new TalonSRX(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);

	private final TalonSRX rightMaster = new TalonSRX(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
	private final TalonSRX right1 = new TalonSRX(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
	private final TalonSRX right2 = new TalonSRX(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

	private void configureTalons() {
		leftMaster.setInverted(true);
		left1.setInverted(true);
		left2.setInverted(true);
		rightMaster.setInverted(false);

		left1.set(ControlMode.Follower, leftMaster.getDeviceID());
		left2.set(ControlMode.Follower, leftMaster.getDeviceID());
		right1.set(ControlMode.Follower, rightMaster.getDeviceID());
		right2.set(ControlMode.Follower, rightMaster.getDeviceID());

		leftMaster.set(ControlMode.PercentOutput, 0);
		rightMaster.set(ControlMode.PercentOutput, 0);
	}

	public void initDefaultCommand() {
		// setDefaultCommand(new DriveWithJoysticks());
		// setDefaultCommand(new DriveWithArcade());
		setDefaultCommand(new DriveWithStick());
	}

	private static final double PRECISION = 10;
	// using 50% speed
	private static final double multiplier = 0.5;

	public void driveWithJoysticks(double left, double right) {
		leftMaster.set(ControlMode.PercentOutput, multiplier * left);
		// Go forward on the left side as much the left throttle is pushed upwards
		rightMaster.set(ControlMode.PercentOutput, multiplier * right);
		// Go forward on the right side as much the right throttle is pushed upwards
	}

	public void driveWithArcade(double turn, double throttle) {
		turn = turn * -1;
		leftMaster.set(ControlMode.PercentOutput, multiplier * (throttle + turn));
		rightMaster.set(ControlMode.PercentOutput, multiplier * (throttle - turn));
		// I'll be real, I don't even know
	}

	private boolean close = false;

	public void driveWithStick(double x, double y, double angle) {
		x = -x;
		double destinationAngle = Math.atan(y / x); // finds angle currently turned to
		if (destinationAngle < 0) {
			destinationAngle = Math.PI * 2 + destinationAngle;
		}

		if (x < 0) { // Account for negative x (domain of arctangent)
			destinationAngle += Math.PI;
		}
		if (Math.abs(x) < 0.1) {
			close = true;
		}
		destinationAngle = Math.toDegrees(destinationAngle) % 360;
		SmartDashboard.putNumber("DestAngle", destinationAngle);
		double speed = -Math.sqrt(x * x + y * y); // Finds hypotenuse of triangle

		// if not close, then run turn to angle
		if (!close) {
			angleControl.setAngle(destinationAngle);
			angleControl.runSync();
			// if angleControl hit target, close = true
			if (angleControl.isDone()) {
				close = true;
			}

		} else {
			// if close, recheck close, and run forward
			close = Math.abs(destinationAngle - angle) <= PRECISION;
			// And instead, go forward at the speed of the hypotenuse of the triangle
			leftMaster.set(ControlMode.PercentOutput, multiplier * speed);
			rightMaster.set(ControlMode.PercentOutput, multiplier * speed);
		}
	}

	/*
	 * AUTON BEGINS BELOW HERE
	 */

	// AUTON Pommes
	private static final double autoSpped = 100;

	public void rotate(double d) {
		leftMaster.set(ControlMode.PercentOutput, -Math.min(d / autoSpped, 0.5));
		rightMaster.set(ControlMode.PercentOutput, Math.min(d / autoSpped, 0.5));
	}

	// AUTON Visaya
	public void rotateCW(double speed) {
		// Left is inverted
		leftMaster.set(ControlMode.PercentOutput, -speed);
		rightMaster.set(ControlMode.PercentOutput, speed);
	}

	public void rotateCCW(double speed) {
		// Right is inverted
		leftMaster.set(ControlMode.PercentOutput, speed);
		rightMaster.set(ControlMode.PercentOutput, -speed);
	}

	public void stop() {
		leftMaster.set(ControlMode.PercentOutput, 0);
		rightMaster.set(ControlMode.PercentOutput, 0);
	}

}
