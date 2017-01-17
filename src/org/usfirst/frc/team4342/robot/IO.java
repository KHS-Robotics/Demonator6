package org.usfirst.frc.team4342.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI.Port;

public class IO 
{
	private static Joystick driveStick = new Joystick(0);
	private static AHRS navx = new AHRS(Port.kMXP);
	private static CANTalon fr = new CANTalon(0), fl = new CANTalon(1),
				    		mr = new CANTalon(2), ml = new CANTalon(3),
				    		rr = new CANTalon(4), rl = new CANTalon(5);
	
	public static double getDriveX()
	{
		return driveStick.getX();
	}
	
	public static double getDriveY()
	{
		return driveStick.getY();
	}
	
	public static boolean getDriveButton(int number)
	{
		return driveStick.getRawButton(number);
	}

	public static double getYaw()
	{
		return navx.getYaw();
	}

	public static double getAngle()
	{
		return navx.getAngle();
	}

	public static double getPitch()
	{
		return navx.getPitch();
	}

	public static AHRS getNavx()
	{
		return navx;
	}
	
	public static void setDrive(double x, double y)
	{
		double right = y - x;
		double left = y + x;
		
		if (right > 1)
			right = 1;
		else if (right < -1)
			right = -1;
		
		if (left > 1)
			left = 1;
		else if (left < -1)
			left = -1;
		
		fr.set(right);
		fl.set(left);
		mr.set(right);
		ml.set(left);
		rr.set(right);
		rl.set(left);
	}
	
}
