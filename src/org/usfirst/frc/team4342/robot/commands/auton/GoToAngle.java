package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class GoToAngle extends Command
{
	private TankDrive drive;
	private double yaw;
	
	public GoToAngle(TankDrive drive, double yaw)
	{
		this.requires(drive);
		
		this.drive = drive;
		this.yaw = yaw;
	}
	
	@Override
	protected boolean isFinished() 
	{
		return drive.onTarget();
	}
	
	@Override
	public void initialize()
	{
		drive.enablePID();
		drive.setHeading(yaw);
	}
	
	@Override
	public void end()
	{
		drive.disablePID();
	}
	
	@Override
	public void interrupted()
	{
		this.end();
	}
}
