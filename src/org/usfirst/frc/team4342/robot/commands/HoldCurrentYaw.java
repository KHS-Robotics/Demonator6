package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.Joystick;

public class HoldCurrentYaw extends CommandBase
{	
	private double yaw;
	
	private Joystick joystick;
	private TankDrive drive;
	
	public HoldCurrentYaw(Joystick joystick, TankDrive drive)
	{
		this.requires(drive);
		
		this.joystick = joystick;
		this.drive = drive;
	}

	@Override
	protected void initialize() 
	{
		yaw = drive.getHeading();
	}

	@Override
	protected void execute() 
	{
		drive.goStraight(joystick.getY(), yaw);
	}

	@Override
	protected void end() {}

	@Override
	protected boolean isFinished() 
	{
		return false;
	}
}