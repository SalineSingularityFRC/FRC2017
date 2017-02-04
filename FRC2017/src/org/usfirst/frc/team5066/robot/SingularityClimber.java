package org.usfirst.frc.team5066.robot;


import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SingularityClimber {
	private CANTalon talon;
	double climberSpeedConstant;

	public SingularityClimber(int port){
		talon = new CANTalon(port);
		
		//talon.setPosition(0.0);
	}
	
	public void setSpeed(double speed){
		//Checks for illegal value (and deports them. But now we have a wall,
		//so this won't be a problem anymore;))
		speed /= Math.max(1, Math.abs(speed));
		talon.set(speed);
		
		//SmartDashboard.putNumber("Get climber motor position", talon.getPosition());
	}
	
	public double getSpeed(){
		return talon.get();
	}
	
	/*public double getPosition(){
		return talon.getPosition();
	}
	*/



}
