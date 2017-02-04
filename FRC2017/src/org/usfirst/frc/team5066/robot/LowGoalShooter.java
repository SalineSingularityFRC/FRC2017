package org.usfirst.frc.team5066.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class LowGoalShooter{
	private CANTalon lowShooter;
	private CANTalon feedMotor;
	
	public LowGoalShooter(int shootPort, int feedPort){
		lowShooter = new CANTalon(shootPort);
		feedMotor = new CANTalon(feedPort);
	}
	
	public void setSpeed(boolean shoot){
		//Checks for illegal value
		//speed /= Math.max(1, Math.abs(speed));
		
		if(shoot){
		lowShooter.set(1.0);
		feedMotor.set(0.5);
		}
		
		/*
		else if(shoot > 0.5){
		lowShooter.set(1.0);
		feedMotor.set(-0.25);
		}
		*/
		else {
			lowShooter.set(0.0);
			feedMotor.set(-0.2);
		}
		//For Testing
		SmartDashboard.putNumber("Low shooter speed", lowShooter.getSpeed());
		SmartDashboard.putNumber("Feed moter speed", feedMotor.get());
	}
	
	public double getSpeed(){
		return lowShooter.get();
	}
	
	//Keep getting error when using this method
	public double getFeedSpeed(){
		return feedMotor.get();
	}
}
