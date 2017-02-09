package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;

public class RaiseGear extends AutonomousCommand
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