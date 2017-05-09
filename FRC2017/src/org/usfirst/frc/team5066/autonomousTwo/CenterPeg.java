package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

public class CenterPeg extends AutonMode {
	
	public CenterPeg(double gyroRotationConstant, AHRS gyro) {
		super(gyroRotationConstant, gyro);
	}
	
	@Override
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		
		Timer timer = new Timer();
		
		double origAngle = gyro.getAngle();
		
		timer.reset();
		timer.start();
		
		while (timer.get() < 4.0) {
			
			drive.arcadeSixWheel(0.25, gyroRotationConstant * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		drive.arcadeSixWheel(0.0, 0.0, true, SpeedMode.FAST);
		
	}

}
