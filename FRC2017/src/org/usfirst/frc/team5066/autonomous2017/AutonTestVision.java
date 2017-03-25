package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

public class AutonTestVision extends AutonControlScheme {

	public AutonTestVision(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		super(drive, shooter, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveEncoderAuton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRecordableURL() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[][] setSteps() {
		double[][] AutonVision = {{2, 3, 0}, 
								  {0, 0, 0}};
		return AutonVision;
	}

}
