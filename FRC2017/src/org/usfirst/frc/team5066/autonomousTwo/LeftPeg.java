package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author 5066 Singularity
 * 
 * An autonmode for pacing a gear on the left peg
 *
 */
public class LeftPeg extends AutonMode {
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public LeftPeg(double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
	}
	
	/**
	 * place a gear on the left peg
	 * 
	 * Use in autonInit
	 * 
	 * @param drive the object for the drive train
	 * @param shooter the object for the shooter
	 * @param intake the object for the intake
	 */
	public void run(SingDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		/*
		drive.driveStraight(48.96, 0.35, 0.05, 10);
		drive.rotateTo(60.0, 6.0);
		drive.driveStraight(83.081, 0.4, 0.05, 10);
		*/
		
		
		//THIS IS NOT ACTUALLY LEFT PEG> MY B
		double origAngle = gyro.getAngle();
		
		Timer t = new Timer();

		t.reset();
		t.start();
		
		while (t.get() < 2.4) {
			
			drive.drive(0.35, 0.0, 0.05 * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		drive.drive(0.0, 0.0, 0.0, true, SpeedMode.FAST);
		Timer.delay(0.4);
		
		//turn left slightly
		while((origAngle - gyro.getAngle()) > -45) {
			drive.drive(0.0, 0.0, -0.3, false, SpeedMode.FAST);
		}
		
		drive.drive(0.0, 0.0, 0.0, true, SpeedMode.FAST);
		Timer.delay(0.4);
		
		//go forward
		origAngle = gyro.getAngle();
		
		t.reset();
		t.start();
		
		while (t.get() < 3.2) {
			
			drive.drive(0.25, 0.0, 0.05 * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		drive.drive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
		
		
	}
	
}
