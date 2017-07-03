package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class RotateWithJoystick extends InstantCommand
{
	private Joystick joystick;
	private TankDrive drive;
	
	public RotateWithJoystick(Joystick joystick, TankDrive drive)
	{
		this.requires(drive);
		
		this.joystick = joystick;
		this.drive = drive;
	}
	
	@Override
	protected void initialize()
	{
		final double Y = -joystick.getY();
		drive.disablePID();
		drive.set(Y, Y);
	}
}
