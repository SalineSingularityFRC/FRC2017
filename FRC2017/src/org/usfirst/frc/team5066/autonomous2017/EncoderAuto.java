package org.usfirst.frc.team5066.autonomous2017;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.Robot;

public class EncoderAuto extends Robot {

	//Constants
	public static final double kDistancePerRevolution = 12.56;//circumference of wheels
	//TODO Fine tune this for our specific model
	public static final double kPulsesPerRevolution = 1024;
	public static final double kDistancePerPulse = kDistancePerRevolution / kPulsesPerRevolution;
	
	//initialize encoders. true = don't invert counter direction
	static Encoder frontLeftEncoder = new Encoder(11, 12, true, EncodingType.k4X);
	static Encoder rearLeftEncoder = new Encoder(13, 14, true, EncodingType.k4X);
	static Encoder middleLeftEncoder = new Encoder(13, 14, true, EncodingType.k4X);
	static Encoder frontRightEncoder = new Encoder(13, 14, true, EncodingType.k4X);
	static Encoder rearRightEncoder = new Encoder(13, 14, true, EncodingType.k4X);
	static Encoder middleRightEncoder = new Encoder(13, 14, true, EncodingType.k4X);
	
	//6 motor drive
	public static SingularityDrive drive = new SingularityDrive(2, 6, 3, 4, 10, 7);
	public static LowGoalShooter shooter = new LowGoalShooter(8);
	/**
	 * Initialize robot
	 */
	public void robotInit(){
		frontLeftEncoder.setDistancePerPulse(kDistancePerPulse);
		rearLeftEncoder.setDistancePerPulse(kDistancePerPulse);
		middleLeftEncoder.setDistancePerPulse(kDistancePerPulse);
		frontRightEncoder.setDistancePerPulse(kDistancePerPulse);
		rearRightEncoder.setDistancePerPulse(kDistancePerPulse);
		middleRightEncoder.setDistancePerPulse(kDistancePerPulse);
		
	}
	
	public void autonomous() {
		resetAll();
		
	}
	
	public static void resetAll(){
		frontLeftEncoder.reset();
		rearLeftEncoder.reset();
		middleLeftEncoder.reset();
		frontRightEncoder.reset();
		rearRightEncoder.reset();
		middleRightEncoder.reset();
	}
	
	public static double getAverageLeftPosition(){
		return (frontLeftEncoder.getDistance() + rearLeftEncoder.getDistance()) / 2;
	}
	
	public static double getAverageRightPosition(){
		return (frontRightEncoder.getDistance() + rearRightEncoder.getDistance()) / 2;
	}
	
	public static double getAverageMiddlePosition(){
		return (middleLeftEncoder.getDistance() + middleRightEncoder.getDistance()) / 2;
	}

}
