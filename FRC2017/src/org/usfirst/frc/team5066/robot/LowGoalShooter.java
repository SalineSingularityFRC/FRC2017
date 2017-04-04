package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.library.RangeFinder;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class LowGoalShooter{
	
	/*
	 * shootSpeed is the speed of the ejector
	 * reverseSpeed is the speed while reversing the ejector
	 * maxMoveSpeed is the max speed to move while adjusting robot position
	 * reverseTime is the time to reverse the ejector
	 * shootDistVolts is the optimal voltage for the ultrasonic before shooting
	 */
	
	private static final double voltSpeed = 0.35, voltReverseSpeed = -0.4;
	private static final double encoderSpeed = 800.0, encoderReverseSpeed = -400.0;
	
	private static final double voltAutonSpeed = 0.35;
	private static final double encoderAutonSpeed = 800.0;
	
	private static double speed, reverseSpeed;
	private static double autonSpeed;
			
	private static final double maxMoveSpeed = 0.38, reverseTime = 0.4, shootDistVolts = 4.60;
	
	
	
	private CANTalon lowShooter;
	private SingularityDrive sd;
	RangeFinder rf;
	Timer timer;
	
	boolean encoder;
	
	boolean hasReversed;
	
	
	public LowGoalShooter(int shootPort, SingularityDrive sd, RangeFinder rf, boolean encoder){


		this(shootPort, encoder);
		
		this.sd = sd;
		this.rf = rf;
	}
	
	public LowGoalShooter(int shootPort, boolean encoder) {
		
		lowShooter = new CANTalon(shootPort);
		if (encoder) {
			//lowShooter.changeControlMode(CANTalon.TalonControlMode.Speed);
			
			lowShooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			lowShooter.reverseSensor(false);
			
			lowShooter.configNominalOutputVoltage(+0.0f, -0.0f);
			lowShooter.configPeakOutputVoltage(+12.0f, -12.0f);
			
			lowShooter.setAllowableClosedLoopErr(0);
			
			lowShooter.setProfile(0);
			lowShooter.setF(0.1097);
			
			lowShooter.setP(0.1);
			lowShooter.setI(0.001);
			lowShooter.setD(0.1);
			
			
			speed = encoderSpeed;
			reverseSpeed = encoderReverseSpeed;
			
			autonSpeed = encoderAutonSpeed;
			
		}
		else {
			speed = voltSpeed;
			reverseSpeed = voltReverseSpeed;
			
			autonSpeed = voltAutonSpeed;
		}
		this.encoder = encoder;
		
		hasReversed = false;

		timer = new Timer();
	}
	
	public void setSpeed(boolean shoot, boolean auton){
		
		if(shoot){
			
			if (encoder) {
				lowShooter.changeControlMode(CANTalon.TalonControlMode.Speed);
			}
			else {
				lowShooter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			}
			
			//moveFromUltra();
			
			//If we haven't already reversed while shooting this attempt,
			//reverse the shooter for a brief time while still adjusting position
			if (!hasReversed) {
				timer.reset();
				timer.start();
				hasReversed = true;
				
				
				while (timer.get() < reverseTime) {
					lowShooter.set(reverseSpeed);
				}
			}
			
			if (!auton) {
				lowShooter.set(speed);
			}
			else {
				lowShooter.set(autonSpeed);
			}
				
			SmartDashboard.putString("DB/String 5", "encoder: " + lowShooter.getEncVelocity());
			SmartDashboard.putString("DB/String 4", "speed: " + lowShooter.getSpeed());
			
		}
		
		//If not shooting, dont't. Set hasReversed false for next time we shoot
		else {
			lowShooter.set(0.0);
			hasReversed = false;
		}
		
		//SmartDashboard.putString("DB/String 4", "Encoder: " + lowShooter.getSpeed());
	}
	
	
	
	public void setSpeed(double speed) {
		if (speed > 0.4) lowShooter.set(speed);
		else if (speed < -0.4) lowShooter.set(-reverseSpeed);
		else lowShooter.set(0.0);
	}
	
	public double getSpeed(){
		return lowShooter.get();
	}
	
	private void moveFromUltra() {
		//Move the robot to be the optimal distance from the wall
		//We want to do this constantly while shooting
		if (rf.getVolts() - shootDistVolts > 0)
			sd.hDriveStraightConstant(Math.min(maxMoveSpeed, rf.getVolts() - shootDistVolts), 0.0, 0.0);
		else
			sd.hDriveStraightConstant(Math.max(-maxMoveSpeed, rf.getVolts() - shootDistVolts), 0.0, 0.0);
	}
}