package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.teleop.Scale;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.Scaler;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class IO 
{
	private IO() {}
	
	private static boolean initialized;
	
	// Sensors and motor controllers
	private static Joystick driveStick, switchBox;
	private static CANTalon fr, fl, mr, ml, rr, rl, intake, agitator, shooter, scaleMotor;
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
		
		// NavX
		navx = new AHRS(RobotMap.NAVX_PORT, RobotMap.NAVX_UPDATE_RATE_HZ);
		
		// DIOs
		scaleSwitch = new DigitalInput(RobotMap.SCALE_SWITCH);
		rsensor = new DigitalInput(RobotMap.RIGHT_PHOTO_SENSOR);
		lsensor = new DigitalInput(RobotMap.LEFT_PHOTO_SENSOR);
		
		// Pneumatics
	    placer = new DoubleSolenoid(RobotMap.PLACER_FORWARD_CHANNEL, RobotMap.PLACER_REVERSE_CHANNEL);
	    
	    // Subsystems
	    drive = new TankDrive(fr, fl, mr, ml, rr, rl, navx, rsensor, lsensor);
	    shootingSubsystem = new Shooter(intake, agitator, shooter);
	    scaler = new Scaler(scaleMotor, scaleSwitch);
	    gearPlacer = new GearPlacer(placer);
	    
	    // Scale when the driver presses the Scale button. This will disable DriveWithJoystick
	    // until the Scale's isFinished() returns true or the Scale command times out (15 seconds).
	    // Once finished, DriveWithJoystick will start again
	    new JoystickButton(driveStick, ButtonMap.Drive.SCALE).toggleWhenPressed(new Scale(scaler, drive));
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
