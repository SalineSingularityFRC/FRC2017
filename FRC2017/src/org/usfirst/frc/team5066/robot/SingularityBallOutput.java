package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.library.SingularityDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A class that represents our conveyer belt from the 2016 year.
 * 
 * @author Saline Singularity 5066
 *
 */

public class SingularityBallOutput 
{
	double spd;
	CANTalon Ball;
	
	
	
	public SingularityBallOutput( int b) 
	{

		Ball = new CANTalon(b);
		
	}
	
	
	public void setSpeed(double speed) 
	{
		// Calculates the correct speed
		speed /= Math.max(1, Math.abs(speed)) * (SingularityDrive.isreverse ? 1 : -1);
		
		// Sets the speed to the robot
		Ball.set(speed);
		
		
		SmartDashboard.putNumber("left encoder speed", Ball.getSpeed());
		
	}
	
	
	public double getSpeed() 
	{
		return Ball.getspeed();
	}
	
	
}
