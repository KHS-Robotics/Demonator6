package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.subsystems.drive.DriveTrain;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SPI.Port;

public class IO 
{
	private static boolean initialized;
	
	private static Joystick driveStick;
	private static CANTalon fr, fl, rr, rl;
	private static DriveTrain driveTrain;
	private static AHRS navx;
	private static DigitalInput rsensor, lsensor; 
	private static PIDController yawPID;
	
	private static CANTalon scaleMotor;
	private static DigitalInput scaleSwitch;
	
	private static Joystick switchBox;
	private static CANTalon intake, agitator, shooter;
	
	private static DoubleSolenoid placer;
	
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
		
		//Scaling mechanism
		scaleMotor = new CANTalon(4);
		scaleSwitch = new DigitalInput(0);
		
		//Shooter
		switchBox = new Joystick(1);
		intake = new CANTalon(5);
		agitator = new CANTalon(6);
		shooter = new CANTalon(7);
		
		//Gear Placer
	    placer = new DoubleSolenoid(1, 2);
		
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
			if(yawPID.isEnabled())
				return;
			
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
	
	public static class Scaler
	{
		private static double output;
		
		public static void enable()
		{
			if(Math.abs(0.5 - output) < 0.05)
				return;
			
			scaleMotor.set(0.5);
		}
		
		public static void disable()
		{
			if(Math.abs(0 - output) < 0.05)
				return;
			
			scaleMotor.set(0);
		}
		
		public static boolean hasScaled()
		{	
			return scaleSwitch.get();
		}
	}
	
	public static class Shooter
	{
		public static final int ACCUMULATE_BUTTON = 1, AGITATE_BUTTON = 2, SHOOT_BUTTON = 3;
		private static double currentOutputIntake, currentOutputAgitator, currentOutputShooter;
		
		public static boolean getRawButton(int number)
		{		
			return switchBox.getRawButton(number);
		}
		
		public static void accumulate()
		{
			if(Math.abs(0.67 - currentOutputIntake) < 0.05)
				return;
				
			intake.set(0.67);
		}
		
		public static void stopAccumulating()
		{
			if(Math.abs(0 - currentOutputIntake) < 0.05)
				return;
			
			intake.set(0);
		}
		
		public static void agitate()
		{
			if(Math.abs(0.8 - currentOutputAgitator) < 0.05)
				return;
			
			agitator.set(0.8);
		}
		
		public static void stopAgitating()
		{
			if(Math.abs(0 - currentOutputAgitator) < 0.05)
				return;
			
			agitator.set(0);
		}
		
		public static void shoot()
		{
			if(Math.abs(0.85 - currentOutputShooter) < 0.05)
				return;
			
			shooter.set(0.85);
		}
		
		public static void stopShooting()
		{
			if(Math.abs(0 - currentOutputShooter) < 0.05)
				return;
			
			shooter.set(0);
		}
		
	}
	
	public static class GearPlacer
	{
		public static final int LOWER_BUTTON = 4;
		private static Value currentValue;
		
		public static void lower()
		{
			if(currentValue == Value.kForward)
				return;
				
			placer.set(Value.kForward);
		}
		
		public static void raise()
		{
			if(currentValue == Value.kReverse)
				return;
			
			placer.set(Value.kReverse);
		}
		
		public static boolean getRawButton(int button)
		{
			return switchBox.getRawButton(button);
		}
	}
	
}
