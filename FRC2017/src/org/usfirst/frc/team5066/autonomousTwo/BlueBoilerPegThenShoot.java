package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * @author 5066 Saline singularity
 * 
 * A class for placing a gear on the peg on the boiler side of the airship
 * of the blue side, then shooting into the low goal
 * 
 * 
 * This class does not appear to be useful in any way
 * See GeneralBoilerPegThenShoot
 */
public class BlueBoilerPegThenShoot extends GeneralBoilerPegThenShoot{
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public BlueBoilerPegThenShoot(double gyroRotationConstant, AHRS gyro){
		super(true, gyroRotationConstant, gyro);
	}
	
	/**
	 * to be run at auton init
	 * 
	 * this method will place a gear on the boiler side of the blue airship,
	 * then shoot in the low goal
	 * 
	 * @param drive the object for the drive train
	 * @param shooter the object for the shooter
	 * @param intake the object for the intake
	 */
	@Override
	public void run(SingDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		
		super.run(drive, shooter, intake);
		
	}
	
}
