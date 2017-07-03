package org.usfirst.frc.team4342.robot.auton;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Superclass for Autonomous Routines
 * 
 * @see CommandGroup
 */
public abstract class AutonomousRoutine extends CommandGroup
{
	private boolean useDeadReckoning;
	
	private Alliance alliance;
	
	protected AutonomousRoutine() {}
	
	protected AutonomousRoutine(Alliance alliance)
	{
		super();
		
		this.alliance = alliance;
	}
	
	@Override
	public void initialize()
	{
		IO.getInstance().Drive.resetNavX();
		IO.getInstance().Drive.resetEncoders();
		IO.getInstance().Drive.shiftLow();
	}
	
	/**
	 * Gets if the robot is on the blue alliance
	 * @return true if on the blue alliance, false otherwise
	 */
	protected boolean isBlueAlliance()
	{
		return Alliance.Blue.equals(alliance);
	}
	
	/**
	 * Gets if the robot is on the red alliance
	 * @return true if on the red alliance, false otherwise
	 */
	protected boolean isRedAlliance()
	{
		return Alliance.Red.equals(alliance);
	}

	/**
	 * Gets if the routine is using dead reckoning
	 * @return true if using dead reckoning, false otherwise
	 */
	protected boolean isUsingDeadReckoning() 
	{
		return useDeadReckoning;
	}

	/**
	 * Sets dead reckoning
	 * @param useDeadReckoning true to use dead reckoning, false otherwise
	 */
	public void setUseDeadReckoning(boolean useDeadReckoning) 
	{
		this.useDeadReckoning = useDeadReckoning;
	}
}
