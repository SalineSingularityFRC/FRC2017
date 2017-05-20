package org.usfirst.frc.team5066.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author 5066 Singularity
 * 
 * A class to control a high goal shooter
 *
 */
public class HighGoalShooter {
	
	private CANTalon cantalon;
	
	/**
	 * Constructor for HighGoalShooter
	 * @param port the CANTalon port for the high goal motor
	 */
	public HighGoalShooter(int port) {
		cantalon = new CANTalon(port);
	}
	
	/**
	 * Sets the CANTalon to the speed
	 * @param speed the percent voltage to the motor
	 * from -1.0 to 1.0
	 */
	public void setSpeed(double speed) {
		//checks for illegal input
		speed /= Math.max(1, Math.abs(speed));
		
	}
	
	/**
	 * 
	 * @return the current percent voltage we are
	 * setting the motor to.
	 */
	public double getSpeed() {
		return cantalon.get();
	}
	
}
