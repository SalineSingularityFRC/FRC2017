package org.usfirst.team5066.controller2017.controlSchemes;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.LogitechController;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author camtr
 * 
 * leftJoyStick on the xbox remote controls translation
 * rightJoyStick on the xbox remote controls rotation
 * 
 * leftButton on the xbox remote controls down-shift
 * rightButton on the xbox remote controls up-shift
 * 
 */

public class BasicDrive implements ControlScheme {
	
	XboxController xbox;
	SpeedMode speedMode;
	
	public BasicDrive(int xboxPort) {
		xbox = new XboxController(xboxPort);
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
		
		
		sd.hDrive(xbox.getLS_Y(), xbox.getLS_X(), xbox.getRS_X(), squaredInputs, speedMode);
	}

}
