package org.usfirst.frc.team5066.manymouse;

import java.util.Arrays;

import Jama.Matrix;

public class OdometryMath {
	
	public static double[] calculateSensorMotion(Mouse[] mice, double robot_dx, double robot_dy, double robot_dw) {
		
		Matrix robotMotion = new Matrix(new double[][] {{robot_dx}, {robot_dy}, {robot_dw}});
		
		double[][] currentArray = {};
		//Coefficient Matrix
		for(Mouse m : mice){
			currentArray = append2DArray(currentArray, m.getCoefficientArray());
		}
		
		Matrix b = new Matrix(currentArray);
		
		/*
		double[] xArray = {Math.sin(m1.getAlpha()), Math.cos(m1.getAlpha()), Math.sin(m2.getAlpha()), Math.cos(m2.getAlpha())};
		double[] yArray = {-1 * Math.cos(m1.getAlpha()), Math.sin(m1.getAlpha()), -1 * Math.cos(m2.getAlpha()), Math.sin(m2.getAlpha())};
		double[] wArray = {m1.getR() * Math.cos(m1.getPhi()), m1.getR() * Math.sin(m1.getPhi()), m2.getR() * Math.cos(m2.getPhi()), m2.getR() * Math.sin(m2.getPhi())};
		
		double[][] infoArray = {xArray, yArray, wArray};
		Matrix b = new Matrix(infoArray);
		b = b.transpose();
		*/
		
		Matrix result = b.times(robotMotion);
		
		return result.getColumnPackedCopy();
	}
	
	public static double[] calculateRobotMotion(Mouse[] mice, double[][] sensorInputsArray) {
		if(2*mice.length == sensorInputsArray.length) {
			
			Matrix b = new Matrix(sensorInputsArray);
			
			double[][] currentArray = {};
			//Coefficient Matrix
			for(Mouse m : mice){
				currentArray = append2DArray(currentArray, m.getCoefficientArray());
			}
			double[][] aArray = currentArray;
			//System.out.println("Coefficient Array:" + Arrays.deepToString(aArray));
			Matrix a = new Matrix(aArray);
			
			System.out.println("A: " + a.getRowDimension() + " x " + a.getColumnDimension());
			System.out.println("B: " + b.getRowDimension() + " x " + b.getColumnDimension());
			
			System.out.println("A:");
			a.print(6, 3);

			System.out.println("B:");
			b.print(6, 3);
			
			Matrix robotMotion = a.solve(b);
			double[] robotMotionArray = robotMotion.getColumnPackedCopy();
			
			System.out.println("Robot motion array:" + Arrays.toString(robotMotionArray));
			return robotMotion.getColumnPackedCopy();
			
		} else {
			System.out.println("Number of sensor inputs and number of mice are incompatible");
			return null;
		}
		
		
	}
	
	public static double[][] append2DArray(double[][] a, double[][] b) {
        double[][] result = new double[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
}
