package org.usfirst.frc.team5066.controller2017.controlSchemes;
import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.LogitechController;
import org.usfirst.frc.team5066.controller2017.SingularityBallOutput;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityClimber;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.robot.SingularityLEDs;

public class ArcadeHDrive implements ControlScheme {

	XboxController xbox;
	LogitechController logitech;
	SpeedMode speedMode;
	boolean on, prevY;
	
public ArcadeHDrive(int xboxPort, int logitechPort) {
	xbox = new XboxController(xboxPort);
	logitech = new LogitechController(logitechPort);
	
}


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

public void controlShooter(LowGoalShooter lGS){
	lGS.setSpeed(logitech.getTrigger(), false);
}

public void controlClimber(SingularityClimber climber){
	climber.setSpeed(logitech.getStickX());
}

public void controlIntake(SingularityIntake intake){
	if (xbox.getYButton() && !prevY) on = on ? false : true;
	if (!on) intake.setSpeed(0.0);
	else if (xbox.getXButton()) intake.setSpeed(-1.0);
	else intake.setSpeed(1.0);
}


@Override
public void controlLEDs(SingularityLEDs robotLEDs) {
	// TODO Auto-generated method stub
	
}

}
