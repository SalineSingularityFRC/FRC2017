package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;

public class AutonMiddle extends AutonControlScheme {
	
	private SingularityDrive drive;
	private LowGoalShooter shooter;
	
	public AutonMiddle(SingularityDrive drive, LowGoalShooter shooter){
		super(drive, shooter);
	}
	
	@Override
	public void moveAuton() {
		
		//move backward 52in
		super.vertical(0.75, 93.25, true);
		
	}	
}
