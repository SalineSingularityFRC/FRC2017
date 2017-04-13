package org.usfirst.frc.team5066.autonomousTwo;

import com.kauailabs.navx.frc.AHRS;

public class RightCenterPegDash extends GeneralCenterPegDash{

	public RightCenterPegDash(AHRS gyro) {
		super(true, gyro);
	}

}
