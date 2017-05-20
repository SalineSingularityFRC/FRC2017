package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author 5066 Singularity
 * 
 * An autonmode for pacing a gear on the right peg
 *
 */
public class RightPeg extends AutonMode{
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public RightPeg(double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
	}
	
	/**
	 * Place a gear on the right peg
	 * 
	 * Use in autonInit
	 * 
	 * @param drive the object for the drive train
	 * @param shooter the object for the shooter
	 * @param intake the object for the intake
	 */
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {

		drive.driveStraight(48.96, 0.35, 0.05, 10);
		drive.rotateTo(-1.0 * 55.0, 8);
		//drive.driveStraight(83.081, 0.4, 0.05, 10);


		Timer timer = new Timer();
		
		double origAngle = gyro.getAngle();
		
		timer.reset();
		timer.start();
		
		while (timer.get() < 2.8) {
			
			drive.arcadeSixWheel(0.25, 0.05 * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		drive.arcadeSixWheel(0.0, 0.0, true, SpeedMode.FAST);
		
	
		
	}

}
