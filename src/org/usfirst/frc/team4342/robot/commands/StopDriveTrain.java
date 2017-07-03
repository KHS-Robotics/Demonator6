package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StopDriveTrain extends InstantCommand 
{
	private TankDrive drive;
	
	public StopDriveTrain(TankDrive drive)
	{
		this.requires(drive);
		
		this.drive = drive;
	}
	
	@Override
	protected void initialize()
	{
		drive.stopMotors();
	}
}
