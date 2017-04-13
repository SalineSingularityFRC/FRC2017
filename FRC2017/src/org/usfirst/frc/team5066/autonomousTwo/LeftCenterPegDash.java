package org.usfirst.frc.team5066.autonomousTwo;

import com.kauailabs.navx.frc.AHRS;

public class LeftCenterPegDash extends GeneralCenterPegDash{
	
	public LeftCenterPegDash(AHRS gyro) {
		super(false, gyro);
	}
}
