package org.usfirst.frc.team1732.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1732.robot.Robot;

/**
 *
 */
public class TurnToAngle extends Command {
	
	public int stop = 0;
	public double angle = 0;
	public double range = 4;
	
	public TurnToAngle(double angle) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
		this.angle = angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//GET HEADING IS ONLY CALLED ONCE BECAUSE SUBSYSTEMS ARE NOT RUN CONTINUOUSLY
		double heading = Robot.navx.getHeading();
		
		SmartDashboard.putNumber("Heading", heading);
		System.out.println("Running");
		System.out.println(heading);
		
		double speed = 0.5;
		
		if(Math.abs(heading - angle) < range * 1.5) {
			speed = (Math.abs(heading - angle) / (range * 1.5)) / 2;
		}
		
		if (heading < (angle - range / 2.0)) {
			Robot.driveTrain.rotateCW(speed);
			stop = 0;
		} else if (heading > (angle + range / 2.0)) {
			Robot.driveTrain.rotateCCW(speed);
			stop = 0;
		} else {
			Robot.driveTrain.stop();
			stop++;
		}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return stop > 5;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
