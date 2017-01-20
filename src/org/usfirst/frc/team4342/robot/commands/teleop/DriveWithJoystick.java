package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command
{
	private Joystick joystick;
	private TankDrive drive;
	
	public DriveWithJoystick(Joystick joystick, TankDrive drive)
	{
		super();
		
		this.requires(drive);
		
		this.joystick = joystick;
		this.drive = drive;
	}
	
	@Override
	protected void execute()
	{
		drive.set(joystick.getX(), joystick.getY());
	}
	
	@Override
	protected void end()
	{
		drive.set(0, 0);
		drive.disablePID();
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
