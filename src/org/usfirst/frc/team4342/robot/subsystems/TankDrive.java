package org.usfirst.frc.team4342.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive extends Subsystem implements PIDOutput
{
	private static final double P = 0.015, I = 0.0, D = 0.019;
	
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
		
		yawPID = new PIDController(-P, -I, -D, navx, this);
		yawPID.setInputRange(-180.0, 180.0);
		yawPID.setOutputRange(-1.0, 1.0);
		yawPID.setContinuous();
		
	}
	
	public void setPID(double p, double i, double d)
	{
		yawPID.setPID(p, i, d);
	}
	
	public void set(double x, double y)
	{
		double right = y + x;
		double left = y - x;
		
		right = normalize(right);
		left = normalize(left);

		setLeft(left);
		setRight(right);
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
	
	@Override
	public void pidWrite(double output)
	{
		SmartDashboard.putNumber("Drive-Error-Inst", yawPID.getError());
		SmartDashboard.putNumber("Drive-Error-Avg", yawPID.getAvgError());
		
		double right = direction + output;
		double left = direction - output;
		
		right = normalize(right);
		left = normalize(left);

		setLeft(left);
		setRight(right);
	}
	
	private static double normalize(double output)
	{
		if (output > 1)
			return 1;
		else if (output < -1)
			return -1;
		
		return output;
	}
	
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(null);
		//this.setDefaultCommand(new DriveWithJoystick(IO.getDriveSitck(), this));
	}
}
