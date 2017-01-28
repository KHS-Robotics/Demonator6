package org.usfirst.frc.team4342.robot.subsystems;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TankDrive extends Subsystem implements PIDOutput
{
	private static final double P = 0.015, I = 0.0, D = 0.019;
	
	private CANTalon fr, fl, rr, rl, mr , ml;
	private AHRS navx;
	private Encoder left, right;
	private DigitalInput rsensor, lsensor;
	private PIDController yawPID;
	
	private double direction;
	
	public TankDrive(CANTalon fr, CANTalon fl, CANTalon mr, CANTalon ml, CANTalon rr, CANTalon rl, AHRS navx, Encoder left, Encoder right, DigitalInput rsensor, DigitalInput lsensor)
	{
		super();
		
		this.fr = fr;
		this.fl = fl;
		this.mr = mr;
		this.ml = ml;
		this.rr = rr;
		this.rl = rl;
		this.navx = navx;
		this.left = left;
		this.right = right;
		this.rsensor = rsensor;
		this.lsensor = lsensor;
		
		yawPID = new PIDController(P, I, D, navx, this);
		yawPID.setInputRange(-180.0, 180.0);
		yawPID.setOutputRange(-1.0, 1.0);
		yawPID.setContinuous();
		yawPID.setAbsoluteTolerance(3);
		disablePID();
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
		mr.set(right);
		ml.set(left);
		rr.set(right);
		rl.set(left);
	}
	
	public double getRightDistance()
	{
		return right.getDistance();
	}
	
	public double getLeftDistance()
	{
		return left.getDistance();
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
	
	public void goStraight(double direction, double yaw)
	{
		enablePID();
		this.setHeading(yaw);
		this.setDirection(direction);
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
	
	public boolean onTarget()
	{
		return yawPID.onTarget();
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
