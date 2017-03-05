package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.library.RangeFinder;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class LowGoalShooter{
	
	static final double shootSpeed = 0.7, reverseSpeed = -0.4, reverseTime = 0.4, shootDistVolts = 4.60, distLeniency = 0.2;
	
	private CANTalon lowShooter;
	private SingularityDrive sd;
	RangeFinder rfLeft, rfRight;
	
	boolean hasReversed;
	
	public LowGoalShooter(int shootPort, SingularityDrive sd, RangeFinder rfLeft, RangeFinder rfRight){
		lowShooter = new CANTalon(shootPort);
		this.sd = sd;
		this.rfLeft = rfLeft;
		this.rfRight = rfRight;
		
		hasReversed = false;
	}
	
	public void setSpeed(boolean shoot){
		
		if(shoot){
			if (Math.abs(rfLeft.getVolts() - shootDistVolts) > distLeniency || Math.abs(rfRight.getVolts() - shootDistVolts) > distLeniency) {
				sd.hDriveTank(rfLeft.getVolts() - shootDistVolts, rfRight.getVolts() - shootDistVolts, 0.0, true, SpeedMode.NORMAL);
			}
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