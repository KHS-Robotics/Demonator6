package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.commands.DemonCommand;

/**
 * Superclass for teleop commands so they can share common methods
 * that they will or may need.
 * 
 * @see edu.wpi.first.wpilibj.command.Command
 */
public abstract class TeleopCommand extends DemonCommand 
{
	/**
	 * Creates a new teleop command
	 */
	public TeleopCommand()
	{
		super();
	}
	
	/**
	 * Returns true because a teleop command is never complete;
	 * you have to explicitly call {@link #cancel()}.
	 * @return true because a teleop command is never complete
	 */
	@Override
	protected final boolean isFinished() 
	{
		return false;
	}
}
