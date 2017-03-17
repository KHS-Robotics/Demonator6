package org.usfirst.frc.team4342.robot.commands.auton.routines;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Superclass for Autonomous Routines
 * 
 * @see CommandGroup
 */
public abstract class AutonomousRoutine extends CommandGroup
{
	private HookAlign align;
	private boolean useVision;
	
	private Alliance alliance;
	
	protected AutonomousRoutine() {}
	
	protected AutonomousRoutine(Alliance alliance)
	{
		super();
		
		this.alliance = alliance;
	}
	
	public void initialize() {}
	
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
		return align == HookAlign.DEAD_RECKONING;
	}
	
	/**
	 * Gets if the routine is using <code>AlignHook</code>
	 * @return true if using <code>AlignHook</code>, false otherwise
	 * @see org.usfirst.frc.team4342.robot.commands.auton.AlignHook
	 */
	protected boolean isUsingAlignHook()
	{
		return align == HookAlign.ALIGN_HOOK;
	}
	
	/**
	 * Gets if the routine is using <code>TurnUntilSeePeg</code>
	 * @return true if using <code>TurnUntilSeePeg</code>, false otherwise
	 * @see org.usfirst.frc.team4342.robot.commands.auton.TurnUntilSeePeg
	 */
	protected boolean isUsingTurnUntilSeePeg()
	{
		return align == HookAlign.TURN_UNTIL_SEE_PEG;
	}

	/**
	 * Sets dead reckoning
	 * @param useDeadReckoning true to use dead reckoning, false otherwise
	 */
	public void setHookAlign(HookAlign align) 
	{
		this.align = align;
	}
	
	/**
	 * Used to determine if the routine is using the
	 * calculated values from off board vision processing
	 * @param use true to use, false otherwise
	 */
	public void setUseVision(boolean use)
	{
		this.useVision = use;
	}
	
	/**
	 * Gets if the routine is using vision
	 * @return true if using vision, false otherwise
	 */
	protected boolean isUsingVision()
	{
		return useVision;
	}
}
