 package org.usfirst.frc.team5066.controller2017.controlSchemes;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityClimber;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class OneController implements ControlScheme {
	
	XboxController xbox;
	SpeedMode speedMode;
	boolean on, prevY;
	
	public OneController(int xboxPort) {
		xbox = new XboxController(xboxPort);
		on = false;
	}
	
	@Override
	public void drive(SingularityDrive sd, boolean squaredInputs) {
		speedMode = SpeedMode.FAST;
		sd.hDrive(-xbox.getLS_Y(), -xbox.getLS_X(), xbox.getRS_X(), squaredInputs, speedMode);

	}

	@Override
	public void controlShooter(LowGoalShooter lGS) {
		lGS.setSpeed(xbox.getTriggerRight() > 0.6);
	}

	@Override
	public void controlClimber(SingularityClimber climber) {
		if (xbox.getRB()) climber.setSpeed(1.0);
		else climber.setSpeed(0.0);
	}

	@Override
	public void controlIntake(SingularityIntake intake) {
		
		if (xbox.getYButton() && !prevY) {
			if (!on) on = true;
			else on = false;
		}
		if (xbox.getXButton()) intake.setSpeed(-1.0);
		else if (!on) intake.setSpeed(0.0);
		else intake.setSpeed(1.0);
		
		if (xbox.getYButton()) prevY = true;
		else prevY = false;
		
		//SmartDashboard.putString("DB/String 5", "Intake Speed: " + intake.getSpeed());
	}

}