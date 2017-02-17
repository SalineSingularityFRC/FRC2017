package org.usfirst.frc.team5066.controller2017.controlSchemes;

import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.usfirst.frc.team5066.library.SpeedMode;

public class AutonRightFuel extends AutonControlScheme {

	@Override
	public void moveAuton() {
		
		//Drive to boiler
		super.vertical(-0.75, -52.0);
		//turn to face boiler
		super.rotation(-0.75, -45);
		//drive to the boiler
		super.vertical(0.75, 52.0);
		
		//shoot fuel
		EncoderAuto.shooter.setSpeed(true);
		Timer.delay(4);
		//go to the peg
		super.vertical(-0.75, -136.62);
		
		//VISION:
	}

}
