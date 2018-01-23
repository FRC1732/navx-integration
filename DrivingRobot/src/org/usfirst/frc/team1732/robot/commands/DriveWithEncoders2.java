package org.usfirst.frc.team1732.robot.commands;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveWithEncoders2 extends CommandGroup {

    public DriveWithEncoders2(double driveForwardDistance, double driveBackDistance) {
	this(driveForwardDistance, driveBackDistance, driveBackDistance);
    }

    public DriveWithEncoders2(double driveForwardDistance, double leftDriveBackDistance, double rightDriveBackDistance) {
	this(() -> driveForwardDistance, () -> leftDriveBackDistance, () -> rightDriveBackDistance);
    }

    public DriveWithEncoders2(DoubleSupplier driveForwardDistance, DoubleSupplier driveBackDistance) {
	this(driveForwardDistance, driveBackDistance, driveBackDistance);
    }

    public DriveWithEncoders2(DoubleSupplier driveForwardDistance, DoubleSupplier leftDriveBackDistance,
	    DoubleSupplier rightDriveBackDistance) {
	// drive into gear peg
	// addSequential(new TurnWithVision(0));
	addSequential(new SetEncoderPID(0.1, 0, 0));
	addSequential(new DriveEncodersGetSetpointAtRuntime(driveForwardDistance));
	addSequential(new Pause(5000));
	addSequential(new SetEncoderPID(.05, 0, 0));
	addSequential(new DriveEncodersGetSetpointAtRuntime(leftDriveBackDistance, rightDriveBackDistance));
	addSequential(new ResetEncoderPID());
    }

    @Override
    public void interrupted() {
	end();
    }

    @Override
    public void end() {
	Robot.driveTrain.resetEncoderPID();
    }
}
