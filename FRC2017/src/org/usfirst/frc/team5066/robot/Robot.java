package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import org.usfirst.frc.team5066.controller2017.ControlScheme;
//import org.usfirst.frc.team5066.controller2017.controlSchemes.ArcadearcadeSixWheel;
import org.usfirst.frc.team5066.controller2017.controlSchemes.BasicDrive;
import org.usfirst.frc.team5066.controller2017.controlSchemes.OneController;
//import org.usfirst.frc.team5066.controller2017.controlSchemes.TankarcadeSixWheel;
import org.usfirst.frc.team5066.library.RangeFinder;
import org.usfirst.frc.team5066.singularityDrive.*;//import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SingularityProperties;
import org.usfirst.frc.team5066.library.SingularityPropertyNotFoundException;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.library.playback.Reader;
import org.usfirst.frc.team5066.library.playback.Recorder;
import org.usfirst.frc.team5066.controller2017.FindGreenAreas;
import org.usfirst.frc.team5066.controller2017.FindGreenAreasApp;
import org.usfirst.frc.team5066.controller2017.GripRunner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import org.usfirst.frc.team5066.controller2017.Pipeline;
import org.usfirst.frc.team5066.controller2017.XboxController;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.Relay;

import org.usfirst.frc.team5066.autonomousTwo.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	
	
	//AUTON CONSTANT
	public static final double driveStraight = 1.03;
	
	
	//A boolean for which direction to move after placing a gear
	final boolean dashingRight = true;
	
	XboxController xbox;
	
	//Holds the current mode such as CenterPeg
	AutonMode autonMode;
	
	// Here are the options for using recordable autonomous mode.
    boolean record, play;
    // To which location the recordings should be stored (if a file of the same
    // name already exists (such as foobar.json), a new name will be chosen
    // (foobar(1).json, etc.))
    String recordingURL;
    // An array of which files should be played back during autonomous
    String[] playbackURLs;
 
    // These variables are necessary, but need not be initialized
    long initialTime;
    Reader reader;
    Recorder recorder;
    int currentRecordingIndex;
    
	//Constants for vision
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private double centerX = 160.0;
	private double centerY = 160.0;
	private double turn;
	
	private final Object imgLock = new Object();
	
	//For the LEDs
	SingularityLEDs robotLEDs;
	Solenoid greenLed, redLed, blueLed;
	
	final int portGreen = 0;
	final int portRed = 1;
	final int portBlue = 2;
	
	public static UsbCamera camera;
	public static UsbCamera climbCamera;
	
	//NEW gyro
	public AHRS ahrs;
	
	//OLD gyro
	public ADXRS450_Gyro gyro;
	
	//CONSTANT for driving straight
	//We might want to test this further
	public final double gyroRotationConstant = 0.05;
	
	//For the cheap red ultrasonics
	Ultrasonic redLeft;
	final int inputLeft = 4, outputLeft = 3;
	Ultrasonic redRight;
	final int inputRight = 9, outputRight = 8;
	final int autonModeUltraDist = 48;
	/*
	 * For the nice silver ultrasonics,
	 * plug the main port into analog, which is the port no.
	 * Also, plug the random red wire into the 12V/2A
	 * on the voltage Regulator Module
	 */
	RangeFinder silverLeft;
	final int ultraPortLeft = 0;
	
	//Arrays for Auton
	int[] autonSteps;
	int index;
	
	Timer timer;
	boolean timerHasStarted;
	Timer timer2;
	double rotationSpeed;
	
	//Holds the current control scheme
	ControlScheme currentScheme;
    AutonControlScheme autonScheme;
	private boolean preAutonHasRun;
	
	SingularityProperties props;


	//Holds the integer port id's for for the motors.The values are assigned when properties are loaded.
	//drive:
	int leftRearMotor, leftFrontMotor, rightFrontMotor, rightRearMotor, rightMiddleMotor, leftMiddleMotor; //TEST
	//climber:
	int climbPlanetary, climbWorm;
	//intake:
	int frontMotor;
	//low goal:
	int shootMotor, feedMotor;
	
	// Set CANTalon or Talon in DEFAULT_TALON_TYPE in SingularityDrive
	
	//Encoders
	boolean encoderShooter;
	
	
	PowerDistributionPanel pdp;
	
	
	//Speed constants
	double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;
	
	//private SingularityDrive drive;
	private SixWheelDrive drive;
	
	LowGoalShooter shooter;
	SingularityClimber climber;
	SingularityIntake intake;
	SingularityProperties properties;
	
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
		
		try {
			properties = new SingularityProperties("/home/lvuser/robot.properties");
		}
		catch (Exception e) {
			
			setDefaultProperties();
			
			properties = new SingularityProperties();
			DriverStation.reportError("error in properties", true); 
			
					    
		} finally {
			
			
			loadProperties();
			
			
			//robotLEDs = new SingularityLEDs(portGreen, portRed, portBlue);
			//greenLed = new Solenoid(portGreen);
			//redLed = new Solenoid(portRed);
			//blueLed = new Solenoid(portBlue);
			
			encoderShooter = true;
			
			//NEW gyro
			ahrs = new AHRS(SPI.Port.kMXP);
			
			
			gyro = new ADXRS450_Gyro();
			gyro.calibrate();
			
			//drive = new SingularityDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor, 
			//		leftMiddleMotor, rightMiddleMotor, speedControllerType, .4, .8, 1.0, driveStraight, ahrs);
			
			drive= new SixWheelDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor, 
					leftMiddleMotor, rightMiddleMotor, driveStraight, ahrs);
			
			shooter = new LowGoalShooter(shootMotor, encoderShooter);
			climber = new SingularityClimber(climbPlanetary, climbWorm);
			intake = new SingularityIntake(frontMotor);
			currentScheme = new OneController(XBOX_PORT, BIG_JOYSTICK_PORT);
			
			
			
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//TODO not sure what this will be
			//climbCamera = CameraServer.getInstance().startAutomaticCapture();
		    //climbCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		    
		    //camera = CameraServer.getInstance().startAutomaticCapture();
			//camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
			//camera.setExposureManual(1);
		    
			/*
		    visionThread = new VisionThread(camera, new FindGreenAreas(), pipeline -> {
		        if (!pipeline.filterContoursOutput().isEmpty()) {
		        	Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
		            synchronized (imgLock) {
		                centerX = r.x + (r.width / 2);
		                centerY = r.y + (r.height / 2 );
		            }
		        }
		    });
		    visionThread.start();
		    */
		    
		    
		    //camera.setExposureManual(1);
		    
		    redLeft = new Ultrasonic(inputLeft, outputLeft);
		    redLeft.setAutomaticMode(true);
		    
		    silverLeft = new RangeFinder(ultraPortLeft);
		    
		    //redRight = new Ultrasonic(inputRight, outputRight);
		    //redRight.setAutomaticMode(true);
		    
		    
		    xbox = new XboxController(XBOX_PORT);
		    
		    //autonMode = autonMode.RECORDABLE;
		    
		    timer2 = new Timer();
		    
		    pdp = new PowerDistributionPanel();
		    
		    //robotLEDs.turnBlue();
		
		}
	}

		
	public void disabledInit() {
        // Closes all readers and recorder (allows files to close and/or save)
        if (recorder != null) {
            recorder.close();
            recorder = null;
        }
        if (reader != null) {
            reader.close();
            reader = null;
        }
        
        
        //blueLed.set(true);
        //timer.start();
    }	
	
	public void disabledPeriodic() {
		/*
		double centerX;
		synchronized (imgLock) {
			centerX = this.centerX;
			centerY = this.centerY;
		}
		
		double turn = centerX - (IMG_WIDTH / 2);
		//FOR TESTING
		SmartDashboard.putString("DB/String 1", "Center X: " + centerX);
		SmartDashboard.putString("DB/String 2", "Center Y: " + centerY);
		SmartDashboard.putString("DB/String 3", "Turn: " + turn);
		*/
		SmartDashboard.putString("DB/String 6", "Ultra" + redLeft.getRangeInches());
		SmartDashboard.putNumber("gyro.getAngle(): ", gyro.getAngle());
		/*
		SmartDashboard.putString("DB/String 7", "Ultra Right: " + redRight.getRangeInches());
		*/
		
		SmartDashboard.putNumber("NEW GYRO ANGLE: ", ahrs.getAngle());
		
		drive.displayEncoder();
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
		
		
		//for leds
		//robotLEDs.oscillate();
		
//		\/ \/ \/ \/ \/ \/ \/ \/ \/ NEW AUTON CODE \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ 
		//If there is an error after changing schemes, add "ahrs" to the parentheses, or remove it.
		autonMode = new CenterPeg(gyroRotationConstant, ahrs);
		autonMode.run(drive, shooter, intake);
//		/\ /\ /\ /\ /\ /\ /\ /\ /\ ============== /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\
		
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	
	@Override
	public void autonomousPeriodic() {
		
	}
	
	public void teleopInit() {
		//camera.setExposureManual(50);
		
		//Set the ramp rate of drive CANTalons to a constant in Singularity Drive.
		//The constant is in volts per second (full power is about 12 volts)
		drive.rampVoltage();
		
		//blueLed.set(false);
		//redLed.set(true);
		//greenLed.set(true);
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		//TODO make LEDs work
		//currentScheme.controlLEDs(robotLEDs);
		
		//Drive all subsystems
		currentScheme.drive(drive, true);
		currentScheme.controlShooter(shooter);
		currentScheme.controlClimber(climber);
		currentScheme.controlIntake(intake);
		
		SmartDashboard.putString("DB/String 6", "Left Ultra: " + redLeft.getRangeInches());
		SmartDashboard.putNumber("gryo.getAngle(): ", gyro.getAngle());
		
		drive.displayEncoder();
		
		//SmartDashboard.putString("DB/String 6", "Right Ultra: " + redRight.getRangeInches());
		
		//This appears to be how to view the voltage on a particular motor.
		//NOT TESTED
		//pdp.getCurrent(leftFrontMotor);
		
		
	}
	
	@Override
	public void testInit() {
		if (record) {
            // This initializes the recorder. The former parameter is the keys,
            // and the latter is the defaults to use.
            recorder = new Recorder(new String[] {"vertical", "rotation", "horizontal", "intake", "shooter"}, 
            		new Object[] {0.0, 0.0, 0.0, 0.0, 0.0}, recordingURL);
        }
	}
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		/*
		if (recorder != null) {
            // xbox and js are two input controllers. These methods just return
            // joystick values (in the form of doubles)
            Object[] input = new Object[] { xbox.getLS_Y(), xbox.getRS_X(), xbox.getLS_X(),
                    0.3, xbox.getTriggerRight()};
 
            // Do stuff to drive with the inputs.
            drive.arcadeSixWheel((Double) input[0], (Double) input[2], (Double) input[3], true, SpeedMode.NORMAL);
            intake.setSpeed((Double) input[3]);
            shooter.setSpeed((Double) input[4] > 0.6);
 
            recorder.appendData(input);
        }
        
        */
		
		currentScheme.drive(drive, true);
		currentScheme.controlShooter(shooter);
		currentScheme.controlClimber(climber);
		currentScheme.controlIntake(intake);
		//currentScheme.controlLEDs(robotLEDs);
		
		
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
			
			climbPlanetary = properties.getInt("climbPlanetary");
			climbWorm = properties.getInt("climbWorm");
			
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
		properties.addDefaultProp("rightFrontMotor", 13);
		properties.addDefaultProp("rightRearMotor", 4);
		properties.addDefaultProp("rightMiddleMotor", 10);
		properties.addDefaultProp("leftMiddleMotor", 7);
		
		//climber:
		properties.addDefaultProp("climbPlanetary", 8);//8 is backwards for one motor
		properties.addDefaultProp("climbWorm", 12);
		
		//intake:
		properties.addDefaultProp("frontMotor", 5);
		
		//low goal:
		properties.addDefaultProp("shootMotor", 9);
		
		
		//Speed mode
		properties.addDefaultProp("slowSpeedConstant", 0.4);
		properties.addDefaultProp("normalSpeedConstant", 0.8);
		properties.addDefaultProp("fastSpeedConstant", 1.0);
		
		properties.addDefaultProp("autonMode", "backwards");
		
		//properties.addDefaultProp("record", "false");
		//properties.addDefaultProp("play", "true");
		
	}
	
	private Reader initializeReader(String playbackURL) {
        Reader reader;
        try {
            reader = new Reader(playbackURL);
            initialTime = System.currentTimeMillis();
        } catch (Exception e) {
            // This segment will execute if the file is missing or has the wrong
            // permissions
            reader = null;
            e.printStackTrace();
        }
        return reader;
    }
}

