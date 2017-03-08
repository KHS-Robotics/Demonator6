package org.usfirst.frc.team4342.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Superclass for teleop commands so they can share common methods
 * that they will or may need.
 * 
 * @see edu.wpi.first.wpilibj.command.Command
 */
public abstract class TeleopCommand extends Command 
{
	/**
	 * Creates a new teleop command
	 * @param name the name of the command
	 */
	public TeleopCommand()
	{
		super();
	}
	
	/**
	 * Returns true because a teleop command is never complete;
	 * you have to explicitly call {@link #cancel()}.
	 * 
	 * @return true because a teleop command is never complete
	 */
	@Override
	protected final boolean isFinished() 
	{
		return false;
	}
	
	/**
	 * Calls {@link #end()} if {@link #cancel()} is called
	 */
	@Override
	protected void interrupted()
	{
		this.end();
	}
	
	/** {@inheritDoc} */
	@Override
	protected abstract void initialize();
	
	/** {@inheritDoc} */
	@Override
	protected abstract void execute();
	
	/** {@inheritDoc} */
	@Override
	protected abstract void end();
}
