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
 * An autonmode for placing the gear on the middle peg
 *
 */
public class CenterPeg extends AutonMode {
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public CenterPeg(double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
	}
	
	/**
	 * run the auton for the middle peg of either color
	 * 
	 * Use in autonInit
	 * 
	 * @param drive the object for the drive train
	 * @param shooter the object for the shooter
	 * @param intake the object for the intake
	 */
	@Override
	public void run(SingDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		
		Timer timer = new Timer();
		
		double origAngle = gyro.getAngle();
		
		timer.reset();
		timer.start();
		
		while (timer.get() < 4.0) {
			
			drive.drive(0.25, 0.0, gyroRotationConstant * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		drive.drive(0.0, 0.0, 0.0, true, SpeedMode.FAST);
		
	}

}
