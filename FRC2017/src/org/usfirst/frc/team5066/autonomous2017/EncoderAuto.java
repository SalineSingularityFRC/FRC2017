package org.usfirst.frc.team5066.autonomous2017;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.Robot;

import com.ctre.CANTalon;

public class EncoderAuto extends Robot {
/*
	//Constants
	public static final double DistancePerRevolution = 12.56;//circumference of wheels
	//TODO Fine tune this for our specific model
	//public static final double kPulsesPerRevolution = 1024;
	//public static final double PositionPerPulse = DistancePerRevolution / kPulsesPerRevolution;
	/*
	static CANTalon frontLeftEncoder;
	static CANTalon rearLeftEncoder;
	static CANTalon middleLeftEncoder;
	static CANTalon frontRightEncoder;
	static CANTalon rearRightEncoder;
	static CANTalon middleRightEncoder;
	
	SingularityDrive drive;
	//initialize encoders. true = don't invert counter direction
	public EncoderAuto(SingularityDrive drive){//int ePFL, int ePRL, int ePML, int ePFR, int ePRR, int ePMR) {
	//public EncoderAuto(int ePFLa, int ePFLb, int ePRLa, int ePRLb, int ePMLa, int ePMLb, int ePFRa, int ePFRb, int ePRRa, int ePRRb, int ePMRa, int ePMRb){
	
		this.drive = drive;
		/*frontLeftEncoder = new CANTalon(ePFL);  //(ePFLa, ePFLb, true, EncodingType.k4X);
		rearLeftEncoder = new CANTalon(ePRL); //(ePRLa, ePRLb, true, EncodingType.k4X);
		middleLeftEncoder = new CANTalon(ePML); //(ePMLa, ePMLb, true, EncodingType.k4X);
		frontRightEncoder = new CANTalon(ePFR); //(ePFRa, ePFRa, true, EncodingType.k4X);
		rearRightEncoder = new CANTalon(ePRR); //(ePRRa, ePRRb, true, EncodingType.k4X);
		middleRightEncoder = new CANTalon(ePMR); //(ePMRa, ePMRb, true, EncodingType.k4X);
		
	}
	//6 motor drive
	//public static SingularityDrive drive = new SingularityDrive(2, 6, 3, 4, 10, 7);
	//public static LowGoalShooter shooter = new LowGoalShooter(8);
	
	public void resetAll(){
		drive.setPosition(drive.m_frontLeftMotor);
		drive.m_leftMiddleMotor.reset();
		drive.m_frontRightMotor.reset();
	}
	
	public static double getAverageLeftPosition(){
		return (frontLeftEncoder.getPosition() + rearLeftEncoder.getPosition()) / 2;
	}
	
	public static double getAverageRightPosition(){
		return (frontRightEncoder.getPosition() + rearRightEncoder.getPosition()) / 2;
	}
	
	public static double getAverageMiddlePosition(){
		return (middleLeftEncoder.getPosition() + middleRightEncoder.getPosition()) / 2;
	}
*/
}
