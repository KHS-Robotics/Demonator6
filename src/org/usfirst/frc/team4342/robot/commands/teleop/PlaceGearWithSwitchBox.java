package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class PlaceGearWithSwitchBox extends Command
{	
	private Joystick switchBox;
	private GearPlacer placer;
	
	public PlaceGearWithSwitchBox(Joystick switchBox, GearPlacer placer)
	{
		super();
		
		this.requires(placer);
		
		this.switchBox = switchBox;
		this.placer = placer;
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
		final boolean USER_LOWER = switchBox.getRawButton(IO.LOWER_BUTTON);
		
		if(USER_LOWER)
			placer.lower();
		else
			placer.raise();	
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
