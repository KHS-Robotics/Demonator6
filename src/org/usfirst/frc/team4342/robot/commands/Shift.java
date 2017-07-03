package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class Shift extends InstantCommand
{
	private static final int DEBOUNCE_TIME_MS = 500;
	private static long lastTimeUsed;
	
	private TankDrive drive;
	
	public Shift(TankDrive drive)
	{
		this.requires(drive);
		
		this.drive = drive;
	}
	
	@Override
	protected void initialize()
	{
		if(System.currentTimeMillis() - lastTimeUsed >= DEBOUNCE_TIME_MS)
			drive.shift();
		
		lastTimeUsed = System.currentTimeMillis();
	}
}
