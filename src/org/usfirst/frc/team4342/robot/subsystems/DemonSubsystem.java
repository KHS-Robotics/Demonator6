package org.usfirst.frc.team4342.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Superclass for subsystems used on the robot for methods
 * that they may share
 */
public abstract class DemonSubsystem extends Subsystem 
{
	/**
	 * Creates a new subsystem
	 */
	public DemonSubsystem()
	{
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(null);
	}
}
