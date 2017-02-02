package org.usfirst.frc.team5066.robot;

import edu.wpi.cscore.UsbCamera;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5066.autonomous2017.AutonomousMode;
import org.usfirst.frc.team5066.autonomous2017.MoveBackwards;
import org.usfirst.frc.team5066.autonomous2017.MoveForward;
import org.usfirst.frc.team5066.controller2017.Pipeline;
import org.usfirst.frc.team5066.library.SingularityProperties;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionRunner;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	
	AutonomousMode autonMode;
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private double centerX = 0.0;
	private double centerY = 0.0;
	private RobotDrive drive;
	
	private final Object imgLock = new Object();
	
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	SingularityProperties props;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//chooser.addDefault("Default Auto", defaultAuto);
		//chooser.addObject("My Auto", customAuto);
		//SmartDashboard.putData("Auto choices", chooser);
		
		props = new SingularityProperties("/home/lvuser/robot.properties"); //TODO not sure what this will be
		
		 UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		    camera.setResolution(324, 240);
		
		    visionThread = new VisionThread(camera, new Pipeline(), pipeline -> 
		    {
		        if (!pipeline.filterContoursOutput().isEmpty()) {
		            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
		            
		            synchronized (imgLock)
		            {
		                centerX = (r.x + (r.width / 2))-((r.width * (3/25))+ (r.width * 3));
		                centerY = (r.height / 2 );
		                
		            }
		        }
		    });
		    visionThread.start();
		        
		    drive = new RobotDrive(1, 2);
		}
		

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		
		autoSelected = props.getString("autonMode");
		
		switch(autoSelected){
		
		case "forwards":
			autonMode = new MoveForward();
			break;
		
		case "backwards":
			autonMode = new MoveBackwards();
			break;
		default:
			DriverStation.reportError("A O nothing in the Auto Mode", false);
		
		}
		
		
		
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		
		double centerX;
		synchronized (imgLock) {
			centerX = this.centerX;
		}
		double turn = centerX - (IMG_WIDTH / 2);
		drive.(-0.6, turn * 0.005);
		
		
		
		autonMode.run();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

