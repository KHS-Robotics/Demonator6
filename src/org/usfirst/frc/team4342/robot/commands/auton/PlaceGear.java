package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.command.Command;

public class PlaceGear extends Command
{
	private GearPlacer placer;
	private int numLoops;
	private static final int SECONDS = 1;

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
	public void execute()
	{
		numLoops++;
	}
	
	@Override
	public void interrupted()
	{
		this.end();
	}

	@Override
	protected boolean isFinished() 
	{
		return placer.isLowered() && numLoops >= (SECONDS * 50);
	}
}