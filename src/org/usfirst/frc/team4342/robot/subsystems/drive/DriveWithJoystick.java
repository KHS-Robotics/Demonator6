package org.usfirst.frc.team4342.robot.subsystems.drive;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command
{
	@Override
	protected void execute()
	{
		IO.Drive.set(IO.Drive.getX(), IO.Drive.getY());
	}
	
	@Override
	protected void end()
	{
		IO.Drive.set(0, 0);
		IO.Drive.disablePID();
	}
	
	@Override
	public void interrupted()
	{
		this.end();
	}
	
	@Override
	protected boolean isFinished() 
	{
		return false;
	}
}
