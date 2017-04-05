package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class LEDTest {
	public Solenoid ledG;
	
	public LEDTest(int LEDPort){
		ledG = new Solenoid(LEDPort);
	}

	public void turnLEDOn(){
		ledG.set(true);
	}
}
