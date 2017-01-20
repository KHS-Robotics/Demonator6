package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.Scaler;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class IO 
{
	public static final int ACCUMULATE_BUTTON = 5, AGITATE_BUTTON = 6, SHOOT_BUTTON = 7; 
	public static final int LOWER_BUTTON = 8;
	
	private static boolean initialized;
	
	private static Joystick driveStick, switchBox;
	private static CANTalon fr, fl, rr, rl, intake, agitator, shooter, scaleMotor;
	private static AHRS navx;
	private static DigitalInput rsensor, lsensor, scaleSwitch;
	private static DoubleSolenoid placer;
	
	private static TankDrive drive;
	private static Shooter shootingSubsystem;
	private static Scaler scaler;
	private static GearPlacer gearPlacer;
	
	public static void initialize()
	{
		if(initialized)
			return;
		initialized = true;
		
		// Joysticks
		driveStick = new Joystick(RobotMap.DRIVE_STICK_PORT);
		switchBox = new Joystick(RobotMap.SWITCH_BOX_PORT);
		
		// CANTalons
		fr = new CANTalon(RobotMap.FRONT_RIGHT);
		fl = new CANTalon(RobotMap.FRONT_LEFT);
		rr = new CANTalon(RobotMap.REAR_RIGHT);
		rl = new CANTalon(RobotMap.REAR_LEFT);
		intake = new CANTalon(RobotMap.INTAKE);
		agitator = new CANTalon(RobotMap.AGITATOR);
		shooter = new CANTalon(RobotMap.SHOOTER);
		scaleMotor = new CANTalon(RobotMap.SCALER);
		
		// NavX
		navx = new AHRS(RobotMap.NAVX_PORT, RobotMap.NAVX_UPDATE_RATE_HZ);
		
		// DIOs
		scaleSwitch = new DigitalInput(RobotMap.SCALE_SWITCH);
		rsensor = new DigitalInput(RobotMap.RIGHT_PHOTO_SENSOR);
		lsensor = new DigitalInput(RobotMap.LEFT_PHOTO_SENSOR);
		
		// Pneumatics
	    placer = new DoubleSolenoid(RobotMap.PLACER_FORWARD_CHANNEL, RobotMap.PLACER_REVERSE_CHANNEL);
	    
	    // Subsystems
	    drive = new TankDrive(fr, fl, rr, rl, navx);
	    shootingSubsystem = new Shooter(intake, agitator, shooter);
	    scaler = new Scaler(scaleMotor, scaleSwitch);
	    gearPlacer = new GearPlacer(placer);
	}
	
	public static TankDrive getDrive()
	{
		return drive;
	}
	
	public static Shooter getShooter()
	{
		return shootingSubsystem;
	}
	
	public static Scaler getScaler()
	{
		return scaler;
	}
	
	public static GearPlacer getGearPlacer()
	{
		return gearPlacer;
	}
	
	public static Joystick getDriveSitck()
	{
		return driveStick;
	}
	
	public static Joystick getSwitchBox()
	{
		return switchBox;
	}
	
	public static class Sensors
	{
		public static boolean getRightSensor()
		{
			return rsensor.get();
		}
		
		public static boolean getLeftSensor()
		{
			return lsensor.get();
		}
	}
}
