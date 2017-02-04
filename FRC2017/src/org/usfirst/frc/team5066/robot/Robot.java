package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SingularityProperties;
import org.usfirst.team5066.controller2017.controlSchemes.BasicDrive;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/*
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	*/
	//Holds the current control scheme
	ControlScheme currentScheme;

	//Holds the integer port id's for for the motors.The values are assigned when properties are loaded.
	//drive:
	int leftRearMotor, leftFrontMotor, rightFrontMotor, rightRearMotor, rightMiddleMotor, leftMiddleMotor; //TEST
	//climber:
	int climbMotor;
	//intake:
	int frontMotor, lowMotor, highMotor;
	//low goal:
	int shootMotor;
	
	/*
	 *high goal:
	 *int highMotor;
	 */
	
	Joystick js;
	SingularityDrive drive;
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
		/*
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		*/
		
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
		/*
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
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

