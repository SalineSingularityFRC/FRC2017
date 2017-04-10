 package org.usfirst.frc.team5066.controller2017.controlSchemes;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.LogitechController;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityClimber;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.robot.SingularityLEDs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class OneController implements ControlScheme {
	
	XboxController xbox;
	LogitechController logitech;
	SpeedMode speedMode;
	boolean on, prevY;
	
	public OneController(int xboxPort, int logitechPort) {
		xbox = new XboxController(xboxPort);
		logitech = new LogitechController(logitechPort);
		on = false;
	}
	
	@Override
	public void drive(SingularityDrive sd, boolean squaredInputs) {
		speedMode = SpeedMode.FAST;
		sd.arcadeSixWheel(-xbox.getLS_Y(), xbox.getRS_X(), squaredInputs, speedMode);

	}

	@Override
	public void controlShooter(LowGoalShooter lGS) {
		lGS.setSpeed(xbox.getTriggerRight() > 0.6, false);
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
		else intake.setSpeed(0.6);
		
		if (xbox.getYButton()) prevY = true;
		else prevY = false;
		
		//SmartDashboard.putString("DB/String 5", "Intake Speed: " + intake.getSpeed());
	}
	
	public void controlLEDs(SingularityLEDs robotLEDs){
		if(logitech.getStickX() > 0.2 && (!xbox.getYButton() || !xbox.getTrigger())){
			robotLEDs.turnBlue();
		}
		else if(logitech.getStickX() < -0.2 && (!xbox.getYButton() || !xbox.getTrigger())){
			robotLEDs.turnYellow();
		}
		else if(logitech.getTrigger()){
			robotLEDs.oscillate();
		}
		else if (xbox.getRB() && !(logitech.getTrigger())){
			robotLEDs.oscillate();
		}
		else if (xbox.getTrigger()){
			robotLEDs.flashBlue();
		}
		else if(xbox.getYButton()){
			robotLEDs.flashYellow();
		}
	}

}