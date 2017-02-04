package org.usfirst.frc.team4342.robot;

import edu.wpi.first.wpilibj.SPI;

public class RobotMap 
{
	private RobotMap() {}
	
	// Joysticks
	public static final int DRIVE_STICK_PORT = 0;
	public static final int SWITCH_BOX_PORT = 1;
	
	// Drive
	public static final int FRONT_RIGHT = 0;
	public static final int FRONT_LEFT = 1;
	public static final int MIDDLE_RIGHT = 2;
	public static final int MIDDLE_LEFT = 3;
	public static final int REAR_RIGHT = 4;
	public static final int REAR_LEFT = 5;
	
	// Encoders
	public static final int LEFT_DRIVE_ENC_CH_A = 0;
	public static final int LEFT_DRIVE_ENC_CH_B = 1;
	public static final int RIGHT_DRIVE_ENC_CH_A = 2;
	public static final int RIGHT_DRIVE_ENC_CH_B = 3;
	public static final int SHOOTER_ENC_CH_A = 4;
	public static final int SHOOTER_ENC_CH_B = 5;
	
	// Shooter
	public static final int INTAKE = 6;
	public static final int AGITATOR = 7;
	public static final int SHOOTER = 8;
	
	// Scaler
	public static final int SCALER = 9;
	
	// NavX
	public static final SPI.Port NAVX_PORT = SPI.Port.kMXP;
	public static final byte NAVX_UPDATE_RATE_HZ = (byte) 50;
	
	// DIOs
	public static final int SCALE_SWITCH = 0;
	public static final int RIGHT_PHOTO_SENSOR = 1;
	public static final int LEFT_PHOTO_SENSOR = 2;
	
	// Pneumatics
	public static final int PLACER_FORWARD_CHANNEL = 1;
	public static final int PLACER_REVERSE_CHANNEL = 2;
	public static final int SHIFT_FORWARD_CHANNEL = 3;
	public static final int SHIFT_REVERSE_CHANNEL = 4;
	
	
}
