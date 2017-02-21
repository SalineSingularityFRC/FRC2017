package org.usfirst.frc.team5066.autonomous2017;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5066.controller2017.FindGreenAreas;
import org.usfirst.frc.team5066.controller2017.Pipeline;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Middle extends Command implements AutonomousMode{
	
	Ultrasonic leftGearUltra;
	Ultrasonic rightGearUltra;
	
	private SingularityDrive drive;
	SpeedMode speedMode;
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private VisionThread visionThread;
	private double centerX = 0.0;
	private double centerY = 0.0;
	private final Object imgLock = new Object();
	
	public void autonomousInit(){
		
		leftGearUltra = new Ultrasonic(1, 1);
		rightGearUltra = new Ultrasonic(2, 2);
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(324, 240);
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
	    
	    
	}

	public void run(double cntrX, double cntrY) {
		
		
		//start vision
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
		
		if (centerX > 152 && centerX < 172){
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
		
	}
		

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}