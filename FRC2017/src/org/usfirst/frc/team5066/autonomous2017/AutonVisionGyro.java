package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

public class AutonVisionGyro extends AutonControlScheme {
	
	public AutonVisionGyro(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake){
		super(drive, shooter, intake);
	}
	
	@Override
	public void moveEncoderAuton() {
		
		//slide in front of the boiler
		super.horizontal(.75, 32, true, 5);
		
		super.shoot(3);
		
		//move backward 136.62in
		super.vertical(0.75, 136.62, true, 5);
		
	}
	
	@Override
	public int getRecordableURL() {
		return 1;
	}
	
	@Override
	public int[] getSteps(){
		int[] steps = {8, 1, 7, 0};
		return steps;
	}
}
