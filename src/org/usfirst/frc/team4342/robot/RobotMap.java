package org.usfirst.frc.team4342.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * Class that holds the port configuration for the motor
 * controllers and sensors
 */
public class RobotMap 
{
	private RobotMap() {}
	
	// Cameras
	public static final int FRONT_USB_CAMERA = 0;
	
	// Joysticks
	public static final int LEFT_DRIVE_STICK_PORT = 0;
	public static final int RIGHT_DRIVE_STICK_PORT = 1;
	public static final int SWITCH_BOX_PORT = 2;
	
	// Drive
	public static final int FRONT_RIGHT = 0;
	public static final int MIDDLE_RIGHT = 1;
	public static final int REAR_RIGHT = 2;
	public static final int FRONT_LEFT = 15;
	public static final int MIDDLE_LEFT = 14;
	public static final int REAR_LEFT = 13;

	
	// Encoders
	public static final int LEFT_DRIVE_ENC_CH_A = 2;
	public static final int LEFT_DRIVE_ENC_CH_B = 3;
	public static final int RIGHT_DRIVE_ENC_CH_A = 0;
	public static final int RIGHT_DRIVE_ENC_CH_B = 1;
	public static final int SHOOTER_ENC_CH_A = 4;
	public static final int SHOOTER_ENC_CH_B = 5;
	
	// Shooter
	public static final int INTAKE = 12;
	public static final int AGITATOR = 4;
	public static final int SHOOTER = 3;
	
	// Scaler
	public static final int SCALER = 11;
	
	// NavX
	public static final SPI.Port NAVX_PORT = SPI.Port.kMXP;
	public static final byte NAVX_UPDATE_RATE_HZ = (byte) 50;
	
	// DIOs
	public static final int RIGHT_PHOTO_SENSOR = 6;
	public static final int LEFT_PHOTO_SENSOR = 7;
	public static final int LINE_FOLLOWER = 8;
	public static final int GEAR_PLACER_SWITCH = 9;
	public static final int ULTRASONIC_DIGITAL_IN = 23;
	public static final int ULTRASONIC_DIGITAL_OUT = 22;
	
	// Pneumatics
	public static final int PLACER_FORWARD_CHANNEL = 2;
	public static final int PLACER_REVERSE_CHANNEL = 3;
	public static final int SHIFT_FORWARD_CHANNEL = 0;
	public static final int SHIFT_REVERSE_CHANNEL = 1;
	public static final int SHOOT_FAR_SOLENOID = 4;
	
	// Lights
	public static final int CAM_LIGHT_CHANNEL = 6;

}
