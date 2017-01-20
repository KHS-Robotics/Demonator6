package org.usfirst.frc.team4342.robot.subsystems.scaler;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.command.Command;

public class Scaler extends Command
{
	public Scaler()
	{
		super();
		
		this.requires(IO.Drive.getDriveTrain());
	}
	
	@Override
	protected boolean isFinished() 
	{
		return IO.Scaler.hasScaled();
	}
	
	@Override
	public void initialize()
	{
		IO.Drive.enablePID();
		IO.Drive.setDirection(0.25);
		IO.Drive.setSetpoint(IO.Drive.getYaw());
		IO.Scaler.enable();
	}
	
	@Override
	protected void execute()
	{
		
	}
	
	@Override
	protected void end()
	{
		IO.Scaler.disable();
		IO.Drive.disablePID();
	}
	
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
