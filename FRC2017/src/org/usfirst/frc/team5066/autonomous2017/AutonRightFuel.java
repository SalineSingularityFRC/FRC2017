package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;

public class AutonRightFuel extends AutonControlScheme {
	
	private LowGoalShooter shooter;
	
	public AutonRightFuel(SingularityDrive drive, LowGoalShooter shooter){
		super(drive, shooter);
	}

	@Override
	public void moveAuton() {
		
		//slide in front of the boiler
		super.horizontal(0.75, 32, false);
		
		/*
		//Drive to boiler
		super.vertical(0.75, 52.0, true);
		//turn to face boiler
		super.rotation(0.75, 45, true);
		//drive to the boiler
		super.vertical(0.75, 52.0, false);
		*/
		
		//shoot fuel
		shooter.setSpeed(true);
		Timer.delay(3);
		//go to the peg
		shooter.setSpeed(false);
		super.vertical(0.75, 136.62, true);
		
	}

}
