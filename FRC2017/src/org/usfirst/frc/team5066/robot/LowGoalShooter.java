package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.library.RangeFinder;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class LowGoalShooter{
	
	//shootSpeed is the speed of the shooter
	//reverseSpeed is the speed to reverse before shooting
	//reverseTime is the time to reverse before shooting
	//shooDistVolts is the number of volts for the ultrasonics
	//distLeniency the range within the voltage to be within
	
	static final double shootSpeed = 0.7, reverseSpeed = -0.4, reverseTime = 0.4, shootDistVolts = 4.60, distLeniency = 0.2, speed = 4;
	
	private CANTalon lowShooter;
	private SingularityDrive sd;
	RangeFinder rfLeft/*, rfRight*/;
	
	boolean hasReversed;
	
	public LowGoalShooter(int shootPort, SingularityDrive sd, RangeFinder rfLeft/*, RangeFinder rfRight*/){
		lowShooter = new CANTalon(shootPort);
		this.sd = sd;
		this.rfLeft = rfLeft;
		//this.rfRight = rfRight;
		
		hasReversed = false;
	}
	
	public void setSpeed(boolean shoot){
		
		if(shoot){
			
			//if were are not the right distance from the wall, move accordingly
			
			if (Math.abs(rfLeft.getVolts() - shootDistVolts) > distLeniency /*|| Math.abs(rfRight.getVolts() - shootDistVolts) > distLeniency*/) {
				sd.hDriveStraightConstant(rfLeft.getVolts() - shootDistVolts * 4, 0.0);
			}
			
			//reverse if not already done
			else {
				if (!hasReversed) {
					lowShooter.set(reverseSpeed);
					Timer.delay(reverseTime);
					hasReversed = true;
				}
				
				lowShooter.set(shootSpeed);
				
			}
			
		}
		
		else {
			lowShooter.set(0.0);
			hasReversed = false;
		}
	}
	
	public double getSpeed(){
		return lowShooter.get();
	}
}