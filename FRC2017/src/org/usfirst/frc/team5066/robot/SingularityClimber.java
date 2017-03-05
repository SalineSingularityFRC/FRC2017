package org.usfirst.frc.team5066.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SingularityClimber {
	private CANTalon planetaryTalon, wormTalon;
	double climberSpeedConstant;

	public SingularityClimber(int planetaryPort, int wormPort){
		planetaryTalon = new CANTalon(planetaryPort);
		wormTalon = new CANTalon(wormPort);
		
		//talon.setPosition(0.0);
	}
	
	public void setSpeed(double speed){
		//Checks for illegal value (and deports them. But now we have a wall,
		//so this won't be a problem anymore;))
		speed /= Math.max(1, Math.abs(speed));
		planetaryTalon.set(speed);
		wormTalon.set(speed);
		
		//SmartDashboard.putNumber("Get climber motor position", talon.getPosition());
	}
	
	public double getPlanetarySpeed() {
		return planetaryTalon.get();
	}
	
	public double getWormSpeed() {
		return wormTalon.get();
	}
}