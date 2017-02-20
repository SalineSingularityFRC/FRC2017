package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.library.SingularityDrive;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A class that represents our ball intake with conveyor belt for the 2017 year.
 * 
 * @author Saline Singularity 5066
 *
 */
public class SingularityIntake {

	CANTalon frontWheel;
	
	/**
	 * Constructor for the Singularity intake.
	 * 
	 * @param front
	 * 				<b>int</b> The front intake wheel for the balls
	 * @param low
	 * 				<b>int<b> The lower conveyer motor for the intake
	 * @param high
	 * 				<b>int<b> The higher conveyer motor for the intake
	 */
	public SingularityIntake(int front){
		
		frontWheel = new CANTalon(front);
	}
	
	/**
	 * Sets the speed of all three motors in the same direction. Positive is forward, negative is backwards.
	 * 
	 * @param speed
	 * 				<b>double</b> The desired analog speed of the motors. [-1.0, 1.0]
	 */
	public void setSpeed(double speed){
		//Calculates the correct speed
		speed /= Math.max(1, Math.abs(speed));
		
		//Sets speed to the robot
		frontWheel.set(speed);

		
		/* Don't know how this will be implemented yet.
		 * SmartDashboard.putNumber("front encoder speed", frontWheel.getSpeed());
		 * SmartDashboard.putNumber("low conveyer encoder speed", lowConveyer.getSpeed());
		 * SmartDashboard.putNumber("high conveyer encoder speed", highConveyer.getSpeed());
		 */
	}
	
	/**
	 * 
	 * @return The current speed of the motors(front wheel motor)
	 */
	public double getSpeed() {
		return frontWheel.get();
	}
	
	
	
	
	
	
	
}
