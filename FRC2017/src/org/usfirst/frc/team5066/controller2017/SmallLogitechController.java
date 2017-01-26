package org.usfirst.frc.team5066.controller2017;

import edu.wpi.first.wpilibj.Joystick;
/** A simple Joystick subclass that makes button inputs more 
 * comprehensive and efficient when using the Logitech Controller. 
 * 
 * 
 * @author Max Brenner
 *
 */
public class SmallLogitechController extends Joystick {

	public SmallLogitechController(int port) {
		super(port);
	}
	
	//public boolean getTrigger() {
		//return this.getRawButton(2);
	//}
}
