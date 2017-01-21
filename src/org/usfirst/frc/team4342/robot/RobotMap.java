package org.usfirst.frc.team4342.robot;

import edu.wpi.first.wpilibj.SPI;

public class RobotMap 
{
	private RobotMap() {}
	
	// Joysticks
	public static final int DRIVE_STICK_PORT = 0;
	public static final int SWITCH_BOX_PORT = 1;
	
	// Drive
	public static final int FRONT_LEFT = 0;
	public static final int REAR_LEFT = 1;
	public static final int FRONT_RIGHT = 2;
	public static final int REAR_RIGHT = 3;
	
	// Shooter
	public static final int INTAKE = 6;
	public static final int AGITATOR = 5;
	public static final int SHOOTER = 4;
	
	// Scaler
	public static final int SCALER = 7;
	
	// NavX
	public static final SPI.Port NAVX_PORT = SPI.Port.kMXP;
	public static final byte NAVX_UPDATE_RATE_HZ = (byte) 50;
	
	// DIOs
	public static final int SCALE_SWITCH = 2;
	public static final int RIGHT_PHOTO_SENSOR = 0;
	public static final int LEFT_PHOTO_SENSOR = 1;
	
	// Pneumatics
	public static final int PLACER_FORWARD_CHANNEL = 0;
	public static final int PLACER_REVERSE_CHANNEL = 1;
}
