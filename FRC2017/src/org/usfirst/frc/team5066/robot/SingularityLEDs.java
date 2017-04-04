package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class SingularityLEDs {
	
	public Solenoid BotLED1, BotLED2, BotLED3, TopLED1, TopLED2;
	
	Timer timer;
	boolean hasStarted;
	
	public SingularityLEDs(int port1, int port2, int port3, int portT1, int portT2){
		BotLED1 = new Solenoid(port1);
		BotLED2 = new Solenoid(port2);
		BotLED3 = new Solenoid(port3);
		TopLED1 = new Solenoid(portT1);
		TopLED2 = new Solenoid(portT2);
		
		timer = new Timer();
		hasStarted = false;
	}
	
	public void turnOffAll(){
		BotLED1.set(false);
		BotLED2.set(false);
		BotLED3.set(false);
		TopLED1.set(false);
		TopLED2.set(false);
	}
	
	public void turnOnBottom(){
		BotLED1.set(true);
		BotLED2.set(true);
		BotLED3.set(true);
	}
	
	public void turnOffBottom(){
		BotLED1.set(false);
		BotLED2.set(false);
		BotLED3.set(false);
	}
	
	public void turnOnTop(){
		TopLED1.set(true);
		TopLED2.set(true);
	}
	
	public void turnOffTop(){
		TopLED1.set(false);
		TopLED2.set(false);
	}
	
	/*
	 * To be impelemented in teleop periodic
	 */
	public void oscillate(){
		if(hasStarted){
			TopLED1.set(false);
			TopLED2.set(true);
			BotLED1.set(false);
			BotLED3.set(true);
			
			timer.start();
			hasStarted = true;
		}
		if(timer.get() > 0.4){
			TopLED1.set(true);
			TopLED2.set(false);
			BotLED1.set(true);
			BotLED3.set(false);
			
			hasStarted = false;
		}
	}

}
