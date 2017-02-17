package org.usfirst.frc.team5066.autonomous2017;

import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.usfirst.frc.team5066.library.SpeedMode;

public class AutonRight extends AutonControlScheme {
	
	@Override
	public void moveAuton() {
		
		//move backward 52in
		super.vertical(-0.75, -52);
		
		//rotate counterclockwise 45
		super.rotation(-0.75, -45);
		
		//move backward 136.62in
		super.vertical(-0.75, -84.62);
		
		
		//VISION:
		
		
	}
}