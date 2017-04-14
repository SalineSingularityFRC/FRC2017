package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LeftPeg extends AutonMode{
	
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {

		SmartDashboard.putString("Left Peg:", "Started");
		drive.driveStraight(92.96, 0.35, 0.05, 10.0);
		drive.rotateTo(60.0, 10.0);
		drive.driveStraight(83.081, 0.4, 0.05, 10.0);
		SmartDashboard.putString("Left Peg:", "Finished");
		
	}
	
}
