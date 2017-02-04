package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;

public class MoveBackwards implements AutonomousMode{
	
	private SingularityDrive drive;
	SpeedMode speedMode;
	public void run() {
		
		drive.hDrive(-1, 0, 0, true, speedMode);
		
		
		//move BACKWARDS really fast
		
	}
	
}
