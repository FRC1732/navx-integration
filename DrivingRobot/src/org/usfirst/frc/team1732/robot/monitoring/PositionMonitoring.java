package org.usfirst.frc.team1732.robot.monitoring;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;

public class PositionMonitoring extends Command {
	private double x = 0;
	private double y = 0;
	private double z = 0;
	private double heading = 0;
	private AHRS ahrs;
	
	public PositionMonitoring(AHRS ahrs) {
		this.ahrs = ahrs;
	}
	
	@Override
	protected void execute() {
		heading = ahrs.getCompassHeading();
		x = ahrs.getRawGyroX();
		y = ahrs.getRawGyroY();
		z = ahrs.getRawGyroZ();
		System.out.println("heading: " + heading);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getHeading() {
		return heading;
	}
}
