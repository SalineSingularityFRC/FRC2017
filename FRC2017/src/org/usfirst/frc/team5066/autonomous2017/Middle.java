package org.usfirst.frc.team5066.autonomous2017;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5066.controller2017.FindGreenAreas;
import org.usfirst.frc.team5066.controller2017.Pipeline;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.Robot;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Middle extends Command{
	
	private SingularityDrive drive;
	SpeedMode speedMode;
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private VisionThread visionThread;
	private double centerX = 0.0;
	private double centerY = 0.0;
	private final Object imgLock = new Object();
	@Override
	public void start(){
		
		//UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    //camera.setResolution(324, 240);
	    /*
		visionThread = new VisionThread(camera, new FindGreenAreas(), pipeline -> 
	    {
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            
	            synchronized (imgLock)
	            {
	                centerX = r.x + r.width +r.width*0.76;
	                //centerY = r.y + (r.height / 2 );  
	            }
	        }
	    });
	    visionThread.start();
	    */
	    
	}
	
	@Override
	public void initialize(){
		visionThread = new VisionThread(Robot.camera, new FindGreenAreas(), pipeline -> 
	    {
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            
	            synchronized (imgLock)
	            {
	                centerX = r.x + r.width +r.width*0.76;
	                //centerY = r.y + (r.height / 2 );  
	            }
	        }
	    });
	    visionThread.start();
	}
	
	@Override
	public void execute(){//double cntrX, double cntrY) {

		/*
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(324, 240);
	    visionThread = new VisionThread(camera, new FindGreenAreas(), pipeline -> 
	    {
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            
	            synchronized (imgLock)
	            {
	                centerX = cntrX; 
	                centerY = cntrY;
	            }
	        }
	    });
	    visionThread.start();
		*/
		double centerX;
		synchronized (imgLock) {
			centerX = this.centerX;
		}
		
		if (centerX >= 140 && centerX <= 180){ //&& centerY == 120){
			
			drive.hDrive(0.1, 0, 0, true, speedMode);
			
		}
		else{
			
			if(centerX > 180 ){
				drive.hDrive(0, 0.2, 0, true, speedMode);
				
			}
			else{
				
				drive.hDrive(0, -0.2, 0, true, speedMode);
			}
			
		}
	
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void end(){
		
	}
}