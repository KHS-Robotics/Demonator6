package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StopScaler extends InstantCommand 
{
	private Scaler scaler;
	
	public StopScaler(Scaler scaler)
	{
		this.requires(scaler);
		this.scaler = scaler;
	}
	
	@Override
	protected void initialize()
	{
		scaler.disable();
	}
}
