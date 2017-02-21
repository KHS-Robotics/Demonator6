package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;

/**
 * Command to place a gear on a peg on the airship
 * 
 * @see org.usfirst.frc.team4342.robot.commands.auton.AutonomousCommand
 */
public class PlaceGear extends AutonomousCommand
{
	private GearPlacer placer;

	/**
	 * Creates a new <code>PlaceGear</code> command.
	 * @param placer the <code>GearPlacer</code> subsystem
	 */
	public PlaceGear(GearPlacer placer)
	{
		super(2);
		
		this.requires(placer);
		
		this.placer = placer;
	}
	
	/**
	 * Lowers the gear placer
	 */
	@Override
	protected void initialize()
	{
		placer.lower();
	}
	
	/**
	 * Returns if the gear placer is lowered
	 * @return true if the gear placer is lowered, false otherwise
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
