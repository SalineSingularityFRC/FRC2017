package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class SingularityLEDs {
	
	public Solenoid BotLED1, BotLED2, BotLED3, TopLED1, TopLED2;
	
	Timer timer;
	boolean hasSwitched;
	
	public SingularityLEDs(int port1, int port2, int port3, int portT1, int portT2){
		BotLED1 = new Solenoid(port1);
		BotLED2 = new Solenoid(port2);
		BotLED3 = new Solenoid(port3);
		TopLED1 = new Solenoid(portT1);
		TopLED2 = new Solenoid(portT2);
		
		timer = new Timer();
		hasSwitched = false;
	}
	
	public void turnOffAll(){
		BotLED1.set(false);
		BotLED2.set(false);
		BotLED3.set(false);
		TopLED1.set(false);
		TopLED2.set(false);
	}
	
	public void turnOnAll(){
		BotLED1.set(true);
		BotLED2.set(true);
		BotLED3.set(true);
		TopLED1.set(true);
		TopLED2.set(true);
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
		if(!hasSwitched){
			timer.start();
			hasSwitched = true;
		}
		
		else if(timer.get() > 0.25){
			TopLED1.set(false);
			TopLED2.set(true);
			BotLED1.set(false);
			BotLED3.set(true);
			
		}
		else if(timer.get() > 0.5){
			TopLED1.set(true);
			TopLED2.set(false);
			BotLED1.set(true);
			BotLED3.set(false);
			
			timer.reset();
			hasSwitched = false;
		}
	}
	
	public void spin(){
		if(!hasSwitched){
			timer.start();
			hasSwitched = true;
		}
		
		else if(timer.get() > 0.1){
			turnOffAll();
			BotLED1.set(true);
		}
		else if(timer.get() > 0.2){
			turnOffAll();
			BotLED2.set(true);
		}
		else if(timer.get() > 0.3){
			turnOffAll();
			BotLED3.set(true);
		}
		else if(timer.get() > 0.4){
			turnOffAll();
			TopLED1.set(true);
		}
		else if(timer.get() > 0.5){
			turnOffAll();
			TopLED2.set(true);
			timer.reset();
			timer.reset();
		}
	}

}
