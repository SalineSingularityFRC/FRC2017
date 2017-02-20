package org.usfirst.frc.team5066.controller2017;

import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;

public abstract class AutonControlScheme {
	
	public static final double DistancePerRevolution = 12.56;//circumference of wheels
	
	private static SingularityDrive drive;
	private static LowGoalShooter shooter;
	public AutonControlScheme(SingularityDrive drive, LowGoalShooter shooter){
		this.drive = drive;
		this.shooter = shooter;
	}
	
	public abstract void moveAuton();
	
	/**
	 * 
	 * @param verticalSpeed
	 * Sets speed of the motors.
	 * @param distance
	 * Sets distance of movement in inches.
	 * @param reverse
	 * true means travel backwards, false forwards
	 */
	public static void vertical(double verticalSpeed, double distance, boolean reverse) {
		
		if (reverse) {
			//Slowly start motors for i(10) inches
			for(int i = 10; i > 0; i--){
				do{
					drive.hDrive(-verticalSpeed/i, 0.0, 0.0, false, SpeedMode.NORMAL);
				} while((drive.getLeftPosition() + drive.getRightPosition()) / 2 > -1.0 / DistancePerRevolution);
			}	
			//normal speed
			do {
				drive.hDrive(-verticalSpeed, 0.0, 0.0, false, SpeedMode.NORMAL);
			} while ((drive.getLeftPosition() + drive.getRightPosition()) / 2 > -(distance - 10) / DistancePerRevolution);
		} 
		else {
			//Slowly start motors for i(10) inches
			for(int i = 10; i > 0; i--){
				do{
					drive.hDrive(-verticalSpeed/i, 0.0, 0.0, false, SpeedMode.NORMAL);
				} while((drive.getLeftPosition() + drive.getRightPosition()) / 2 > -1.0 / DistancePerRevolution);
			}
			//normal speed
			do {
				drive.hDrive(verticalSpeed, 0.0, 0.0, false, SpeedMode.NORMAL);
			} while ((drive.getLeftPosition() + drive.getRightPosition()) / 2 < (distance - 10) / DistancePerRevolution);
		}
		
		drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		drive.resetAll();
	}
	
	/**
	 * 
	 * @param horizontalSpeed
	 * Sets speed of strafe motors.
	 * @param distance
	 * Sets distance of movements in inches.
	 * @param left
	 * true means travel left, false right
	 */
	public static void horizontal(double horizontalSpeed, double distance, boolean left) {
		if (left) {
			//Slowly start motors for i(10) inches
			for(int i = 10; i > 0; i--){
				do{
					drive.hDrive(-horizontalSpeed/i, 0.0, 0.0, false, SpeedMode.NORMAL);
				} while((drive.getLeftPosition() + drive.getRightPosition()) / 2 > -1.0 / DistancePerRevolution);
			}	
			//normal speed
			do {
				drive.hDrive(0.0, -horizontalSpeed, 0.0, false, SpeedMode.NORMAL);
			} while(drive.getMiddlePosition() > -distance  / DistancePerRevolution);
		}
		else {
			//Slowly start motors for i(10) inches
			for(int i = 10; i > 0; i--){
				do{
					drive.hDrive(-horizontalSpeed/i, 0.0, 0.0, false, SpeedMode.NORMAL);
				} while((drive.getLeftPosition() + drive.getRightPosition()) / 2 > -1.0 / DistancePerRevolution);
			}	
			//normal speed
			do {
				drive.hDrive(0.0, horizontalSpeed, 0.0, false, SpeedMode.NORMAL);
			} while(drive.getMiddlePosition() < distance  / DistancePerRevolution);
		}
		
		drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		drive.resetAll();
	}
	
	/**
	 * 
	 * @param rotationSpeed
	 * Sets speed of rotating motors
	 * @param degrees
	 * Set degrees of the rotation (negative is counterclockwise)
	 * @param counterClockwise
	 * true means we are rotating left, false right
	 */
	//TODO Fix the Rotation
	public static void rotation(double rotationSpeed, double degrees, boolean counterClockwise) {
		if (counterClockwise) {
			do {
				drive.hDrive(0.0, 0.0, -rotationSpeed, false, SpeedMode.NORMAL);
			} while(drive.getLeftPosition() > -degrees / 20);
		}
		else{
			do {
				drive.hDrive(0.0, 0.0, rotationSpeed, false, SpeedMode.NORMAL);
			} while(drive.getMiddlePosition() < degrees / 20);
		}
		drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		drive.resetAll();
	}
}
