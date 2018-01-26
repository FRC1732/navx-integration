package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.EncoderReader;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithEncoders extends Command {

	private double inches;
	
	//public static final EncoderReader leftReader = Robot.driveTrain.leftEncoder.makeReader();
	//public static final EncoderReader rightReader = Robot.driveTrain.rightEncoder.makeReader();
	
    public DriveWithEncoders(double inches) {
        requires(Robot.driveTrain);
		this.inches = inches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {   	
    	//leftReader.zero();
    	//rightReader.zero();
    	
    	if(inches < 0){
    		Robot.driveTrain.drive(-1);
    	}
    	else if(inches > 0){
    		Robot.driveTrain.drive(1);
    	}
    	else{
    		Robot.driveTrain.drive(0);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	SmartDashboard.putNumber("EncoderLeft", leftReader.getPosition());
//    	SmartDashboard.putNumber("EncoderRight", rightReader.getPosition());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(inches > 0){ //croc boss
        	//return leftReader.getPosition() > inches && rightReader.getPosition() > inches;
        }
        if(inches < 0){
        	//return leftReader.getPosition() < inches && rightReader.getPosition() < inches;
        }
        else{
        	return true;
        }
        return false;
        
        /*
         * 				>.<
         */
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.drive(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.drive(0);
    }
}
