package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

public class AutonDriveStraight extends AutonControlScheme{

	public AutonDriveStraight(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
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
	public int[] getSteps() {
		int[] steps = {1, 7, 0};
		return steps;
	}

}
