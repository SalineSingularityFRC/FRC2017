package org.usfirst.team5066.controller2017.controlSchemes;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.LogitechController;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankHDrive implements ControlScheme {
	
	XboxController xbox;
	LogitechController logitech;
	SpeedMode speedMode;
	
	
	public TankHDrive(int xboxPort, int logitechPort) {
		xbox = new XboxController(xboxPort);
		logitech = new LogitechController(logitechPort);
		
	}
	
	@Override
	public void drive(SingularityDrive sd, boolean squaredInputs) {
		
		//set speedMode
		if(xbox.getLB()) {
			speedMode = SpeedMode.SLOW;
		} else if(xbox.getRB()) {
			speedMode = SpeedMode.FAST;
		} else {
			speedMode = SpeedMode.NORMAL;
		}
		
		//sd.hDriveTank(xbox.getLS_Y(), xbox.getRS_Y(), Math.max(xbox.getLS_X(), xbox.getRS_X()), squaredInputs, speedMode);
		
	}

}
