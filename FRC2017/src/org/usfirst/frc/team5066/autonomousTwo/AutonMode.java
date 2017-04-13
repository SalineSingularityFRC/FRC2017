package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

public abstract class AutonMode {

	public abstract void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake);
	
}
