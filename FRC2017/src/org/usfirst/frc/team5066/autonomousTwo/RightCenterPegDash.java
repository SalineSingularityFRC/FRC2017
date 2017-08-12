package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * @author 5066 Singularity
 * 
 * A seemingly unused class
 * See GeneralCenterPegDash
 *
 */
public class RightCenterPegDash extends GeneralCenterPegDash{
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public RightCenterPegDash(double gyroRotationConstant, AHRS gyro) {
		super(true, gyroRotationConstant, gyro);
	}

	@Override
	public void run(SingDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		// TODO Auto-generated method stub
		
	}

}
