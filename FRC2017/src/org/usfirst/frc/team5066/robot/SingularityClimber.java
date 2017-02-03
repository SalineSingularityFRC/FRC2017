package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SingularityClimber {
	private Solenoid gearChanger;
	private CANTalon climber;
	double climberSpeedConstant;

	public SingularityClimber(int solenoidPort, int talonPort){
		gearChanger = new Solenoid(solenoidPort);
		climber = new CANTalon(talonPort);
		
		//talon.setPosition(0.0);
	}
	
	public void setSpeed(double speed){
		//Checks for illegal value (and deports them)
		speed /= Math.max(1, Math.abs(speed));
		climber.set(speed);
		
		
		//SmartDashboard.putNumber("Get climber motor position", talon.getPosition());
	}
	
	public void setGear(boolean gearSwitch ){
		gearChanger.set(true);
	}
	
	public double getSpeed(){
		return climber.get();
	}
	
	/*public double getPosition(){
		return talon.getPosition();
	}
	*/
}
