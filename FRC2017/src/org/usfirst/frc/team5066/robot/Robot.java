package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.controlSchemes.ArcadeHDrive;
import org.usfirst.frc.team5066.controller2017.controlSchemes.BasicDrive;
import org.usfirst.frc.team5066.controller2017.controlSchemes.TankHDrive;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SingularityProperties;
import org.usfirst.frc.team5066.library.SingularityPropertyNotFoundException;

import edu.wpi.first.wpilibj.DriverStation;
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
	int frontMotor;
	//low goal:
	int shootMotor, feedMotor;
	
	//CANTalon, Talon
	int speedControllerType;
	
	//Speed constants
	double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;
	
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
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#robotInit()
	 */
	@Override
	public void robotInit() {
		/*
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		*/
		
		try{
			properties = new SingularityProperties("/home/lvuser/robot.properties");
		}catch (Exception e){
			properties = new SingularityProperties();
			DriverStation.reportError("error in properties", true); 
		} finally {
			
			setDefaultProperties();
			
			
			loadProperties();
			
			
			drive = new SingularityDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor, leftMiddleMotor, rightMiddleMotor, speedControllerType, .4, .8, 1.0);
			shooter = new LowGoalShooter(shootMotor);
			climber = new SingularityClimber(climbMotor);
			intake = new SingularityIntake(frontMotor);
			currentScheme = new BasicDrive(XBOX_PORT, BIG_JOYSTICK_PORT);
		}
		
		
		
		/*
		 * js = new Joystick(XBOX_PORT);
		 * 
		 * 
		 */ 
		  intake = new SingularityIntake(frontMotor);
		  shooter = new LowGoalShooter(shootMotor);
		  climber = new SingularityClimber(climbMotor);
		
		
		
		
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
		//currentScheme.controlShooter(shooter);
		currentScheme.controlClimber(climber);
		//currentScheme.controlIntake(intake);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
	
	private void loadProperties() {
		try{
			//Ports
			leftRearMotor = properties.getInt("leftRearMotor");
			leftFrontMotor = properties.getInt("leftFrontMotor");
			rightFrontMotor = properties.getInt("rightFrontMotor");
			rightRearMotor = properties.getInt("rightRearMotor");
			
			//CANTalon or Talon drive?
			speedControllerType = properties.getInt("driveControllerType");
			
			climbMotor = properties.getInt("climbMotor");
			
			frontMotor = properties.getInt("frontMotor");
			shootMotor = properties.getInt("shootmotor");
			
			slowSpeedConstant = properties.getInt("slowSpeedConstant");
			normalSpeedConstant = properties.getInt("normalSpeedConstant");
			fastSpeedConstant = properties.getInt("fastSpeedConstant");
		
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
		properties.addDefaultProp("climbMotor", 9);
		
		//intake:
		properties.addDefaultProp("frontMotor", 5);
		
		//low goal:
		properties.addDefaultProp("shootMotor", 8);
		
		//CANTalon = 0 or Talon = 1
		speedControllerType = SingularityDrive.CANTALON_DRIVE;
		
		//Speed mode
		properties.addDefaultProp("slowSpeedConstant", 0.4);
		properties.addDefaultProp("normalSpeedConstant", 0.8);
		properties.addDefaultProp("fastSpeedConstant", 1.0);
		
		
		/*
		 *high goal:
		 *highMotor = INSERTPORT;
		 */

		
	}
}

