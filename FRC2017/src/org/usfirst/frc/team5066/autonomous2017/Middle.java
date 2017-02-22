package org.usfirst.frc.team5066.autonomous2017;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5066.controller2017.FindGreenAreas;
import org.usfirst.frc.team5066.controller2017.Pipeline;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	private boolean visionBool = true;
	
	public Middle(SingularityDrive drive) {
		this.drive = drive;
	}
	
	public void autonomousInit(){
		
		leftGearUltra = new Ultrasonic(1, 1);
		rightGearUltra = new Ultrasonic(2, 2);
		/*
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
	    
*/	    
	}

	public void run(UsbCamera camera, double cntrX, double cntrY) {
		
		
		//start vision
		/*
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(324, 240);
	    */
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
	    if(visionBool){
	    	visionThread.start();
	    	visionBool = false;
	    }

		SmartDashboard.putNumber("X value", centerX);
		if (centerX > 152 && centerX < 172){
			drive.hDriveTank(0.4, 0.4, 0.0, false, SpeedMode.FAST);
			//Timer.delay(0.15);
			//drive.hDriveTank(0.0, 0.0, 0.0, false, SpeedMode.FAST);
			
		}
	    /*TODO fix encoders (they r giving a null pointer exception)
		else if(leftGearUltra.getRangeInches() > rightGearUltra.getRangeInches() + 4){
	    	drive.hDriveTank(0.15, -0.15, 0.0, false, SpeedMode.FAST);
	    
	    }
	    else if(leftGearUltra.getRangeInches() < rightGearUltra.getRangeInches() - 4){
	    	drive.hDriveTank(-0.15, 0.15, 0.0, false, SpeedMode.FAST);
	    }
	    */

		else if(centerX >= 172){
			SmartDashboard.putString("DB/String 2", "We should be moving left...");
			drive.hDriveTank(0.0, 0.0, -0.2, false, SpeedMode.FAST);
			//Timer.delay(0.2);
			//drive.hDriveTank(0.0, 0.0, 0.0, false, SpeedMode.FAST);
		}
		else if(centerX <= 152){	
			SmartDashboard.putString("DB/String 3", "We should be moving right...");
			drive.hDriveTank(0.0, 0.0, 0.2, false, SpeedMode.FAST);
			//Timer.delay(0.2);
			//drive.hDriveTank(0.0, 0.0, 0.0, false, SpeedMode.FAST);
		}
		else{
			drive.hDriveTank(-0.1, -0.1, 0.0, false, SpeedMode.FAST);
			//Timer.delay(0.2);
			//drive.hDriveTank(0.0, 0.0, 0.0, false, SpeedMode.FAST);
		}
	}
		

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}