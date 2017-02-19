
package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;

public class AutonLeftFuel extends AutonControlScheme {
	
	private SingularityDrive drive;
	private LowGoalShooter shooter;
	
	public AutonLeftFuel(SingularityDrive drive, LowGoalShooter shooter){
		super(drive, shooter);
	}
	
	@Override
	public void moveAuton() {
		
		//slide in front of the boiler
		super.horizontal(.75, 30, true);
		
		/*
		//move backward 52in
		super.vertical(0.75, 52, true);
		
		//rotate counterclockwise 45
		super.rotation(0.75, 45, true);
		
		//move forward 52in
		super.vertical(0.75, 52, false);
		*/
		//SHOOT
		shooter.setSpeed(true);
		//timer
		Timer.delay(3);
		shooter.setSpeed(false);
		
		//move backward 136.62in
		super.vertical(0.75, 136.62, true);
		
	}	
}
