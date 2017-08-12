package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * @author 5066 Saline Singularity
 * 
 * The main abstract class for the auton modes of 2017.
 * These were created at states 2017, and are the most recent 
 * auton scheme (as of 5/13/17)
 *
 */
public abstract class AutonMode {
	
	/**
	 * a constant for driving straight while using the gyro
	 */
	double gyroRotationConstant;
	
	/**
	 * The main gyro for auton.
	 * AHRS is for the large purple gyro.
	 */
	AHRS gyro;
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public AutonMode(double gyroRotationConstant, AHRS gyro) {
		this.gyroRotationConstant = gyroRotationConstant;
		this.gyro = gyro;
	}
	
	/**
	 * Run this method in autonomous init.
	 * @param drive the drive train for the robot
	 * @param shooter a shooter object for shooting objects
	 * @param intake an intake object for shooting objects
	 */
	public abstract void run(SingDrive drive, LowGoalShooter shooter, SingularityIntake intake);
	
}
