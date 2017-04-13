package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import edu.wpi.first.wpilibj.Timer;

public class BlueBoilerPegThenShoot {
	
public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		
		AutonMode leftPegMode = new LeftPeg();
		leftPegMode.run(drive, shooter, intake);
		
		Timer t = new Timer();
		t.delay(5);
		
		drive.driveStraight(83.081, -0.4, 0.05, 10);
		drive.rotateTo(-15.0, 8);
		drive.driveStraight(30, -0.25, 0.05, 10);
		
		shooter.setSpeed(true, true);
		
		
	}
	
}
