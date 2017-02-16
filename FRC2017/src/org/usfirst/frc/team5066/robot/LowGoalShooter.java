package org.usfirst.frc.team5066.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class LowGoalShooter{
	private CANTalon lowShooter;
	
	public LowGoalShooter(int shootPort){
		lowShooter = new CANTalon(shootPort);
	}
	
	public void setSpeed(boolean shoot){
		//Checks for illegal value
		//speed /= Math.max(1, Math.abs(speed));
		
		if(shoot){
		lowShooter.set(0.7);
		}
		
		/*
		else if(shoot > 0.5){
		lowShooter.set(1.0);
		feedMotor.set(-0.25);
		}
		*/
		else {
			lowShooter.set(0.0);
		}
		//For Testing
		SmartDashboard.putNumber("Low shooter speed", lowShooter.getSpeed());
	}
	
	public double getSpeed(){
		return lowShooter.get();
	}
}
