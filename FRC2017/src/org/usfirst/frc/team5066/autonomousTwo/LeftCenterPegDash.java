package org.usfirst.frc.team5066.autonomousTwo;

import com.kauailabs.navx.frc.AHRS;

public class LeftCenterPegDash extends GeneralCenterPegDash{
	
	public LeftCenterPegDash(double gyroRotationConstant, AHRS gyro) {
		super(false, gyroRotationConstant, gyro);
	}
}
