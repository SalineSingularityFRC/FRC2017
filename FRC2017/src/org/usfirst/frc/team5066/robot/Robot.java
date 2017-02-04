package org.usfirst.frc.team5066.robot;


import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SingularityProperties;
import org.usfirst.team5066.controller2017.controlSchemes.BasicDrive;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5066.autonomous2017.AutonomousMode;
import org.usfirst.frc.team5066.autonomous2017.MoveBackwards;
import org.usfirst.frc.team5066.autonomous2017.MoveForward;
import org.usfirst.frc.team5066.controller2017.Pipeline;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
	//Create a variable to hold a reference to a SendableChooser object.
	Command autonomousCommand;
	SendableChooser autochooser;
	
	
	AutonomousMode autonMode;
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private double centerX = 0.0;
	private double centerY = 0.0;
	
	private final Object imgLock = new Object();
	

	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	//Holds the current control scheme
	ControlScheme currentScheme;

	
	SingularityProperties props;


	//Holds the integer port id's for for the motors.The values are assigned when properties are loaded.
	//drive:
	int leftRearMotor, leftFrontMotor, rightFrontMotor, rightRearMotor, rightMiddleMotor, leftMiddleMotor; //TEST
	//climber:
	int climbMotor;
	//intake:
	int frontMotor, lowMotor, highMotor;
	//low goal:
	int shootMotor;
	
	private SingularityDrive drive;
	
	
	
	/*
	 *high goal:
	 *int highMotor;
	 */
	
	Joystick js;
	LowGoalShooter shooter;
	SingularityClimber climber;
	SingularityIntake intake;
	SingularityProperties properties;
	/*
	 * SingularityIntake intake;
	 * LowGoalShooter shooter;
	 * HighGoalShooter highShooter;
	 * SingularityClimber climber;
	 */
	
	final int XBOX_PORT = 0;
	final int BIG_JOYSTICK_PORT = 1;
	final int SMALL_JOYSTICK_PORT = 2;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		//Create a SendableChooser object and add instances of the two commands to it. 
		//There can be any number of commands, and the one added as a default (addDefault), 
		//becomes the one that is initially selected. Notice that each command is included in an addDefault() 
		//or addObject() method call on the SendableChooser instance.
		
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		chooser.addObject("My Auto2", customAuto);
		SmartDashboard.putData("Auto choices", chooser);

		
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
		        
		    //drive = new SingularityDrive(1, 2);

		
		
		loadDefaultProperties();
		
		drive = new SingularityDrive(2, 3, 4, 5, 6, 7, 0, .4, .8, 1.0);
		shooter = new LowGoalShooter(8);
		climber = new SingularityClimber(9);
		intake = new SingularityIntake(10, 11, 12);
		currentScheme = new BasicDrive(XBOX_PORT, BIG_JOYSTICK_PORT);
		
		
		
		
		/*
		 * js = new Joystick(XBOX_PORT);
		 * 
		 * 
		 * 
		 * intake = new SingularityIntake(highMotor, lowMotor, highMotor);
		 * shooter = new LowGoalShooter(shootMotor, 0);
		 * climber = new SingularityClimber(climbMotor, 0);
		 * 
		 * 
		 * 
		*/
		
		
		
		
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
		
		
		//When the autonomous period starts the SendableChooser object is polled to get
		//the selected command and that command is scheduled.
		autonomousCommand = (Command) autochooser.getSelected();
		autonomousCommand.start();
		
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
		

		/*
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		*/

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		//RobotBuilder will generate code automatically that 
		//runs the scheduler every driver station update period (about every 20ms). 
		//This will cause the selected autonomous command to run
		Scheduler.getInstance().run();
		
		
		
		double turn = centerX - (IMG_WIDTH / 2);
		
		drive.hDrive(vertical, horizontal, rotation, squaredInputs, speedMode);
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
		
		
		
		autonMode.run();
		
		}

	

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		currentScheme.drive(drive, true);
		currentScheme.controlShooter(shooter);
		currentScheme.controlClimber(climber);
		currentScheme.controlIntake(intake);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
	
	private void loadProperties() {
		
	}
	
	private void loadDefaultProperties() {
		
		//Holds the integer port id's for for the motors.The values are assigned when properties are loaded.
		//drive:
		/*
		leftMotor = INSERTPORT, rightMotor = INSERTPORT, middleMotor = INSERTPORT;
		//climber:
		climbMotor = INSERTPORT;
		//intake:
		frontMotor = INSERTPORT, lowMotor = INSERTPORT, highMotor = INSERTPORT;
		//low goal:
		shootMotor = INSERTPORT;
		
		/*
		 *high goal:
		 *highMotor = INSERTPORT;
		 */
		
	}
}

