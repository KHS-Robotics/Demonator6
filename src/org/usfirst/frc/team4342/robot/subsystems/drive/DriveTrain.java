package org.usfirst.frc.team4342.robot.subsystems.drive;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem implements PIDOutput
{
	private PIDController pid;
	private double direction;
	
	public DriveTrain(double p, double i, double d)
	{
		pid = new PIDController(p, i, d, IO.getNavx(), this);
		pid.setInputRange(-180, 180);
		pid.setOutputRange(-1, 1);
		pid.setContinuous();
	}
	
	public void enablePID()
	{
		pid.enable();
	}
	
	public void disablePID()
	{
		if(pid.isEnabled()) 
			pid.disable();
	}
	
	public void setSetpoint(double yaw)
	{
		pid.setSetpoint(yaw);
	}
	
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(new DriveWithJoystick(new DriveTrain(0,0,0)));
	}
	
	@Override
	public void pidWrite(double output)
	{
		IO.setDrive(output, direction);
	}
	
}
