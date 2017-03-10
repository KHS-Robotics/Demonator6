package org.usfirst.frc.team4342.robot.commands.auton.routines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Superclass for Autonomous Routines
 * 
 * @see CommandGroup
 */
public abstract class AutonomousRoutine extends CommandGroup
{
	protected AutonomousRoutine() {}
	
	private boolean useDeadReckoning;
	
	/**
	 * Gets if the robot is on the blue alliance
	 * @return true if on the blue alliance, false otherwise
	 */
	protected boolean isBlueAlliance()
	{
		return DriverStation.getInstance().getAlliance().equals(Alliance.Blue);
	}
	
	/**
	 * Gets if the robot is on the red alliance
	 * @return true if on the red alliance, false otherwise
	 */
	protected boolean isRedAlliance()
	{
		return !isBlueAlliance();
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
