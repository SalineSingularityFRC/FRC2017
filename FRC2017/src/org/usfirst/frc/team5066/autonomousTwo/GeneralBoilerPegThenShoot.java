package org.usfirst.frc.team5066.autonomousTwo;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

public abstract class GeneralBoilerPegThenShoot extends AutonMode {
	
	private double angleMultiplier;
	private boolean onBlueAlliance;
	AHRS gyro;
	
	public GeneralBoilerPegThenShoot(boolean onBlueAlliance, AHRS gyro) {
		angleMultiplier = onBlueAlliance ? 1.0 : -1.0;
		this.onBlueAlliance = onBlueAlliance;
		this.gyro = gyro;
	}
	
	public void run(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		
		
		AutonMode sidePegMode = onBlueAlliance ? new LeftPeg() : new RightPeg(gyro);
		sidePegMode.run(drive, shooter, intake);

		Timer t = new Timer();
		t.delay(5);

		drive.driveStraight(83.081, -0.4, 0.05, 10);
		drive.rotateTo(angleMultiplier * -15.0, 8);
		drive.driveStraight(30, -0.25, 0.05, 10);

		shooter.setSpeed(true, true);

	}

}
