package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author 5066 Singularity
 * 
 * An autonmode for placing a gear on the red center peg
 * and then shooting into the low goal.
 *
 */
public class RedCenterPegShoot extends AutonMode {
	
	/**
	 * A constructor for the abstract class
	 * @param gyroRotationConstant a constant for driving straight with the gyro
	 * @param gyro the large purple gyro
	 */
	public RedCenterPegShoot(double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
	}
	
	/**
	 * place a gear and shoot in the red goal
	 * 
	 * 
	 * 
	 * Use in autonInit
	 * 
	 * @param drive the object for the drive train
	 * @param shooter the object for the shooter
	 * @param intake the object for the intake
	 */
	@Override
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		AutonMode autonMode = new CenterPeg(gyroRotationConstant, gyro);
		autonMode.run(drive, shooter, intake);
		
		Timer.delay(4);
		
		drive.driveStraight(-80, 0.6, 0.06, 4);
		drive.rotateTo(90, 5);
		drive.driveStraight(210, 0.6, 0.06, 6);
		//drive.rotateTo(degrees, maxTime);
	}

}
