package org.usfirst.frc.team4342.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Superclass for subsystems used on the robot for methods
 * that they may share
 */
public class DemonSubsystem extends Subsystem 
{
	/**
	 * Creates a new subsystem
	 */
	public DemonSubsystem()
	{
		super();
	}
	
	/**
	 * Calls {@link #setDefaultCommand(edu.wpi.first.wpilibj.command.Command)} 
	 * with null as the command.
	 */
	@Override
	protected final void initDefaultCommand() 
	{
		this.setDefaultCommand(null);
	}
}
