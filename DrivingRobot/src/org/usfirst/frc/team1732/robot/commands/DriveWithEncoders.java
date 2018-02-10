package org.usfirst.frc.team1732.robot.commands;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithEncoders extends Command {
	
	public double P = 0.3;
	public double I = 0;
	public double D = 0;
	
<<<<<<< HEAD
	public double INCHES_OFF = 24.0;
	
	//public static double INCHES = 36;
	private PIDController leftDistance;
	private PIDController rightDistance;
=======
	public static double INCHES_OFF = 12;// how far away the PID loop should kick in.
	
	//public static double INCHES = 36;

	private static PIDController leftDistance = new PIDController(P, I, D, new PIDSource() { //PID = multiplier, error to voltage check, overshoot adjustment
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}
		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}
		@Override
		public double pidGet() {
			double val =  (inches - Robot.driveTrain.getLeftDistance()) / (INCHES_OFF);
//			System.out.println("Left PIDGet: " + val);
			return val;
		}
		
	}, System.out::println);
	private static PIDController rightDistance = new PIDController(P, I, D, new PIDSource() {
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}
		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}
		@Override
		public double pidGet() {
			return (inches - Robot.driveTrain.getRightDistance()) / (INCHES_OFF);
		}
		
	}, System.out::println);
	
	static {
		LiveWindow.add(leftDistance);
    	LiveWindow.add(rightDistance);
	}
>>>>>>> 770d07ba6f0d5f7b84ce8c92defcd6ad9dc44ada
	
	private double inches; //Setpoint
	private final double TOLERANCE = 0.1;
	
		
    public DriveWithEncoders(double inches) {
        requires(Robot.driveTrain);
		this.inches = inches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	P = Robot.config.getValue("drivetrain.p", P);
//    	I = Robot.config.getValue("drivetrain.i", I);
//    	D = Robot.config.getValue("drivetrain.d", D);
//    	INCHES_OFF = Robot.config.getValue("drivetrain.inches", INCHES_OFF);
    	System.out.println(P);
    	System.out.println(I);
    	System.out.println(D);
    	System.out.println(INCHES_OFF);
    	leftDistance = new PIDController(P, I, D, new PIDSource() { //PID = multiplier, error to voltage check, overshoot adjustment
    		@Override
    		public void setPIDSourceType(PIDSourceType pidSource) {}
    		@Override
    		public PIDSourceType getPIDSourceType() {
    			return PIDSourceType.kDisplacement;
    		}
    		@Override
    		public double pidGet() {
    			return (inches - Robot.driveTrain.getLeftDistance()) / (INCHES_OFF);
    		}
    		
    	}, this::u);
    	rightDistance = new PIDController(P, I, D, new PIDSource() {
    		@Override
    		public void setPIDSourceType(PIDSourceType pidSource) {}
    		@Override
    		public PIDSourceType getPIDSourceType() {
    			return PIDSourceType.kDisplacement;
    		}
    		@Override
    		public double pidGet() {
    			return (inches - Robot.driveTrain.getRightDistance()) / (INCHES_OFF);
    		}
    		
    	}, this::u);
    	Robot.driveTrain.resetEncoders();
    	
    	leftDistance.setAbsoluteTolerance(TOLERANCE);
    	leftDistance.enable();
    	
    	rightDistance.setAbsoluteTolerance(TOLERANCE);
    	rightDistance.enable();
    	
    	//Robot.driveTrain.resetEncoderPIDValues();
    }
    private void u(double u) {}
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	System.out.println("Left OUTPUT: " + leftDistance.get());
//    	System.out.println("Right OUTPUT: " + rightDistance.get());
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
