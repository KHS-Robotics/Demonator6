package org.usfirst.frc.team4342.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TankDrive extends Subsystem implements PIDOutput
{
	private static final double P = 0.015, I = 0.0, D = 0.019;
	
	private Talon fr, fl, rr, rl;
	private AHRS navx;
	private DigitalInput rsensor, lsensor;
	private PIDController yawPID;
	private Ultrasonic ultra;
	
	private double direction;
	
	public TankDrive(Talon fr, Talon fl, Talon rr, Talon rl, AHRS navx, DigitalInput rsensor, DigitalInput lsensor, 
			Ultrasonic ultra)
	{
		super();
		
		this.fr = fr;
		this.fl = fl;
		this.rr = rr;
		this.rl = rl;
		this.navx = navx;
		this.rsensor = rsensor;
		this.lsensor = lsensor;
		this.ultra = ultra;
		
//		yawPID = new PIDController(-P, -I, -D, navx, this);
//		yawPID.setInputRange(-180.0, 180.0);
//		yawPID.setOutputRange(-1.0, 1.0);
//		yawPID.setContinuous();
		
	}
	
	public void setPID(double p, double i, double d)
	{
		yawPID.setPID(p, i, d);
	}
	
	public void set(double left, double right)
	{
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
	
	public void setLeft(double output)
	{
		fl.set(output);
		rl.set(output);
	}
	
	public void setRight(double output)
	{
		fr.set(output);
		rr.set(output);
	}
	
	public double getYaw()
	{
		return navx.getYaw();
	}
	
	// USED FOR TESTING ONLY!! We should NOT reset this in matches
	public void resetNavx()
	{
		navx.reset();
	}
	
	public void goStraight(double direction, double yaw)
	{
		setHeading(yaw);
		setDirection(direction);
	}
	
	public void setHeading(double yaw)
	{
		yawPID.setSetpoint(yaw);
	}
	
	public void enablePID()
	{
		yawPID.enable();
	}
	
	public void disablePID()
	{
		if(yawPID.isEnabled())
			yawPID.disable();
	}
	
	public boolean pidEnabled()
	{
		return yawPID.isEnabled();
	}

	public void setDirection(double direction)
	{
		this.direction = direction;
	}
	
	public boolean getRightSensor()
	{
		return rsensor.get();
	}
	
	public boolean getLeftSensor()
	{
		return lsensor.get();
	}
	
	public double getUltrasonic()
	{
		return ultra.getRangeInches();
	}
	
	@Override
	public void pidWrite(double output)
	{
		this.set(output, direction);
	}
	
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(null);
		//this.setDefaultCommand(new DriveWithJoystick(IO.getDriveSitck(), this));
	}
}
