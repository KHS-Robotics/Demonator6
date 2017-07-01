package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Command to stop the fuel shooter
 */
public class StopShooter extends InstantCommand 
{
	private Shooter shooter;
	
	/**
	 * Creates a new <code>Shoot</code> command
	 * @param shooter the <code>Shooter</code> subsystem
	 */
	public StopShooter(Shooter shooter)
	{
		this.shooter = shooter;
	}
	
	@Override
	protected void initialize()
	{
		shooter.stopShooting();
		shooter.stopAgitating();
	}
}
