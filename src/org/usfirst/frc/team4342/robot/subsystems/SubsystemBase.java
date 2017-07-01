package org.usfirst.frc.team4342.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Superclass for subsystems used on the robot for methods
 * that they may share
 */
public abstract class SubsystemBase extends Subsystem 
{
	/**
	 * Creates a new subsystem
	 */
	public SubsystemBase()
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
