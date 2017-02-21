package org.usfirst.frc.team4342.robot.commands.auton.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Superclass for Autonomous Routines
 */
public class AutonomousRoutine extends CommandGroup
{
	private boolean useDeadReckoning;

	/**
	 * Gets if the routine is using dead reckoning
	 * @return true if using dead reckoning, false otherwise
	 */
	public boolean isUsingDeadReckoning() 
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
