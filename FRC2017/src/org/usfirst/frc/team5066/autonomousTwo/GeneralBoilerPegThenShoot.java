package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author 5066 Saline Singularity
 * 
 * an autonmode for pacing a gear and then shooting
 *
 */
public abstract class GeneralBoilerPegThenShoot extends AutonMode {
	
	private double angleMultiplier;
	private boolean onBlueAlliance;
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 * @param onBlueAlliance true if our team is on the blue alliance, false if red
	 */
	public GeneralBoilerPegThenShoot(boolean onBlueAlliance, double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
		
		angleMultiplier = onBlueAlliance ? 1.0 : -1.0;
		this.onBlueAlliance = onBlueAlliance;
	}
	
	/**
	 * run the auton for placing a gear before shooting, on either side of the field.
	 * 
	 * Use in autonInit
	 * 
	 * @param drive the object for the drive train
	 * @param shooter the object for the shooter
	 * @param intake the object for the intake
	 */
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		
		
		AutonMode sidePegMode = onBlueAlliance ? new LeftPeg(gyroRotationConstant, gyro) : new RightPeg(gyroRotationConstant, gyro);
		sidePegMode.run(drive, shooter, intake);

		Timer t = new Timer();
		t.delay(5);

		drive.driveStraight(83.081, -0.4, 0.05, 10);
		drive.rotateTo(angleMultiplier * -15.0, 8);
		drive.driveStraight(30, -0.25, 0.05, 10);

		shooter.setSpeed(true, true);

	}

}
