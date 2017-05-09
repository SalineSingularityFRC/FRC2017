package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

public abstract class AutonMode {
	
	double gyroRotationConstant;
	AHRS gyro;
	
	public AutonMode(double gyroRotationConstant, AHRS gyro) {
		this.gyroRotationConstant = gyroRotationConstant;
		this.gyro = gyro;
	}

	public abstract void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake);
	
}
