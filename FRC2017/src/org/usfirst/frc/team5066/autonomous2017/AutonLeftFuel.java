
package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

public class AutonLeftFuel extends AutonControlScheme {
	
	public AutonLeftFuel(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake){
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
		//int[] steps = {5, 6, 9, 4, 10, 0};
		int[] steps = {9, 12, 8, 13, 0};
		return steps;
	}
}
