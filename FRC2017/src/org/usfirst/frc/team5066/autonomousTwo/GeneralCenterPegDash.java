package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

public abstract class GeneralCenterPegDash extends AutonMode{
	
	private double angleMultiplier;
	private boolean directionIsToRight;
	
	public GeneralCenterPegDash(boolean directionIsToRight, double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
		angleMultiplier = directionIsToRight ? 1.0 : -1.0;
		this.directionIsToRight = directionIsToRight;
	}
	
	@Override
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {

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
