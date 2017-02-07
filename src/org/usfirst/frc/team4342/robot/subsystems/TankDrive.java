package org.usfirst.frc.team4342.robot.subsystems;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TankDrive extends Subsystem implements PIDOutput
{
	private static final double P = 0.015, I = 0.0, D = 0.019;
	private static final Value HIGH_GEAR = Value.kForward, LOW_GEAR = Value.kReverse;
	
	private CANTalon fr, fl, rr, rl, mr , ml;
	private AHRS navx;
	private DoubleSolenoid shifter;
	private Encoder left, right;
	private DigitalInput rsensor, lsensor;
	private PIDController yawPID;
	
	private Value currentGear = LOW_GEAR;
	private double direction;
	
	// TODO: Add DoubleSolenoid for shifting
	public TankDrive(CANTalon fr, CANTalon fl, CANTalon mr, CANTalon ml, CANTalon rr, CANTalon rl, AHRS navx,
			DoubleSolenoid shifter, Encoder left, Encoder right, DigitalInput rsensor, DigitalInput lsensor)
	{
		super();
		
		this.fr = fr;
		this.fl = fl;
		this.mr = mr;
		this.ml = ml;
		this.rr = rr;
		this.rl = rl;
		this.navx = navx;
		this.shifter = shifter;
		this.left = left;
		this.right = right;
		this.rsensor = rsensor;
		this.lsensor = lsensor;
		
		shiftLow();
		
		yawPID = new PIDController(P, I, D, navx, this);
		yawPID.setInputRange(-180.0, 180.0);
		yawPID.setOutputRange(-1.0, 1.0);
		yawPID.setContinuous();
		yawPID.setAbsoluteTolerance(3);
		disablePID();
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
		mr.set(right);
		ml.set(left);
		rr.set(right);
		rl.set(left);
	}
	
	public void shift()
	{
		if(LOW_GEAR.equals(currentGear))
			shiftHigh();
		else 
			shiftLow();
	}
	
	public void shiftLow()
	{
		if(LOW_GEAR.equals(currentGear))
			return;
		
		shifter.set(LOW_GEAR);
		currentGear = LOW_GEAR;
	}
	
	public void shiftHigh()
	{
		if(HIGH_GEAR.equals(currentGear))
			return;
		
		shifter.set(HIGH_GEAR);
		currentGear = HIGH_GEAR;
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
