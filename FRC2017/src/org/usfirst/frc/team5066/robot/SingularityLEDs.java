package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class SingularityLEDs {
	
	//0 green
	//1 red
	//2 blue
	
	public Solenoid LEDG, LEDR, LEDB;
	
	Timer timer;
	boolean hasSwitched;
	
	public SingularityLEDs(int port1, int port2, int port3){
		LEDG = new Solenoid(port1);
		LEDR = new Solenoid(port2);
		LEDB = new Solenoid(port3);
		
		LEDG.set(false);
		LEDR.set(false);
		LEDB.set(false);
		
		timer = new Timer();
		hasSwitched = false;
	}
	
	public void resetAll(){
		LEDG.set(false);
		LEDR.set(false);
		LEDB.set(false);
	}
	
	public void turnBlue(){
		LEDB.set(true);
		LEDR.set(false);
		LEDG.set(false);
	}
	
	public void turnYellow(){
		LEDB.set(false);
		LEDG.set(true);
		LEDR.set(true);
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
			LEDR.set(true);
			LEDG.set(true);
			LEDB.set(false);
		}
		else if(timer.get() > 0.5){
			LEDR.set(false);
			LEDG.set(false);
			LEDB.set(true);
			
			timer.reset();
			hasSwitched = false;
		}
	}
	
	public void flashBlue(){
		if(!hasSwitched){
			timer.start();
			hasSwitched = true;
		}
		else if(timer.get() > 0.2){
			LEDB.set(true);
		}
		else if(timer.get() > 0.4){
			LEDB.set(false);
			timer.reset();
			hasSwitched = false;
		}
	}
	
	public void flashYellow(){
		if(!hasSwitched){
			timer.start();
			hasSwitched = true;
		}
		else if(timer.get() > 0.2){
			LEDR.set(true);
			LEDG.set(true);
		}
		else if(timer.get() > 0.4){
			LEDR.set(false);
			LEDG.set(false);
			timer.reset();
			hasSwitched = false;
		}
	}
}
