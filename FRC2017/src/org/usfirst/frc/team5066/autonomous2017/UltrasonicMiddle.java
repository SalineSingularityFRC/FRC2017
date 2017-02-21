package org.usfirst.frc.team5066.autonomous2017;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5066.controller2017.FindGreenAreas;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class UltrasonicMiddle extends Command {
	Ultrasonic leftGearUltra;
	Ultrasonic rightGearUltra;
	Ultrasonic rightBarrierUltra;
	Ultrasonic leftBarrierUltra;
	private SingularityDrive drive;
	SpeedMode speedMode;
	
	public void autonomousInit(){
		
	}
	
public void run(double cntrX, double cntrY) {
		/*
		if (leftBarrierUltra.getRangeInches() == rightBarrierUltra.getRangeInches() || centerX < 172){
			drive.hDriveTank(-0.2, -0.2, 0.0, false, SpeedMode.FAST);
			
		}
	    else if(leftGearUltra.getRangeInches() > rightGearUltra.getRangeInches() + 10){
	    	drive.hDriveTank(0.15, -0.15, 0.0, false, SpeedMode.FAST);
	    
	    }
	    else if(leftGearUltra.getRangeInches() < rightGearUltra.getRangeInches() - 10){
	    	drive.hDriveTank(-0.15, 0.15, 0.0, false, SpeedMode.FAST);
	    }

		else if(centerX >= 172){
			drive.hDriveTank(0, 0, -0.2, false, SpeedMode.FAST);
				
		}
		else if(centerX <= 152){	
			drive.hDriveTank(0, 0, 0.2, false, SpeedMode.FAST);
		}
	*/	
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
