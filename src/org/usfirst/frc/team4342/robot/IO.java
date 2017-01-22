package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.Scaler;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;

public class IO 
{
	private IO() {}
	
	private static boolean initialized;
	
	// Sensors and motor controllers
	private static Joystick driveStick, switchBox;
	private static Talon fr, fl, rr, rl;
	private static Spark intake, agitator, shooter, scaleMotor;
	private static AHRS navx;
	private static DigitalInput rsensor, lsensor, scaleSwitch;
	private static DoubleSolenoid placer;
	
	// Subsystems
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
		
		// Talons
		fr = new Talon(RobotMap.FRONT_RIGHT);
		fl = new Talon(RobotMap.FRONT_LEFT);
		rr = new Talon(RobotMap.REAR_RIGHT);
		rl = new Talon(RobotMap.REAR_LEFT);
		
		//intake = new Spark(RobotMap.INTAKE);
		//agitator = new Spark(RobotMap.AGITATOR);
		shooter = new Spark(RobotMap.SHOOTER);
		//scaleMotor = new Spark(RobotMap.SCALER);
		
		// NavX
		navx = new AHRS(RobotMap.NAVX_PORT, RobotMap.NAVX_UPDATE_RATE_HZ);
		
		// DIOs
		//scaleSwitch = new DigitalInput(RobotMap.SCALE_SWITCH);
		rsensor = new DigitalInput(RobotMap.RIGHT_PHOTO_SENSOR);
		lsensor = new DigitalInput(RobotMap.LEFT_PHOTO_SENSOR);
		
		// Pneumatics
	    placer = new DoubleSolenoid(RobotMap.PLACER_FORWARD_CHANNEL, RobotMap.PLACER_REVERSE_CHANNEL);
	    
	    // Subsystems
	    drive = new TankDrive(fr, fl, rr, rl, navx, rsensor, lsensor);
	    shootingSubsystem = new Shooter(intake, agitator, shooter);
	    //scaler = new Scaler(scaleMotor, scaleSwitch);
	    //gearPlacer = new GearPlacer(placer);
	    
	    // Scale when the driver presses the Scale button. This will disable DriveWithJoystick
	    // until the Scale's isFinished() returns true or the Scale command times out (15 seconds).
	    // Once finished, DriveWithJoystick will start again
	    //new JoystickButton(driveStick, ButtonMap.Drive.SCALE).toggleWhenPressed(new Scale(scaler, drive));
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
}
