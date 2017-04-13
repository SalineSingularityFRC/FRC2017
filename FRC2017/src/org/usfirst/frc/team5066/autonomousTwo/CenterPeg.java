package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

public class CenterPeg extends AutonMode {

	@Override
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		drive.driveStraight(120, 0.25, 0.05, 5);
		
	}

}
