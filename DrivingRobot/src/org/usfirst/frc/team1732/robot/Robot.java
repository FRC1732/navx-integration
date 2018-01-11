
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.monitoring.PositionMonitoring;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team1732.robot.subsystems.Grabber;
import org.usfirst.frc.team1732.robot.subsystems.Roller;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

	public static OI oi;
    public static DriveTrain driveTrain;
    public static Grabber grabber;
    public static Roller roller;
    public static AHRS ahrs;
    
    //Compressor c = new Compressor(0);

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("YO, THE ROBOT IS STARTING THOUGH");
		try {
		    System.out.println("Robot turning on");
		    initializeSubsystems();
		    oi = new OI();
		    //c.setClosedLoopControl(true);
		    ahrs = new AHRS(Port.kMXP);
		    System.out.println(ahrs.isConnected());
		    System.out.println("THE THING DIDNOT FAIL!!1!");
		} catch (Exception e) {
		    e.printStackTrace();
		    System.out.println(e);
		    System.out.println(e.getMessage());
		}
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		System.out.println("teleopInit Called");
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		Scheduler.getInstance().removeAll(); // Cancels commands
		//DriveWithJoysticks cb = new DriveWithJoysticks();
		//cb.start();
		PositionMonitoring pm = new PositionMonitoring(ahrs);
		pm.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//System.out.println("YO, THE ROBOT IS TELEOP-ING THOUGH");
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void initializeSubsystems(){
		System.out.println("Subsystems Initialized");
		driveTrain = new DriveTrain();
		grabber = new Grabber();
		roller = new Roller();
	}
}
