package org.usfirst.frc.team5066.controller2017.controlSchemes;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.LogitechController;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityClimber;
import org.usfirst.frc.team5066.robot.SingularityIntake;

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
		
		sd.hDrive(xbox.getLS_Y(), xbox.getRS_Y(), Math.max(xbox.getLS_X(), xbox.getRS_X()), squaredInputs, speedMode);
		
	}
	
	@Override
	public void controlShooter(LowGoalShooter lGS){
		lGS.setSpeed(logitech.getTrigger());
	}
	
	public void controlClimber(SingularityClimber climber){
		climber.setSpeed(logitech.getStickX());
	}
	
	public void controlIntake(SingularityIntake intake){
		intake.setSpeed(1.0, xbox.getYButton());
	}
	

}
