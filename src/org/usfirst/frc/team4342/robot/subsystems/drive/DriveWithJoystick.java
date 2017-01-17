package org.usfirst.frc.team4342.robot.subsystems.drive;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command
{
	private DriveTrain dt;	
	
	public DriveWithJoystick(DriveTrain dt)
	{
		this.dt = dt;
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void execute()
	{
		IO.setDrive(IO.getDriveX(), IO.getDriveY());
	}
	
	@Override
	protected void end()
	{
		
	}
	
	@Override
	public void interrupted()
	{
		this.end();
	}
}
