package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseGear extends Command
{
	private GearPlacer placer;

	public RaiseGear(GearPlacer placer)
	{
		this.requires(placer);
		this.placer = placer;
	}
	
	@Override
	public void initialize()
	{
		placer.raise();
	}
	
	@Override
	public void interrupted()
	{
		this.end();
	}

	@Override
	protected boolean isFinished() 
	{
		return placer.isRaised();
	}
}