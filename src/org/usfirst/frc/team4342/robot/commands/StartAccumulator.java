package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.Accumulator;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StartAccumulator extends InstantCommand 
{
	private Accumulator accumulator;
	
	public StartAccumulator(Accumulator accumulator)
	{
		this.requires(accumulator);
		this.accumulator = accumulator;
	}
	
	@Override
	protected void initialize()
	{
		accumulator.enable();
	}
}
