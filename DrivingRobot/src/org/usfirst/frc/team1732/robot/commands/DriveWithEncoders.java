package org.usfirst.frc.team1732.robot.commands;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.EncoderReader;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithEncoders extends Command {

	private static PIDController leftDistance = new PIDController(0.7,0.0,0.5, new PIDSource() { //PID = multiplier, error to voltage check, overshoot adjustment
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}
		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}
		@Override
		public double pidGet() {
			double val =  (inches - Robot.driveTrain.getLeftDistance()) / (inches*0.5);
			System.out.println("Left PIDGet: " + val);
			return val;
		}
		
	}, System.out::println);
	private static PIDController rightDistance = new PIDController(0.7,0.0,0.5, new PIDSource() {
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}
		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}
		@Override
		public double pidGet() {
			return (inches - Robot.driveTrain.getRightDistance()) / (inches*0.5);
		}
		
	}, System.out::println);
	
	static {
		LiveWindow.add(leftDistance);
    	LiveWindow.add(rightDistance);
	}
	
	private static double inches; //Setpoint
	private double output = 0;
	private final double TOLERANCE = 0.1;
	
		
    public DriveWithEncoders(double inches) {
        requires(Robot.driveTrain);
		DriveWithEncoders.inches = inches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetEncoders();
    	
    	leftDistance.setAbsoluteTolerance(TOLERANCE);
    	leftDistance.enable();
    	
    	rightDistance.setAbsoluteTolerance(TOLERANCE);
    	rightDistance.enable();
    	
    	//Robot.driveTrain.resetEncoderPIDValues();
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveTrain.driveIndependant(leftDistance.get(), rightDistance.get());
    	
    	/*
		driveTrain.setLeftEncoderSetpoint(inches);
		driveTrain.setRightEncoderSetpoint(inches);
		double leftOutput = driveTrain.getLeftAdjustment();
		double rightOutput = driveTrain.getRightAdjustment();
		
		System.out.println("Right Output: " + rightOutput);
		System.out.println("Left Output: " + leftOutput);
		
		if(leftOutput > 1){
			leftOutput = 1;
		}
		if(leftOutput < -1){
			leftOutput = -1;
		}
		
		if(rightOutput > 1){
			rightOutput = 1;
		}
		if(rightOutput < -1){
			rightOutput = -1;
		}
		
		System.out.println("Right Output: " + rightOutput);
		System.out.println("Left Output: " + leftOutput);
		
		driveTrain.driveIndependant(leftOutput, rightOutput);
		*/
    	
    	if(leftDistance.onTarget()) {
    		leftDistance.disable();
    	}
    	if(rightDistance.onTarget()) {
    		rightDistance.disable();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (!leftDistance.isEnabled() || leftDistance.onTarget()) &&
        		(!rightDistance.isEnabled() || rightDistance.onTarget());
        
        /*
         * 				>.<
         */
    }

    // Called once after isFinished returns true
    protected void end() {
    	leftDistance.disable();
    	rightDistance.disable();
    	Robot.driveTrain.drive(0);
    }
}
