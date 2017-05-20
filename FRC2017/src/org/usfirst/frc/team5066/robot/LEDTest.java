package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class LEDTest {
	public Solenoid ledG;
	
	/**
	 * Constructor for LEDTest
	 * @param LEDPort
	 */
	public LEDTest(int LEDPort){
		ledG = new Solenoid(LEDPort);
	}
	
	/**
	 * Turns a single LED on
	 */
	public void turnLEDOn(){
		ledG.set(true);
	}
}
