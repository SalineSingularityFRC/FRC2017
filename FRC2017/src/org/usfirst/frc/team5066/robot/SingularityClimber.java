package org.usfirst.frc.team5066.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SingularityClimber {
	private CANTalon planetaryTalon, wormTalon;
	double climberSpeedConstant;
	
	/**
	 * A constructor for SingularitClimber, which controls the climber
	 * @param planetaryPort the CANTalon port for one motor (we don't know which one)
	 * @param wormPort the CANTalon port for the other motor (still don't know)
	 */
	public SingularityClimber(int planetaryPort, int wormPort){
		planetaryTalon = new CANTalon(planetaryPort);
		wormTalon = new CANTalon(wormPort);
		
		//talon.setPosition(0.0);
	}
	
	/**
	 * Set the speed based on percent voltage
	 * @param speed between -1.0 and 1.0
	 */
	public void setSpeed(double speed){
		//Checks for illegal value (and deports them. But now we have a wall,
		//so this won't be a problem anymore;))
		speed /= Math.max(1, Math.abs(speed));
		planetaryTalon.set(-speed);
		wormTalon.set(speed);
		
		//SmartDashboard.putNumber("Get climber motor position", talon.getPosition());
	}
	
	/**
	 * 
	 * @return the percent voltage we are sending to the CANTalon
	 * of one of the motors
	 */
	public double getPlanetarySpeed() {
		return planetaryTalon.get();
	}
	
	/**
	 * 
	 * @return the percent voltage we are sending to the CANTalon
	 * of one of the motors
	 */
	public double getWormSpeed() {
		return wormTalon.get();
	}
}