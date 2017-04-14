package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

public class RedBoilerPegThenShoot extends GeneralBoilerPegThenShoot{
	
	public RedBoilerPegThenShoot(AHRS gyro){
		super(false, gyro);
	}

//	@Override
//	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
//		
//		super.run(drive, shooter, intake);
//		
//	}
	
	
	
}
