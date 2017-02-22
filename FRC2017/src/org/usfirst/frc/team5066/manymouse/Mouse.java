package org.usfirst.frc.team5066.manymouse;

public class Mouse {
	
	
	// angle measures (in degrees)
	private double theta;
	private double phi;
	private double alpha;

	public double r;

	// sensor measured change in position variables
	public double sensor_dx, sensor_dy;
	
	/**
	 * 
	 * @param theta - The angle defining the mouse's location from the center of the robot in <b>radians<b>
	 * @param r - The distance defining the mouse's location from the center of the robot 
	 * @param phi - the angle defining the rotation of the sensor in <b>radians</b>
	 * 
	 * Refer to Stephen Bell's paper on Mouse Odometry, section III
	 */
	
	public Mouse(double theta, double r, double phi) {
		alpha = theta + phi;
		this.r = r;
		this.phi = phi;
	}
	
	//Gets the coefficient array (Bell paper figure III.11) for ONE sensor
	//Append to arrays for other sensors
	public double[][] getCoefficientArray() {
		return new double[][] { {Math.sin(this.getAlpha()), -1 * Math.cos(this.getAlpha()), this.getR() * Math.cos(this.getPhi())},
			                    {Math.cos(this.getAlpha()), Math.sin(this.getAlpha()),      this.getR() * Math.sin(this.getPhi())}
		};
	}
	
	public double getTheta() {
		return theta;
	}
	
	public double getPhi() {
		return phi;
	}
	
	public double getAlpha(){
		return alpha;
	}
	
	public double getR(){
		return r;
	}

}
