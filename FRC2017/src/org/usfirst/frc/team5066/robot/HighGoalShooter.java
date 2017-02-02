package org.usfirst.frc.team5066.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HighGoalShooter {
	
	private CANTalon cantalon;
	
	public HighGoalShooter(int port) {
		cantalon = new CANTalon(port);
	}
	
	public void setSpeed(double speed) {
		//checks for illegal input
		speed /= Math.max(1, Math.abs(speed));
		
	}
	
	public double getSpeed() {
		return cantalon.get();
	}
	
}
