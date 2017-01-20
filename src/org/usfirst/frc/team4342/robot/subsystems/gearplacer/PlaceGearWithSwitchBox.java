package org.usfirst.frc.team4342.robot.subsystems.gearplacer;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.command.Command;

public class PlaceGearWithSwitchBox extends Command
{	
	public PlaceGearWithSwitchBox()
	{
		super();
	}
	
	@Override
	protected boolean isFinished() 
	{
		return false;
	}	

	@Override
	public void initialize()
	{
	}
	
	@Override
	protected void execute()
	{
		final boolean USER_LOWER = IO.GearPlacer.getRawButton(IO.GearPlacer.LOWER_BUTTON);
		
		if(USER_LOWER)
			IO.GearPlacer.lower();
		else
			IO.GearPlacer.raise();	
	}
	
	@Override
	protected void end()
	{
	}
	
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
