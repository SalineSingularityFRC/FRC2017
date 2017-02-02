package org.usfirst.frc.team5066.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class LowGoalShooter{
	private CANTalon lowShooter;
	
	public LowGoalShooter(int port, double climberSpeed){
		lowShooter = new CANTalon(port);
	}
	
	public void setSpeed(double speed){
		//Checks for illegal value
		speed /= Math.max(1, Math.abs(speed));
		
		lowShooter.set(speed);
		
		//SmartDashboard.putNumber("Low shooter speed", lowShooter.getSpeed());
	}
	
	public double getSpeed(){
		return lowShooter.get();
	}
}
