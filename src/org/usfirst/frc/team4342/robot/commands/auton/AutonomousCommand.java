package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.logging.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Superclass for our autonomous commands so they all have access
 * to some important functions that may be needed to run the commands
 * 
 * @see edu.wpi.first.wpilibj.command.Command
 */
public abstract class AutonomousCommand extends Command
{
	private Alliance alliance;
	
	/**
	 * Creates the new <code>AutonomousCommand</code> that is a subclass of
	 * <code>Command</code>.
	 */
	public AutonomousCommand()
	{
		checkAlliance();
	}
	
	/**
	 * Creates a new <code>AutonomousCommand</code>.
	 * @param timeout the timeout of the command
	 */
	public AutonomousCommand(double timeout)
	{
		super(timeout);
		checkAlliance();
	}
	
	/**
	 * Checks if robot is on red alliance
	 * @return true if on red alliance, false otherwise
	 */
	public boolean isRedAlliance()
	{
		if(alliance == null || Alliance.Invalid.equals(alliance))
			checkAlliance();
		
		return alliance.equals(Alliance.Red);
	}
	
	/**
	 * Checks if robot is on blue alliance
	 * @return true if on blue alliance, false otherwise
	 */
	public boolean isBlueAlliance()
	{
		if(alliance == null || Alliance.Invalid.equals(alliance))
			checkAlliance();
		
		return alliance.equals(Alliance.Blue);
	}
	
	private void checkAlliance()
	{
		alliance = DriverStation.getInstance().getAlliance();
		
		if (alliance == null || alliance.equals(Alliance.Invalid))
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