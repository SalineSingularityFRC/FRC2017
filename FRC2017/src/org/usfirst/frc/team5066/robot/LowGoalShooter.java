package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.library.RangeFinder;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class LowGoalShooter{
	
	/*
	 * shootSpeed is the speed of the ejector
	 * reverseSpeed is the speed while reversing the ejector
	 * maxMoveSpeed is the max speed to move while adjusting robot position
	 * reverseTime is the time to reverse the ejector
	 * shootDistVolts is the optimal voltage for the ultrasonic before shooting
	 */
	
	private static final double shootSpeed = 0.7, reverseSpeed = -0.4,
			maxMoveSpeed = 0.4, reverseTime = 0.4, shootDistVolts = 4.60;
	
	private CANTalon lowShooter;
	private SingularityDrive sd;
	RangeFinder rf;
	Timer timer;
	
	boolean hasReversed;
	
	public LowGoalShooter(int shootPort, SingularityDrive sd, RangeFinder rf){
		lowShooter = new CANTalon(shootPort);
		this.sd = sd;
		this.rf = rf;
		
		hasReversed = false;
		timer = new Timer();
	}
	
	public void setSpeed(boolean shoot){
		
		if(shoot){
			/*
			moveFromUltra();
			
			//If we haven't already reversed while shooting this attempt,
			//reverse the shooter for a brief time while still adjusting position
			if (!hasReversed) {
				timer.reset();
				hasReversed = true;
				while (timer.get() < reverseTime) {
					lowShooter.set(-reverseSpeed);
					moveFromUltra();
				}
			}
			*/
			lowShooter.set(shootSpeed);
			
		}
		
		//If not shooting, dont't. Set hasReversed false for next time we shoot
		else {
			lowShooter.set(0.0);
			hasReversed = false;
		}
	}
	
	public double getSpeed(){
		return lowShooter.get();
	}
	
	private void moveFromUltra() {
		//Move the robot to be the optimal distance from the wall
		//We want to do this constantly while shooting
		if (rf.getVolts() - shootDistVolts > 0)
			sd.hDriveStraightConstant(Math.min(maxMoveSpeed, rf.getVolts() - shootDistVolts), 0.0, 0.0);
		else
			sd.hDriveStraightConstant(Math.max(-maxMoveSpeed, rf.getVolts() - shootDistVolts), 0.0, 0.0);
	}
}