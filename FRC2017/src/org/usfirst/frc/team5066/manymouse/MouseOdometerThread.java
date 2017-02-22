package org.usfirst.frc.team5066.manymouse;

public class MouseOdometerThread implements Runnable{
	
	public Mouse[] mice;
	
	private double world_x; // In feet
	private double world_y; // In feet
	private double world_z; // In feet
	
	public MouseOdometerThread(Mouse[] mice) {
		this.mice = mice;
	}
	
	
	@Override
	public void run() {
		//get mouse delta values
		//reset mouse delta values to 0.0 OR save previous values to compare to and calculate next delta
		//process mouse input using math methods
		//convert robot delta values into field coordinates relative to starting point
		//
	}

}
