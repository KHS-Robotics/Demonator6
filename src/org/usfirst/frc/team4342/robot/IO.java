package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.logging.Logger;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.Scaler;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Class that holds direct access to the <code>Subsystem</code> objects
 */
public class IO 
{
	private IO() {}
	
	private static boolean initialized;
	
	public static final double JOYSTICK_DEADZONE = 0.04;
	
	// Sensors, motor controllers and cameras
	private static Joystick switchBox, rightDriveStick, leftDriveStick;
	private static CANTalon fr, fl, mr, ml, rr, rl, intake, agitator, shooter, scaleMotor;
	private static AHRS navx;
	private static DigitalInput rsensor, lsensor, gearPlacerSwitch;
	private static AnalogInput ultrasonic;
	private static DoubleSolenoid placer, shifter;
	private static Solenoid shootFar, camLight;
	private static Encoder leftDrive, rightDrive, shooterEnc;
	
	// Subsystems
	private static TankDrive drive;
	private static Shooter shootingSubsystem;
	private static Scaler scaler;
	private static GearPlacer gearPlacer;
	
	/**
	 * Initializes components and subsystems.
	 */
	public static void initialize()
	{
		if(initialized)
			return;
		initialized = true;
		
		Logger.info("Initializing components...");
		
		// Joysticks
		leftDriveStick = new Joystick(RobotMap.LEFT_DRIVE_STICK_PORT);
		rightDriveStick = new Joystick(RobotMap.RIGHT_DRIVE_STICK_PORT);
		switchBox = new Joystick(RobotMap.SWITCH_BOX_PORT);
		
		// CANTalons
		fr = new CANTalon(RobotMap.FRONT_RIGHT);
		fl = new CANTalon(RobotMap.FRONT_LEFT);
		mr = new CANTalon(RobotMap.MIDDLE_RIGHT);
		ml = new CANTalon(RobotMap.MIDDLE_LEFT);
		rr = new CANTalon(RobotMap.REAR_RIGHT);
		rl = new CANTalon(RobotMap.REAR_LEFT);
		intake = new CANTalon(RobotMap.INTAKE);
		agitator = new CANTalon(RobotMap.AGITATOR);
		shooter = new CANTalon(RobotMap.SHOOTER);
		scaleMotor = new CANTalon(RobotMap.SCALER);
		
		fr.enableBrakeMode(true);
		fl.enableBrakeMode(true);
		mr.enableBrakeMode(true);
		ml.enableBrakeMode(true);
		rr.enableBrakeMode(true);
		rl.enableBrakeMode(true);
		intake.enableBrakeMode(false);
		agitator.enableBrakeMode(false);
		shooter.enableBrakeMode(false);
		scaleMotor.enableBrakeMode(true);
		
		// NavX
		navx = new AHRS(RobotMap.NAVX_PORT, RobotMap.NAVX_UPDATE_RATE_HZ);
		
		// DIOs
		rsensor = new DigitalInput(RobotMap.RIGHT_PHOTO_SENSOR);
		lsensor = new DigitalInput(RobotMap.LEFT_PHOTO_SENSOR);
		gearPlacerSwitch = new DigitalInput(RobotMap.GEAR_PLACER_SWITCH);
		ultrasonic = new AnalogInput(RobotMap.ULTRASONIC_ANALOG_IN);
		
		ultrasonic.setAutomaticMode(true);
		
		// Pneumatics
	    placer = new DoubleSolenoid(RobotMap.PLACER_FORWARD_CHANNEL, RobotMap.PLACER_REVERSE_CHANNEL);
	    shifter = new DoubleSolenoid(RobotMap.SHIFT_FORWARD_CHANNEL, RobotMap.SHIFT_REVERSE_CHANNEL);
	    shootFar = new Solenoid(RobotMap.SHOOT_FAR_SOLENOID);
	    
	    // Lights
	    camLight = new Solenoid(RobotMap.CAMERA_LIGHT_CHANNEL);

	    // Encoders
	    leftDrive = new Encoder(RobotMap.LEFT_DRIVE_ENC_CH_A, RobotMap.LEFT_DRIVE_ENC_CH_B);
	    rightDrive = new Encoder(RobotMap.RIGHT_DRIVE_ENC_CH_A, RobotMap.RIGHT_DRIVE_ENC_CH_B);
	    shooterEnc = new Encoder(RobotMap.SHOOTER_ENC_CH_A, RobotMap.SHOOTER_ENC_CH_B);
	    
	    leftDrive.setDistancePerPulse((10.0*Math.PI*20)/(128*3*64));
	    
	    rightDrive.setReverseDirection(true);
	    rightDrive.setDistancePerPulse((10.0*Math.PI*20)/(128*3*64));
	    
	    shooterEnc.setDistancePerPulse(Math.PI / 5.0);
	    
	    Logger.info("Initializing subsystems...");
	    
	    // Subsystems
	    drive = new TankDrive(fr, fl, mr, ml, rr, rl, navx, shifter, leftDrive, rightDrive, rsensor, lsensor, ultrasonic);
	    shootingSubsystem = new Shooter(intake, agitator, shooter, shooterEnc, shootFar, camLight);
	    scaler = new Scaler(scaleMotor);
	    gearPlacer = new GearPlacer(placer, gearPlacerSwitch);
	}
	
	/**
	 * Gets the drive subsystem.
	 * @return the drive subsystem
	 */
	public static TankDrive getDrive()
	{
		return drive;
	}
	
	/**
	 * Gets the shooter subsystem.
	 * @return the shooter subsystem
	 */
	public static Shooter getShooter()
	{
		return shootingSubsystem;
	}
	
	/**
	 * Gets the scaling subsystem.
	 * @return the scaling subsystem
	 */
	public static Scaler getScaler()
	{
		return scaler;
	}
	
	/**
	 * Gets the gear placing subsystem.
	 * @return the gear placing subsystem
	 */
	public static GearPlacer getGearPlacer()
	{
		return gearPlacer;
	}
	
	/**
	 * Gets the left joystick.
	 * @return the left joystick.
	 */
	public static Joystick getLeftDriveStick()
	{
		return leftDriveStick;
	}
	
	/**
	 * Gets the right joystick.
	 * @return the right joystick
	 */
	public static Joystick getRightDriveStick()
	{
		return rightDriveStick;
	}
	
	/**
	 * Gets the switch box subsystem.
	 * @return the switch box subsystem
	 */
	public static Joystick getSwitchBox()
	{
		return switchBox;
	}
	
	/**
	 * Gets if either of the drive sticks are outside of the specified dead zone
	 * @return true if the right or left drive sticks are outside the dead zone, false otherwise
	 */
	public static boolean shouldCancelAutoCommand()
	{
		boolean left = Math.abs(getLeftDriveStick().getY()) > (2*JOYSTICK_DEADZONE);
		boolean right = Math.abs(getRightDriveStick().getY()) > (2*JOYSTICK_DEADZONE);
		
		return left || right;
	}
}
