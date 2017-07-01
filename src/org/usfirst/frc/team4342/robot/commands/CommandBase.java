package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.logging.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Superclass for all commands used by Demonator
 */
public abstract class CommandBase extends Command
{
	private Alliance alliance;
	
	/**
	 * Creates a new <code>DemonCommand</code>
	 */
	public CommandBase()
	{
		super();
	}
	
	/**
	 * Creates a new <code>DemonCommand</code> with a specified timeout
	 * @param timeout the timeout of the command in seconds
	 */
	public CommandBase(double timeout)
	{
		super(timeout);
	}
	
	/**
	 * Checks if robot is on red alliance
	 * @return true if on red alliance, false otherwise
	 */
	protected boolean isRedAlliance()
	{
		if(alliance == null || Alliance.Invalid.equals(alliance))
			getAlliance();
		
		return Alliance.Red.equals(alliance);
	}
	
	/**
	 * Checks if robot is on blue alliance
	 * @return true if on blue alliance, false otherwise
	 */
	protected boolean isBlueAlliance()
	{
		if(alliance == null || Alliance.Invalid.equals(alliance))
			getAlliance();
		
		return Alliance.Blue.equals(alliance);
	}
	
	/**
	 * Gets the current alliance from the Driver Station
	 */
	private void getAlliance()
	{
		alliance = DriverStation.getInstance().getAlliance();
		
		if (Alliance.Invalid.equals(alliance))
		{
			Logger.warning("Alliance is invalid for " + this.getName() + " auton command.");
		}
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
	
	/** {@inheritDoc} */
	@Override
	protected abstract boolean isFinished();
}
