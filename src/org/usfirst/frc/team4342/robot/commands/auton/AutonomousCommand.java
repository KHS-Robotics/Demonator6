package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.commands.DemonCommand;

/**
 * Superclass for our autonomous commands so they all have access
 * to some important functions that may be needed to run the commands
 * 
 * @see edu.wpi.first.wpilibj.command.Command
 */
public abstract class AutonomousCommand extends DemonCommand
{
	/**
	 * Creates a new <code>AutonomousCommand</code>
	 */
	public AutonomousCommand()
	{
		super();
	}
	
	/**
	 * Creates a new <code>AutonomousCommand</code> with a specified timeout
	 * @param timeout the timeout of the command in seconds
	 */
	public AutonomousCommand(double timeout)
	{
		super(timeout);
	}
}
