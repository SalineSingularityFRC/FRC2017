package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

public class LeftPeg extends AutonMode{
	
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		
		drive.driveStraight(92.96, 0.6, 0.05, 10);
		drive.rotateTo(60.0, 8);
		drive.driveStraight(83.081, 0.4, 0.05, 10);
		
	}
	
}
