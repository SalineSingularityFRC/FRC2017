package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2017.AutonControlScheme;
import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.controlSchemes.ArcadeHDrive;
import org.usfirst.frc.team5066.controller2017.controlSchemes.BasicDrive;
import org.usfirst.frc.team5066.controller2017.controlSchemes.OneController;
import org.usfirst.frc.team5066.controller2017.controlSchemes.TankHDrive;
import org.usfirst.frc.team5066.library.RangeFinder;
import org.usfirst.frc.team5066.library.SingularityDrive;
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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import org.usfirst.frc.team5066.autonomous2017.AutonLeft;
import org.usfirst.frc.team5066.autonomous2017.AutonLeftFuel;
import org.usfirst.frc.team5066.autonomous2017.AutonMiddle;
import org.usfirst.frc.team5066.autonomous2017.AutonMode;
import org.usfirst.frc.team5066.autonomous2017.AutonRight;
import org.usfirst.frc.team5066.autonomous2017.AutonRightFuel;
import org.usfirst.frc.team5066.autonomous2017.AutonTestVision;
import org.usfirst.frc.team5066.autonomous2017.AutonomousMode;
import org.usfirst.frc.team5066.autonomous2017.EncoderAuto;
import org.usfirst.frc.team5066.autonomous2017.Middle;
import org.usfirst.frc.team5066.autonomous2017.Left;
import org.usfirst.frc.team5066.controller2017.Pipeline;
import org.usfirst.frc.team5066.controller2017.XboxController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
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
	
	//AUTON CONSTANTS
	public static final double vertSpeedFast = 0.4, vertSpeedSlow = 0.4, 
			turnMultiplier = 0.010, ultraRotate = 0.1, driveStraight = 1.03,
			stopDist = 20;
	
	double autoSpeed;
	
	XboxController xbox;
	
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
	
	/*
	//Create a variable to hold a reference to a SendableChooser object.
	Command autonomousCommand;
	SendableChooser autoChooser;
	AutonomousMode autonMode;
	*/
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private double centerX = 160.0;
	private double centerY = 160.0;
	
	private final Object imgLock = new Object();
	
	public static UsbCamera camera;
	public static UsbCamera climbCamera;

	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	final int strafeXValue = 10;
	
	//For the gyro
	public ADXRS450_Gyro gyro;
	boolean needAngle;
	double origAngle;
	double rotateAngle = 0.05;
	
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
	double strafeSpeed;
	
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
	
	//CANTalon, Talon
	int speedControllerType;
	
	//Encoders
	boolean encoderShooter;
	
	//Speed constants
	double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;
	
	private SingularityDrive drive;
	
	
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
			silverLeft = new RangeFinder(ultraPortLeft);
			
			encoderShooter = true;
			gyro = new ADXRS450_Gyro();
			
			
			drive = new SingularityDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor, 
					leftMiddleMotor, rightMiddleMotor, speedControllerType, .4, .8, 1.0, driveStraight);
			shooter = new LowGoalShooter(shootMotor, true);
			climber = new SingularityClimber(climbPlanetary, climbWorm);
			intake = new SingularityIntake(frontMotor);
			currentScheme = new OneController(XBOX_PORT);
			
			autonScheme = new AutonTestVision(drive, shooter, intake);
			
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//TODO not sure what this will be
			climbCamera = CameraServer.getInstance().startAutomaticCapture();
		    climbCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		    
		    camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
			camera.setExposureManual(1);
		    
			
		    visionThread = new VisionThread(camera, new FindGreenAreas(), pipeline -> {
		        if (!pipeline.filterContoursOutput().isEmpty()) {
		            DriverStation.reportWarning("We got into visionthread", false);
		        	Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
		            synchronized (imgLock) {
		                centerX = r.x + (r.width / 2);
		                centerY = r.y + (r.height / 2 );
		            }
		        }
		    });
		    visionThread.start();
		    camera.setExposureManual(1);
		    //climbCamera.setExposureManual(1);
		    
		    redLeft = new Ultrasonic(inputLeft, outputLeft);
		    redLeft.setAutomaticMode(true);
		    
		    //redRight = new Ultrasonic(inputRight, outputRight);
		    //redRight.setAutomaticMode(true);
		    
		    
		    xbox = new XboxController(XBOX_PORT);
		    
		    autonMode = autonMode.RECORDABLE;
		
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
    }	
	
	public void disabledPeriodic() {
		
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
		
		/*SmartDashboard.putString("DB/String 6", "Ultra" + redLeft.getRangeInches());
		SmartDashboard.putString("DB/String 7", "Ultra Right: " + redRight.getRangeInches());
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
		index = 0;
		autonSteps = autonScheme.getSteps();
		needAngle = true;
		
		timer = new Timer();
		timerHasStarted = false;
		
		camera.setExposureManual(1);
		
		/*if (play) {
			try {
				reader = new Reader(recordingURL);
				initialTime = System.currentTimeMillis();
			}
			catch (Exception e) {
				reader = null;
				e.printStackTrace();
			}
		}
		
		*/
		
		// Chooses the right recording
   //     currentRecordingIndex = autonScheme.getRecordableURL();
   //
   //     // Recordable autonomous
   //     if (play) {
   //         reader = initializeReader(playbackURLs[currentRecordingIndex]);
 //       }

		/*
		//encoderHasRun = false;
		
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
		
		//IF ALL ELSE FAILS USE THIS
		drive.hDrive(0.5, 0.0, 0.0, false, SpeedMode.NORMAL);
		Timer.delay(4);
		drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		
		//@TODO This doesn't work
		drive.resetAll();
		drive.hDriveStraightEncoder(-0.5, 0.0, 0.0);
		Timer.delay(4);
		drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
		*/
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//autonSteps = autonScheme.getSteps();
		System.out.println(autonSteps + ", " + index);
		DriverStation.reportWarning("Current step" + autonSteps[index], false);
		//.putString("DB/String 0", "Current step" + autonSteps[index]);
		switch(autonSteps[index]) {
		/*
		 * This will be the last case in every auton scheme.
		 * Stops the motors.
		 */
		case 0:
			drive.hDrive(0, 0, 0, false, SpeedMode.NORMAL);
			break;
		/*
		 * This will drive the robot straight using the gyro
		 * Will move on when the ultrasonics read a distance < approx. 25in
		 */
		case 1:
			if (needAngle) {
				origAngle = gyro.getAngle();
				needAngle = false;
			}
			drive.hDriveTank(0.5 + rotateAngle * (origAngle - gyro.getAngle()), 0.5, 0.0, false, SpeedMode.FAST);
			if ((redLeft.getRangeInches()) < 40){//+ redRight.getRangeInches()) / 2 < 40) {
				if (!timerHasStarted) {
					timer.start();
				}
				else if (timer.get() > 0.2)
					index++;
			}
			else timer.reset();
		break;
		/*
		 * This case starts the vision code and lines up the robot with the peg
		 * Will move on when the ultrasonics read a distance < approx. 15in
		 */
		case 2:
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
			
			if (Math.abs(turn) < 20) autoSpeed = 0.40;
			else autoSpeed = 0.30;
			
			if(centerX == 0){
				drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
				break;
			}
			
			drive.hDrive(autoSpeed, turn * 0.005, -0.1,//(redRight.getRangeInches() - redLeft.getRangeInches()) * 0.01, 
					false, SpeedMode.FAST);
			
			if ((redLeft.getRangeInches()) > 14){ //+ redRight.getRangeInches()) / 2 < 14) {
				if (!timerHasStarted) {
					timer.start();
				}
				else if (timer.get() > 0.2)
					index++;
			}
			else timer.reset();
			
			SmartDashboard.putString("DB/String 0", "");
		break;
		/*
		 * This case drives the robot backwards for 0.8 seconds at 30% power.
		 * For inserting gear on to peg after case 2 has run.
		 */
		case 3:
			drive.hDrive(0.3, 0.0, 0.0, false, SpeedMode.FAST);
			if ((redLeft.getRangeInches()) < 6){// + redRight.getRangeInches()) / 2 < 6) {
				if (!timerHasStarted) {
					timer.start();
				}
				else if (timer.get() > 0.2)
					index++;
			}
			else timer.reset();
			break;
		/*
		 * This is only for driving forward to get past the base line.
		 */
		case 4:
			drive.hDrive(0.5, 0.0, 0.0, false, SpeedMode.FAST);
			Timer.delay(4);
			index++;
			break;
		/*
		 * Strafe across the boiler
		 */
		case 5:
			
			if (autonScheme instanceof AutonLeftFuel || autonScheme instanceof AutonLeft) {
				strafeSpeed = 0.5;
			}
			else if (autonScheme instanceof AutonRightFuel || autonScheme instanceof AutonRight) {
				strafeSpeed = -0.5;
			}
			
			else strafeSpeed = 0.0;
			
			drive.hDrive(0.0, strafeSpeed, 0.0, false, SpeedMode.FAST);
			Timer.delay(0.6);
			index++;
			break;
		/*
		 * Shoot the balls into the boiler
		 */
		case 6:
			
			shooter.setSpeed(true);
			Timer.delay(0.4);
			shooter.setSpeed(false);
			break;
		default:
			drive.hDrive(0.4, 0.0, 0.1, true, SpeedMode.FAST);
			Timer.delay(4);
			drive.hDrive(0.0, 0.0, 0.0, true, SpeedMode.FAST);
			SmartDashboard.putString("DB/String 0", "no auton found");
			break;
		}
		
		/*
		if (autonMode == autonMode.ENCODER && !preAutonHasRun) {
			autonScheme.moveEncoderAuton();
			preAutonHasRun = true;
		}
		
		else if (autonMode == autonMode.RECORDABLE && reader != null) {
			if (reader.isDone(System.currentTimeMillis() - initialTime)
                    && currentRecordingIndex < playbackURLs.length - 1) {
                reader.close();
                // This will choose the next re		`cording
                //reader = initializeReader(playbackURLs[++currentRecordingIndex]);
            }
 
            JSONObject current = reader.getDataAtTime(System.currentTimeMillis() - initialTime);
            drive.hDrive((Double) current.get("vertical"), (Double) current.get("horizontal"), (Double) current.get("rotation"), true, SpeedMode.NORMAL);
            intake.setSpeed(0.3);
            shooter.setSpeed((Double) current.get("shooter") > 0.6);
		}
		
		else {
			//reset encoders to 0
			drive.resetAll();
			*/
		
		
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
			
			if (Math.abs(turn) < 20) autoSpeed = 0.50;
			else autoSpeed = 0.4;
			
			//if (redLeft.getRangeInches() > autonModeUltraDist/* || redRight.getRangeInches() > autonModeUltraDist) {
				//FOR TESTING
				//SmartDashboard.putString("DB/String 0", "Turn: " + turn);
			/*if(centerY < 55.0) {
				SmartDashboard.putString("DB/String 5", "Final Descent");
				drive.hDrive(0.4, 0.0, 0.0 + 0.10, false, SpeedMode.FAST);
				Timer.delay(1);
				drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
				
				
			}
			*/
			/*
			if (redLeft.getRangeInches() < 30) {
				SmartDashboard.putString("DB/String 5", "Final Descent");
				drive.hDrive(0.25, 0.0, 0.0, false, SpeedMode.FAST);
				Timer.delay(2);
				drive.hDrive(0.0, 0.0, -0.3, false, SpeedMode.FAST);
				Timer.delay(0.5);
				drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
			}
		
			
				
			else drive.hDrive(autoSpeed, 0.0, turn * 0.005, false, SpeedMode.NORMAL);
				
			SmartDashboard.putString("DB/String 6", "ultra: " + redLeft.getRangeInches());
				
				
			//}
			//SmartDashboard.putString("DB/String 5", "Distance: " + redRight.getRangeInches());
			//else {
			
			/* || redRight.getRangeInches() < 4
			
				if (redLeft.getRangeInches() < stopDist && redLeft.getRangeInches() > 1) {
					//FOR TESTING
					SmartDashboard.putString("DB/String 0", "We are not moving");
					SmartDashboard.putString("DB/String 4", "null");
					
					drive.hDriveStraightConstant(0.0, 0.0, 0.0);
				}
				/*else if(centerY < 80){
					//FOR TESTING
					SmartDashboard.putString("DB/String 0", "WE ARE MOVING FORWARD CUZ \"Y\" NOT");
					SmartDashboard.putString("DB/String 4", "null");
					
					drive.hDriveStraightConstant(-vertSpeedSlow, 0.0, 0.0);
				}*/
				/*
				else if (centerX < (IMG_WIDTH/2) - strafeXValue) {
					SmartDashboard.putString("DB/String 0", "Strafe: moving left");
					//drive.hDrive(0.0, turn * 0.005, 0, true, SpeedMode.NORMAL);
				}
				
				else if (centerX > (IMG_WIDTH/2) + strafeXValue) {
					SmartDashboard.putString("DB/String 0", "Strafe: moving right");
					//drive.hDrive(0.0, turn * 0.005, 0, true, SpeedMode.NORMAL);
				}
				
				else {
					SmartDashboard.putString("DB/String 0", "moving forward");
					//drive.hDriveStraightEncoder(-0.4, 0.0);
				}
				 
				else {
					SmartDashboard.putString("DB/String 0", "Going forward, strafing");
					//SmartDashboard.putString("DB/String 4", "Turn * 0.005: " + turn * 0.005);
					drive.hDriveStraightConstant(vertSpeedSlow, turn * turnMultiplier, 0.0);//(redLeft.getRangeInches() - redRight.getRangeInches()) * ultraRotate);
				}
			//}*/
			
			//RobotBuilder will generate code automatically that 
			//runs the scheduler every driver station update period (about every 20ms). 
			//This will cause the selected autonomous command to run
			//Scheduler.getInstance().run();
	}
	
	public void teleopInit() {
		camera.setExposureManual(50);
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
		
		SmartDashboard.putString("DB/String 6", "Left Ultra: " + redLeft.getRangeInches());
		//SmartDashboard.putString("DB/String 6", "Right Ultra: " + redRight.getRangeInches());
		
		
		
		
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
		if (recorder != null) {
            // xbox and js are two input controllers. These methods just return
            // joystick values (in the form of doubles)
            Object[] input = new Object[] { xbox.getLS_Y(), xbox.getRS_X(), xbox.getLS_X(),
                    0.3, xbox.getTriggerRight()};
 
            // Do stuff to drive with the inputs.
            drive.hDrive((Double) input[0], (Double) input[2], (Double) input[3], true, SpeedMode.NORMAL);
            intake.setSpeed((Double) input[3]);
            shooter.setSpeed((Double) input[4] > 0.6);
 
            recorder.appendData(input);
        }
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
		
		//CANTalon = 0 or Talon = 1
		speedControllerType = SingularityDrive.CANTALON_DRIVE;
		
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

