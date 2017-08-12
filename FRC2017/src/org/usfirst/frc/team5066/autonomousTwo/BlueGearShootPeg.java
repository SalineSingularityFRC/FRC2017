package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * @author 5066 Saline Singularity
 * 
 * Auton for the blue side. Shoot in the low goal, then place a gear
 *
 */
public class BlueGearShootPeg extends AutonMode {
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public BlueGearShootPeg(AHRS gyro, double gyroRotationConstant) {
		super(gyroRotationConstant, gyro);
	}
	
	
	/**
	 * Run shooting and then placing a gear on the blue side.
	 * 
	 * Use in autonInit
	 * 
	 * @param drive the object for the drive train
	 * @param shooter the object for the shooter
	 * @param intake the object for the intake
	 */
	@Override
	public void run(SingDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
	//	drive.driveStraight(distance, speed, gyroRotationConstant, maxTime);
		
	}

}
