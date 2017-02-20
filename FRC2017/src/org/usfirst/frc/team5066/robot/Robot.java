package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.controlSchemes.ArcadeHDrive;
import org.usfirst.frc.team5066.controller2017.controlSchemes.BasicDrive;
import org.usfirst.frc.team5066.controller2017.controlSchemes.TankHDrive;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SingularityProperties;
import org.usfirst.frc.team5066.library.SingularityPropertyNotFoundException;

import org.usfirst.frc.team5066.controller2017.FindGreenAreas;
import org.usfirst.frc.team5066.controller2017.FindGreenAreasApp;
import org.usfirst.frc.team5066.controller2017.GripRunner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;

import java.io.IOException;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import org.usfirst.frc.team5066.autonomous2017.AutonLeft;
import org.usfirst.frc.team5066.autonomous2017.AutonLeftFuel;
import org.usfirst.frc.team5066.autonomous2017.AutonMiddle;
import org.usfirst.frc.team5066.autonomous2017.AutonRight;
import org.usfirst.frc.team5066.autonomous2017.AutonRightFuel;

import org.usfirst.frc.team5066.autonomous2017.AutonomousMode;
import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.autonomous2017.Middle;
import org.usfirst.frc.team5066.autonomous2017.Left;
import org.usfirst.frc.team5066.controller2017.Pipeline;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.Relay;


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
	SendableChooser autoChooser;
	
	
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
	//private DigitalOutput led;
	
	//Holds the current control scheme
	ControlScheme currentScheme;
    AutonControlScheme autonScheme;
	private boolean encoderHasRun;
	
	SingularityProperties props;


	//Holds the integer port id's for for the motors.The values are assigned when properties are loaded.
	//drive:
	int leftRearMotor, leftFrontMotor, rightFrontMotor, rightRearMotor, rightMiddleMotor, leftMiddleMotor; //TEST
	//climber:
	int climbMotor;
	//intake:
	int frontMotor;
	//low goal:
	int shootMotor, feedMotor;
	
	//CANTalon, Talon
	int speedControllerType;
	
	//Encoders
	
	
	//Speed constants
	double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;
	
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
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#robotInit()
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void robotInit() {
		//Create a SendableChooser object and add instances of the two commands to it. 
		//There can be any number of commands, and the one added as a default (addDefault), 
		//becomes the one that is initially selected. Notice that each command is included in an addDefault() 
		//or addObject() method call on the SendableChooser instance.
		
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Default Auto", new Middle());
		autoChooser.addObject("My Auto", new Left());
		SmartDashboard.putData("Auto choices", autoChooser);
		
		
		try {
			properties = new SingularityProperties("/home/lvuser/robot.properties");
		}
		catch (Exception e) {
			
			setDefaultProperties();
			
			properties = new SingularityProperties();
			DriverStation.reportError("error in properties", true); 
			
					    
		} finally {
			
			
			loadProperties();
			
			
			drive = new SingularityDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor, leftMiddleMotor, rightMiddleMotor, speedControllerType, .4, .8, 1.0);
			shooter = new LowGoalShooter(shootMotor);
			climber = new SingularityClimber(climbMotor);
			intake = new SingularityIntake(frontMotor);
			currentScheme = new BasicDrive(XBOX_PORT, BIG_JOYSTICK_PORT);
			autonScheme = new AutonMiddle(drive, shooter, intake);
			//led = new DigitalOutput(2);
			
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//TODO not sure what this will be
							
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(324, 240);
				
			visionThread = new VisionThread(camera, new FindGreenAreas(), pipeline -> {
		        if (!pipeline.filterContoursOutput().isEmpty()) {
		            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
			            
		            synchronized (imgLock) {
		            	centerX = r.x + r.width +r.width*0.76;
						//centerY = (r.height / 2 );
						          
		            }
		        }
			});
			visionThread.start();
		
		}
		
		
		
		/*
		 * js = new Joystick(XBOX_PORT);
		 * 
		 * 
		 */ 
		 //intake = new SingularityIntake(frontMotor);
		  //shooter = new LowGoalShooter(shootMotor);
		  //climber = new SingularityClimber(climbMotor);
		
		
		
		
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
		
		//++encoderHasRun = false;
		
		//When the autonomous period starts the SendableChooser object is polled to get
		//the selected command and that command is scheduled.
		
		autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
		
		/*try {
			autoSelected = props.getString("autonMode");
		} catch (SingularityPropertyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(autoSelected){
		
		case "forwards":
			autonMode = new Left();
			break;
		
		case "backwards":
			autonMode = new Middle();
			break;
		default:
			DriverStation.reportError("A O nothing in the Auto Mode", false);
		}
		

		
		autoSelected = chooser.getSelected();
		 autoSelected = SmartDashboard.getString("Auto Selector",
		 defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		*/

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		/*
		if(!encoderHasRun) {
			autonScheme.moveAuton();
			encoderHasRun = true;
		}
		*/
		
		
		
		//RobotBuilder will generate code automatically that 
		//runs the scheduler every driver station update period (about every 20ms). 
		//This will cause the selected autonomous command to run
		
		
		Scheduler.getInstance().run();
		
		
		
		/*switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
		
		
		
		autonMode.run(centerX, centerY);
		*/
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
	
	@Override
	public void testInit() {
		
	}
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		//led.set(true);
		//led.updateDutyCycle(255);
		//led.enablePWM(255);
	}
	
	private void loadProperties() {
		try{
			//Ports
			leftRearMotor = properties.getInt("leftRearMotor");
			leftFrontMotor = properties.getInt("leftFrontMotor");
			rightFrontMotor = properties.getInt("rightFrontMotor");
			rightRearMotor = properties.getInt("rightRearMotor");
			leftMiddleMotor = properties.getInt("leftMiddleMotor");
			rightMiddleMotor = properties.getInt("rightMiddleMotor");
			
			//CANTalon or Talon drive?
			speedControllerType = properties.getInt("speedControllerType");
			
			climbMotor = properties.getInt("climbMotor");
			
			frontMotor = properties.getInt("frontMotor");
			shootMotor = properties.getInt("shootMotor");
			
			slowSpeedConstant = properties.getDouble("slowSpeedConstant");
			normalSpeedConstant = properties.getDouble("normalSpeedConstant");
			fastSpeedConstant = properties.getDouble("fastSpeedConstant");
		
		} catch(SingularityPropertyNotFoundException e){
			DriverStation.reportError("The property \"" + e.getPropertyName()
				+ "was not found --> CODE SPLINTERED(CRASHED)!!!!!! \n _POSSIBLE CAUSES:\n - Property missing in file and defaults"
				+ "\n - Typo in property name in code or file/n - using a different properties file than the one that actually contains the property you are looking for",
				false);
			e.printStackTrace();
		}
		
		SmartDashboard.putString("DB/String 9",
				"slow: " + slowSpeedConstant + " | normal: " + normalSpeedConstant + "| fast: " +fastSpeedConstant);
	}
	
	private void setDefaultProperties() {

		//Holds the integer port id's for for the motors.The values are assigned when properties are loaded.
		
		//drive:
		properties.addDefaultProp("leftRearMotor", 2);
		properties.addDefaultProp("leftFrontMotor", 6);
		properties.addDefaultProp("rightFrontMotor", 3);
		properties.addDefaultProp("rightRearMotor", 4);
		properties.addDefaultProp("rightMiddleMotor", 10);
		properties.addDefaultProp("leftMiddleMotor", 7);
		
		//climber:
		properties.addDefaultProp("climbMotor", 5);
		
		//intake:
		properties.addDefaultProp("frontMotor", 9);
		
		//low goal:
		properties.addDefaultProp("shootMotor", 8);
		
		//CANTalon = 0 or Talon = 1
		speedControllerType = SingularityDrive.CANTALON_DRIVE;
		
		//Speed mode
		properties.addDefaultProp("slowSpeedConstant", 0.4);
		properties.addDefaultProp("normalSpeedConstant", 0.8);
		properties.addDefaultProp("fastSpeedConstant", 1.0);
		
		properties.addDefaultProp("autonMode", "backwards");
		
		/*
		 *high goal:
		 *highMotor = INSERTPORT;
		 */

		
	}
}

