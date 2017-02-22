package org.usfirst.frc.team5066.autonomous2017;

import edu.wpi.cscore.UsbCamera;

public interface AutonomousMode {
	
	
	public void run(UsbCamera camera, double centerX, double centerY);
}
