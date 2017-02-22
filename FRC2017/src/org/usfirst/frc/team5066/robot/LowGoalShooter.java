package org.usfirst.frc.team5066.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LowGoalShooter {
	private CANTalon lowShooter;
	AnalogInput leftShootUltra;
	AnalogInput rightShootUltra;
	boolean prevShoot;

	public LowGoalShooter(int shootPort) {// , int leftShoot, int rightShoot){
		lowShooter = new CANTalon(shootPort);
		prevShoot = false;
		// leftShootUltra = new AnalogInput(leftShoot);
		// rightShootUltra = new AnalogInput(rightShoot);
	}

	public void setSpeed(boolean shoot) {
		// Checks for illegal value
		// speed /= Math.max(1, Math.abs(speed));
		
		
		/*
		if (shoot && leftShootUltra.getAverageVoltage() < 3.0 && !prevShoot) {
			lowShooter.set(-0.3);
			Timer.delay(.5);
			lowShooter.set(.7);
			//TODO finish pulling back.
		}
		else if (shoot && leftShoot)

		/*
		 * else if(shoot > 0.5){ lowShooter.set(1.0); feedMotor.set(-0.25); }
		 *
		else {
			lowShooter.set(0.0);
		}

		if (shoot)
			prevShoot = true;
		else
			prevShoot = false;
		// For Testing
		*/
		SmartDashboard.putNumber("Low shooter speed", lowShooter.getSpeed());
	}

	/*
	 * public double getLeftInches(){ return leftShootUltra.getAverageVoltage()
	 * }
	 */
	public double getSpeed() {
		return lowShooter.get();
	}
}
