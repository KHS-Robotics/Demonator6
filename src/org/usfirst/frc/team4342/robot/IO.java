package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.AlignPeg;
import org.usfirst.frc.team4342.robot.commands.LowerGear;
import org.usfirst.frc.team4342.robot.commands.RaiseGear;
import org.usfirst.frc.team4342.robot.commands.StartAccumulator;
import org.usfirst.frc.team4342.robot.commands.StartScaler;
import org.usfirst.frc.team4342.robot.commands.StartShooter;
import org.usfirst.frc.team4342.robot.commands.StopAccumulator;
import org.usfirst.frc.team4342.robot.commands.StopDriveTrain;
import org.usfirst.frc.team4342.robot.commands.StopScaler;
import org.usfirst.frc.team4342.robot.commands.StopShooter;
import org.usfirst.frc.team4342.robot.logging.Logger;
import org.usfirst.frc.team4342.robot.subsystems.Accumulator;
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
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Class that holds direct access to the <code>Subsystem</code> objects
 */
public class IO 
{
	private static IO instance;
	
	public static IO getInstance()
	{
		if(instance == null)
			instance = new IO();
		
		return instance;
	}
	
	// Subsystems
	public final GearPlacer GearPlacer;
	public final Scaler Scaler;
	public final Accumulator Accumulator;
	public final Shooter Shooter;
	public final TankDrive Drive;
	
	// Sensors, motor controllers and pneumatics
	public final Joystick SwitchBox, RightDriveStick, LeftDriveStick;
	public final CANTalon FrontRight, FrontLeft, MiddleRight, MiddleLeft, RearRight, RearLeft, Intake, Agitator, ShooterMotor, ScaleMotor;
	public final AHRS NavX;
	public final DigitalInput RightPhotosensor, LeftPhotosensor;
	public final Ultrasonic Ultrasonic;
	public final DoubleSolenoid Placer, Shifter;
	public final Solenoid ShooterHood, CamLight;
	public final Encoder LeftDrive, RightDrive, ShooterEnc;
	
