package org.usfirst.frc.team5066.autonomous2017;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5066.controller2017.Pipeline;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Middle implements AutonomousMode{
	
	private SingularityDrive drive;
	SpeedMode speedMode;
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private VisionThread visionThread;
	private double centerX = 0.0;
	private double centerY = 0.0;
	private final Object imgLock = new Object();
	public void autonomousInit(){
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(324, 240);
	    visionThread = new VisionThread(camera, new Pipeline(), pipeline -> 
	    {
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            
	            synchronized (imgLock)
	            {
	                centerX = r.x + r.width +r.width*0.76;
	                centerY = r.y + (r.height / 2 );  
	            }
	        }
	    });
	    visionThread.start();
	    
	    
	}

	public void run(double centerX, double centerY) {

		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(324, 240);
	    visionThread = new VisionThread(camera, new Pipeline(), pipeline -> 
	    {
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            
	            synchronized (imgLock)
	            {
	                centerX = r.x + r.width +r.width*0.76; 
	                centerY = r.y + (r.height / 2 );   
	            }
	        }
	    });
	    visionThread.start();
		
		if (centerX == 160 && centerY == 120){
			
			drive.hDrive(0.1, 0, 0, true, speedMode);
			
		}
		else{
			
			if(centerX > 160 ){
				drive.hDrive(0, 0.2, 0, true, speedMode);
				
			}
			else{
				
				drive.hDrive(0, -0.2, 0, true, speedMode);
			}
			
		}
		
	}
}