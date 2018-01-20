package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.DriveWithArcade;
import org.usfirst.frc.team1732.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.commands.DriveWithStick;
import org.usfirst.frc.team1732.robot.commands.TurnToAngle;

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
		//setDefaultCommand(new DriveWithJoysticks());
		setDefaultCommand(new DriveWithArcade());
		//setDefaultCommand(new DriveWithStick());
	}
	
	private static final double PRECISION = 1;
	private static final double multiplier = 0.7;
	
	public void driveWithJoysticks(double left, double right) {
		leftMaster.set(ControlMode.PercentOutput, multiplier*left);
		//Go forward on the left side as much the left throttle is pushed upwards
		rightMaster.set(ControlMode.PercentOutput, multiplier*right);
		//Go forward on the right side as much the right throttle is pushed upwards
	}
	
	public void driveWithArcade(double turn, double throttle) {
		leftMaster.set(ControlMode.PercentOutput, multiplier * (throttle/2 + turn/2));
		rightMaster.set(ControlMode.PercentOutput, multiplier * (throttle/2 - turn/2));
		//I'll be real, I don't even know
	}
	
	public void driveWithStick(double x, double y, double angle) {
		double destinationAngle = Math.atan(y / x); //finds angle currently turned to
		if(x < 0){ //Account for negative x (domain of arctangent)
			destinationAngle += Math.PI;
		}
		double x2 = Math.pow(x, 2); //Holds value x^2
		double y2 = Math.pow(y, 2); //Holds value y^2
		double speed = Math.sqrt(x2 + y2); //Finds hypotenuse of triangle
		
		boolean close = (angle >= destinationAngle - PRECISION && angle <= destinationAngle + PRECISION);
		
		while(!close){ //While the angle is not close to the destination angle
			new TurnToAngle(destinationAngle); //Turn to the destination angle
			if(close){
				//If the angle is near the destination angle:
				//180 >= 48 && 180 <= 52
				//50 >= 49 && 50 <= 51
				break; //Stop rotating...
			}
		}
		//And instead, go forward at the speed of the hypotenuse of the triangle
		leftMaster.set(ControlMode.PercentOutput, multiplier*speed);
		rightMaster.set(ControlMode.PercentOutput, multiplier*speed);
	}
	
	/*
	 * AUTON BEGINS BELOW HERE
	 */
	
	//AUTON Pommes
	private static final double autoSpped = 100;
	public void rotate(double d){
		leftMaster.set(ControlMode.PercentOutput, -Math.min(d/autoSpped, 0.5));
		rightMaster.set(ControlMode.PercentOutput, Math.min(d/autoSpped, 0.5));
    }
	
	//AUTON Visaya
	public void rotateCW(double speed){
		//Left is inverted
		leftMaster.set(ControlMode.PercentOutput, -speed);
		rightMaster.set(ControlMode.PercentOutput, speed);
    }
	
	public void rotateCCW(double speed){
		//Right is inverted
		leftMaster.set(ControlMode.PercentOutput, speed);
		rightMaster.set(ControlMode.PercentOutput, -speed);
	}
	
	public void stop(){
		leftMaster.set(ControlMode.PercentOutput, 0);
		rightMaster.set(ControlMode.PercentOutput, 0);
    }

}
