package org.usfirst.frc.team5066.controller2017;

import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.library.SpeedMode;

public abstract class AutonControlScheme {
	
	public abstract void moveAuton();
	
	/**
	 * 
	 * @param verticalSpeed
	 * Sets speed of the motors. Negative is backwards
	 * @param d
	 * Sets distance of movement in inches.
	 */
	public static void vertical(double verticalSpeed, double d){
		do {
			EncoderAuto.drive.hDrive(verticalSpeed, 0.0, 0.0, false, SpeedMode.NORMAL);
		} while(EncoderAuto.getAverageLeftPosition() != d  / EncoderAuto.kDistancePerRevolution && EncoderAuto.getAverageRightPosition() < d);
		
		EncoderAuto.drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		EncoderAuto.resetAll();
	}
	
	/**
	 * 
	 * @param horizontalSpeed
	 * Sets speed of strafe motors. Negative is left.
	 * @param distance
	 * Sets distance of movements in inches
	 */
	public static void horizontal(double horizontalSpeed, double distance){
		do {
			EncoderAuto.drive.hDrive(0.0, horizontalSpeed, 0.0, false, SpeedMode.NORMAL);
		} while(EncoderAuto.getAverageMiddlePosition() != distance  / EncoderAuto.kDistancePerRevolution);
		
		EncoderAuto.drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		EncoderAuto.resetAll();
	}
	
	/**
	 * 
	 * @param rotationSpeed
	 * Sets speed of rotating motors
	 * @param degrees
	 * Set degrees of the rotation (negative is counterclockwise)
	 */
	public static void rotation(double rotationSpeed, double degrees){
		do {
			EncoderAuto.drive.hDrive(0.0, 0.0, rotationSpeed, false, SpeedMode.NORMAL);
		} while(EncoderAuto.getAverageMiddlePosition() != degrees / 20);
		
		EncoderAuto.drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		EncoderAuto.resetAll();
	}
}
