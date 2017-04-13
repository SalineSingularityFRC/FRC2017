package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import edu.wpi.first.wpilibj.Timer;

public class RedCenterPegShoot extends AutonMode {

	@Override
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		AutonMode autonMode = new CenterPeg();
		autonMode.run(drive, shooter, intake);
		
		Timer.delay(4);
		
		drive.driveStraight(-80, 0.6, 0.06, 4);
		drive.rotateTo(90, 5);
		drive.driveStraight(210, 0.6, 0.06, 6);
		//drive.rotateTo(degrees, maxTime);
	}

}
