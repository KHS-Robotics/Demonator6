package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.logging.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;

public abstract class AutonomousCommand extends Command
{
	private Alliance alliance;
	
	public AutonomousCommand()
	{
		alliance = DriverStation.getInstance().getAlliance();
		
		if (alliance.equals(Alliance.Invalid))
		{
			Logger.warning("Alliance is invalid.");
		}
	}
	
	public boolean isRedAlliance()
	{
		return alliance.equals(Alliance.Red);
	}
	
	public boolean isBlueAlliance()
	{
		return alliance.equals(Alliance.Blue);
	}
}
