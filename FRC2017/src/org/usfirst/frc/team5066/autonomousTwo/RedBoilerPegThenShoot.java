package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * @author 5066 Singularity
 * 
 * An autonMode for placing a gear on the red peg
 * closest to the boiler and shooting
 * 
 * This class does not appear to be useful in any way
 * See GeneralBoilerPegThenShoot
 *
 */
public class RedBoilerPegThenShoot extends GeneralBoilerPegThenShoot{
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public RedBoilerPegThenShoot(double gyroRotationConstant, AHRS gyro){
		super(true, gyroRotationConstant, gyro);
	}
	

//	@Override
//	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
//		
//		super.run(drive, shooter, intake);
//		
//	}
	
	
	
}
