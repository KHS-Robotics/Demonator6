package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;

/**
 * Command to raise the gear placer
 * 
 * @see org.usfirst.frc.team4342.robot.commands.auton.AutonomousCommand
 */
public class RaiseGear extends AutonomousCommand
{
	private GearPlacer placer;

	/**
	 * Creates a new <code>RaiseGear</code> command.
	 * @param placer the <code>GearPlacer</code> subsystem
	 */
	public RaiseGear(GearPlacer placer)
	{
		super(1);
		
		this.requires(placer);
		
		this.placer = placer;
	}
	
	/**
	 * Raises the gear placer
	 */
	@Override
	public void initialize()
	{
		placer.raise();
	}

	/**
	 * Returns if the gear placer is raised
	 * @return true if the gear placer is raised, false otherwise
	 */
	@Override
	protected boolean isFinished() 
	{
		return this.isTimedOut();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void execute() {}

	/** {@inheritDoc} */
	@Override
	protected void end() {}
}