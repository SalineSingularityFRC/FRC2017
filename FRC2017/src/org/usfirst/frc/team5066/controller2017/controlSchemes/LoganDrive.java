package org.usfirst.frc.team5066.controller2017.controlSchemes;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityClimber;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoganDrive implements ControlScheme {
	XboxController xbox;
	SpeedMode speedMode;
	boolean on, prevA;
	
	public LoganDrive(int xboxPort) {
		xbox = new XboxController(xboxPort);
		prevA = false;
	}
	
	@Override
	public void drive(SingularityDrive sd, boolean squaredInputs) {
		//set speedMode
		if(xbox.getXButton()) {
			speedMode = SpeedMode.SLOW;
		} else if(xbox.getBButton()) {
			speedMode = SpeedMode.NORMAL;
		} else {
			speedMode = SpeedMode.FAST;
		}
		
		sd.hDrive(xbox.getLS_Y(), xbox.getLS_X(), xbox.getRS_X(), squaredInputs, speedMode);

	}

	@Override
	public void controlShooter(LowGoalShooter lGS) {
		lGS.setSpeed(xbox.getTriggerRight() > 0.3);
	}

	@Override
	public void controlClimber(SingularityClimber climber) {
		if (xbox.getRB()) climber.setSpeed(1.0);
		else if (xbox.getLB()) climber.setSpeed(-0.5);
		else climber.setSpeed(0.0);
	}

	@Override
	public void controlIntake(SingularityIntake intake) {
		
		if (xbox.getAButton() && !prevA) on = on ? false : true;
		if (!on) intake.setSpeed(0.0);
		else if (xbox.getYButton()) intake.setSpeed(-1.0);
		else intake.setSpeed(1.0);
		SmartDashboard.putString("DB/String 6", "The intake is not intaking.");

	}
}