package org.usfirst.frc.team5066.autonomous2017;

import java.util.ArrayList;

import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityIntake;

public class AutonMiddle extends AutonControlScheme {
	
	public AutonMiddle(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake){
		super(drive, shooter, intake);
	}
	
	@Override
	public void moveEncoderAuton() {
		
		//move backward 52in
		super.vertical(0.75, 93.25, true, 5);
		
	}
	
	@Override
	public int getRecordableURL() {
		return 2;
	}
	
	@Override
	public double[] getSteps(){
		double[] middle = {1, 2, 0};
		return middle;
	}
}
