package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

public class LeftPeg extends AutonMode {
	
	public LeftPeg(double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
	}
	
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
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
			
			drive.arcadeSixWheel(0.35, 0.05 * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		drive.arcadeSixWheel(0.0, 0.0, true, SpeedMode.FAST);
		Timer.delay(0.4);
		
		//turn left slightly
		while((origAngle - gyro.getAngle()) > -45) {
			drive.arcadeSixWheel(0.0, -0.3, false, SpeedMode.FAST);
		}
		
		drive.arcadeSixWheel(0.0, 0.0, true, SpeedMode.FAST);
		Timer.delay(0.4);
		
		//go forward
		origAngle = gyro.getAngle();
		
		t.reset();
		t.start();
		
		while (t.get() < 3.2) {
			
			drive.arcadeSixWheel(0.25, 0.05 * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		drive.arcadeSixWheel(0.0, 0.0, false, SpeedMode.FAST);
		
		
	}
	
}
