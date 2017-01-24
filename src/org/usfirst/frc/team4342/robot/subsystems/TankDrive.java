package org.usfirst.frc.team4342.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TankDrive extends Subsystem implements PIDOutput
{
	private static final double P = 0.0, I = 0.0, D = 0.0;
	
	private Talon fr, fl, rr, rl;
	private AHRS navx;
	private DigitalInput rsensor, lsensor;
	private PIDController yawPID;
	
	private double direction;
	
	public TankDrive(Talon fr, Talon fl, Talon rr, Talon rl, AHRS navx, DigitalInput rsensor, DigitalInput lsensor)
	{
		super();
		
		this.fr = fr;
		this.fl = fl;
		this.rr = rr;
		this.rl = rl;
		this.navx = navx;
		this.rsensor = rsensor;
		this.lsensor = lsensor;
		
		yawPID = new PIDController(P, I, D, navx, this);
		yawPID.setInputRange(-180.0, 180.0);
		yawPID.setOutputRange(-1.0, 1.0);
		yawPID.setContinuous();
	}
	
	public void set(double x, double y)
	{
		double right = y + x;
		double left = y - x;
		
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
	
	public void setLeft(double direction)
	{
		fl.set(direction);
		rl.set(direction);
	}
	
	public void setRight(double direction)
	{
		fr.set(direction);
		rr.set(direction);
	}
	
	public void goToSetpoint(double setpointAngle)
	{
		this.yawPID.setSetpoint(setpointAngle);
		enablePID();
	}
	
	public void goStraight(double direction)
	{
		setDirection(direction);
	}
	
	public void goStraight(double direction, double angle)
	{
		goToSetpoint(angle);
		goStraight(direction);
	}
	
	public void goToAngle(double angle)
	{
		goToSetpoint(angle);
		setDirection(0.0);
	}
	
	public double getYaw()
	{
		return navx.getYaw();
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
