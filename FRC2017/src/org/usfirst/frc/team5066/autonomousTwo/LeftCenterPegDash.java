package org.usfirst.frc.team5066.autonomousTwo;

import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * @author 5066 Singularity
 * 
 * Until shown otherwise, this class is not ever going to be used
 * See GeneralCenterPegDash, as it handles everything
 *
 */
public class LeftCenterPegDash extends GeneralCenterPegDash{
	
	/**
	 * Constructor for the autonomous scheme abstract class
	 * @param gyroRotationConstant for the constant for driving straight
	 * @param gyro for the AHRS
	 */
	public LeftCenterPegDash(double gyroRotationConstant, AHRS gyro) {
		super(false, gyroRotationConstant, gyro);
	}
}
