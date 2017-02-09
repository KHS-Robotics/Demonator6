package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;

public class PlaceGear extends AutonomousCommand
{
	private GearPlacer placer;

	public PlaceGear(GearPlacer placer)
	{
		this.requires(placer);
		this.placer = placer;
	}
	
	@Override
	public void initialize()
	{
		placer.lower();
	}
	
	@Override
	public void interrupted()
	{
		this.end();
	}

	@Override
	protected boolean isFinished() 
	{
		return placer.isLowered();
	}
}
