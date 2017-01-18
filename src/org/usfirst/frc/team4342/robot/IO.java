package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.subsystems.drive.DriveTrain;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI.Port;

public class IO 
{
	private static boolean initialized;
	
	private static Joystick driveStick;
	private static CANTalon fr, fl, rr, rl;
	private static DriveTrain driveTrain;
	private static AHRS navx;
	public static DigitalInput rsensor, lsensor; 
	private static PIDController yawPID;
	
	public static void initialize()
	{
		if(initialized)
			return;
		
		// Drive
		driveStick = new Joystick(0);
		fr = new CANTalon(0);
		fl = new CANTalon(1);
		rr = new CANTalon(2);
		rl = new CANTalon(3);
		driveTrain = new DriveTrain();
		navx = new AHRS(Port.kMXP);
		rsensor = new DigitalInput(8);
		lsensor = new DigitalInput(9);
		yawPID = new PIDController(Drive.P, Drive.I, Drive.D, navx, driveTrain);
		yawPID.setInputRange(-180.0, 180.0);
		yawPID.setOutputRange(-1.0, 1.0);
		yawPID.setContinuous();
	}
	
	public static class Drive
	{
		private static final double P = 0.0, I = 0.0, D = 0.0;
		
		public static double getX()
		{
			return driveStick.getX();
		}
		
		public static double getY()
		{
			return driveStick.getY();
		}
		
		public static boolean getRawButton(int number)
		{
			return driveStick.getRawButton(number);
		}
		
		public static DriveTrain getDriveTrain()
		{
			return driveTrain;
		}

		public static double getYaw()
		{
			return navx.getYaw();
		}
		
		public static void setDirection(double direction)
		{
			driveTrain.setDirection(direction);
		}
		
		public static void setSetpoint(double yaw)
		{
			yawPID.setSetpoint(yaw);
		}
		
		public static void enablePID()
		{
			yawPID.enable();
		}
		
		public static void disablePID()
		{
			if(yawPID.isEnabled())
				yawPID.disable();
		}
		
		public static void set(double x, double y)
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
			rr.set(right);
			rl.set(left);
		}
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
