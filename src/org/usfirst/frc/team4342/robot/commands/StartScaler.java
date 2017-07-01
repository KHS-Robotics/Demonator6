package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StartScaler extends InstantCommand 
{
	private Scaler scaler;
	
	public StartScaler(Scaler scaler)
	{
		this.requires(scaler);
		this.scaler = scaler;
	}
	
	@Override
	protected void initialize()
	{
		scaler.enable();
	}
}
