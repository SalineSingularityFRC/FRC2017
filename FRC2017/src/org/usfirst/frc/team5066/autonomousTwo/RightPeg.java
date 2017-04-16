package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

public class RightPeg extends AutonMode{
	
	AHRS gyro;
	
	public RightPeg(AHRS gyro) {
		this.gyro = gyro;
	}
	
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
