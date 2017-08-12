package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author 5066 Saline Singularity
 * 
 * An autonmode for pacing a gear on the center 
 * peg and moving towards the loading station.s
 *
 */
public abstract class GeneralCenterPegDash extends AutonMode{
	
	private double angleMultiplier;
	private boolean directionIsToRight;
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 * @param directionIsToRight true if we want to dash right, false if leaft
	 */
	public GeneralCenterPegDash(boolean directionIsToRight, double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
		angleMultiplier = directionIsToRight ? 1.0 : -1.0;
		this.directionIsToRight = directionIsToRight;
	}
	
	/**
	 * run the auton for pacing a center gear and dashing
	 * 
	 * Use in autonInit
	 * 
	 * @param drive the object for the drive train
	 * @param shooter the object for the shooter
	 * @param intake the object for the intake
	 */
	@Override
	public void run(SingDrive drive, LowGoalShooter shooter, SingularityIntake intake) {

		AutonMode centerMode = new CenterPeg(gyroRotationConstant, gyro);
		centerMode.run(drive, shooter, intake);
		
		Timer t = new Timer();
		t.delay(5);
		
		//Position self to dash
		drive.driveStraight(32, -0.6, 0.05, 8);
		drive.rotateTo(angleMultiplier * 90.0, 6);
		drive.driveStraight(60, 0.6, 0.05, 8);
		drive.rotateTo(angleMultiplier * -90.0, 6);
		
		//dash
		drive.driveStraight(180, 0.8, 0.05, 10);
		
	}
	
	
	
}
