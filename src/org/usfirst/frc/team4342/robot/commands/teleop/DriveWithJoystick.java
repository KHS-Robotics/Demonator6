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
		drive.set(adjust(-joystick.getX()), adjust(-joystick.getY()));
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
	
	private static double adjust(double input)
	{
		final double SENSITIVITY = 0.67;
		return (SENSITIVITY * Math.pow(input, 3)+ (1 - SENSITIVITY) * input);
	}
}