	private IO() 
	{
		Logger.info("Constructing IO...");
		
		// Joysticks
		LeftDriveStick = new Joystick(RobotMap.LEFT_DRIVE_STICK_PORT);
		RightDriveStick = new Joystick(RobotMap.RIGHT_DRIVE_STICK_PORT);
		SwitchBox = new Joystick(RobotMap.SWITCH_BOX_PORT);
		
		// CANTalons
		FrontRight = new CANTalon(RobotMap.FRONT_RIGHT);
		FrontLeft = new CANTalon(RobotMap.FRONT_LEFT);
		MiddleRight = new CANTalon(RobotMap.MIDDLE_RIGHT);
		MiddleLeft = new CANTalon(RobotMap.MIDDLE_LEFT);
		RearRight = new CANTalon(RobotMap.REAR_RIGHT);
		RearLeft = new CANTalon(RobotMap.REAR_LEFT);
		Intake = new CANTalon(RobotMap.INTAKE);
		Agitator = new CANTalon(RobotMap.AGITATOR);
		ShooterMotor = new CANTalon(RobotMap.SHOOTER);
		ScaleMotor = new CANTalon(RobotMap.SCALER);
		
		FrontRight.enableBrakeMode(true);
		FrontLeft.enableBrakeMode(true);
		MiddleRight.enableBrakeMode(true);
		MiddleLeft.enableBrakeMode(true);
		RearRight.enableBrakeMode(true);
		RearLeft.enableBrakeMode(true);
		Intake.enableBrakeMode(false);
		Agitator.enableBrakeMode(false);
		ShooterMotor.enableBrakeMode(false);
		ScaleMotor.enableBrakeMode(true);
		
		// NavX
		NavX = new AHRS(RobotMap.NAVX_PORT, RobotMap.NAVX_UPDATE_RATE_HZ);
		
		// DIOs
		RightPhotosensor = new DigitalInput(RobotMap.RIGHT_PHOTO_SENSOR);
		LeftPhotosensor = new DigitalInput(RobotMap.LEFT_PHOTO_SENSOR);
		Ultrasonic = new Ultrasonic(RobotMap.ULTRASONIC_DIGITAL_OUT, RobotMap.ULTRASONIC_DIGITAL_IN);
		
		Ultrasonic.setAutomaticMode(true);
		
		// Pneumatics
	    Placer = new DoubleSolenoid(RobotMap.PLACER_FORWARD_CHANNEL, RobotMap.PLACER_REVERSE_CHANNEL);
	    Shifter = new DoubleSolenoid(RobotMap.SHIFT_FORWARD_CHANNEL, RobotMap.SHIFT_REVERSE_CHANNEL);
	    ShooterHood = new Solenoid(RobotMap.SHOOT_FAR_SOLENOID);
	    
	    // Lights
	    CamLight = new Solenoid(RobotMap.CAMERA_LIGHT_CHANNEL);

	    // Encoders
	    LeftDrive = new Encoder(RobotMap.LEFT_DRIVE_ENC_CH_A, RobotMap.LEFT_DRIVE_ENC_CH_B);
	    RightDrive = new Encoder(RobotMap.RIGHT_DRIVE_ENC_CH_A, RobotMap.RIGHT_DRIVE_ENC_CH_B);
	    ShooterEnc = new Encoder(RobotMap.SHOOTER_ENC_CH_A, RobotMap.SHOOTER_ENC_CH_B);
	    
	    LeftDrive.setDistancePerPulse((6.0*Math.PI*20)/(64*3*64));
	    
	    RightDrive.setReverseDirection(true);
	    RightDrive.setDistancePerPulse((6.0*Math.PI*20)/(64*3*64));
	    
	    ShooterEnc.setDistancePerPulse(Math.PI / 5.0);
	    
	    GearPlacer = new GearPlacer(Placer);
		Scaler = new Scaler(ScaleMotor);
		Accumulator = new Accumulator(Intake);
		Shooter = new Shooter(Agitator, ShooterMotor, ShooterEnc, ShooterHood, CamLight);
		Drive = new TankDrive(FrontRight, FrontLeft, MiddleRight, MiddleLeft, RearRight, RearLeft, NavX, Shifter, LeftDrive, RightDrive, RightPhotosensor, LeftPhotosensor, Ultrasonic);
		
		JoystickButton placerButton = new JoystickButton(SwitchBox, ButtonMap.SwitchBox.GearPlacer.LOWER);
		placerButton.whenActive(new LowerGear(GearPlacer));
		placerButton.whenInactive(new RaiseGear(GearPlacer));
		
		JoystickButton scaleButton = new JoystickButton(SwitchBox, ButtonMap.SwitchBox.Scaler.SCALE);
		scaleButton.whenActive(new StartScaler(Scaler));
		scaleButton.whenInactive(new StopScaler(Scaler));
		
		JoystickButton accumulateButton = new JoystickButton(SwitchBox, ButtonMap.SwitchBox.Shooter.ACCUMULATE);
		accumulateButton.whenActive(new StartAccumulator(Accumulator));
		accumulateButton.whenInactive(new StopAccumulator(Accumulator));
		
		JoystickButton shootFarButton = new JoystickButton(SwitchBox, ButtonMap.SwitchBox.Shooter.SHOOT_FAR);
		shootFarButton.whenActive(new StartShooter(Shooter, true));
		shootFarButton.whenInactive(new StopShooter(Shooter));
		
		JoystickButton shootCloseButton = new JoystickButton(SwitchBox, ButtonMap.SwitchBox.Shooter.SHOOT_CLOSE);
		shootCloseButton.whenActive(new StartShooter(Shooter, false));
		shootCloseButton.whenInactive(new StopShooter(Shooter));
		
		JoystickButton alignPegButton = new JoystickButton(LeftDriveStick, ButtonMap.DriveStick.Left.ALIGN_HOOK_LEFT);
		alignPegButton.whenActive(new AlignPeg(Drive));
		alignPegButton.whenReleased(new StopDriveTrain(Drive));
	}
}
