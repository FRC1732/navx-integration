package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/*
 * Use this class for if trying to access encoder through talon. Don't use for configuring motion profiling stuff.
 */
public class TalonEncoder extends EncoderBase {

	private final TalonSRX talon;
	private double distancePerPulse;

	public TalonEncoder(TalonSRX talon) {
		this.talon = talon;
		talon.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_5Ms, 10);
		talon.configVelocityMeasurementWindow(2, 10);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);

		// talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, pidIdx,
		// Robot.CONFIG_TIMEOUT);
		// talon.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference, pidIdx,
		// timeoutMs);
		// talon.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, pidIdx,
		// timeoutMs);
		// talon.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0, pidIdx,
		// timeoutMs);
		// talon.configSelectedFeedbackSensor(FeedbackDevice.SoftwareEmulatedSensor,
		// pidIdx, timeoutMs);
	}

	public double getPosition() {
		return talon.getSensorCollection().getQuadraturePosition() * distancePerPulse;
	}

	public double getRate() {
		return talon.getSensorCollection().getQuadratureVelocity() * distancePerPulse;
	}

	public double getPulses() {
		return talon.getSensorCollection().getQuadraturePosition();
	}

	public void setPhase(boolean sensorPhase) {
		talon.setSensorPhase(sensorPhase);
	}

	public void setDistancePerPulse(double distancePerPulse) {
		this.distancePerPulse = distancePerPulse;
	}
}