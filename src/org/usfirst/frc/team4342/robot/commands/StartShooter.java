package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Command to shoot fuel into the boiler
 */
public class StartShooter extends InstantCommand 
{
	private Shooter shooter;
	private boolean shootFar;
	
	/**
	 * Creates a new <code>Shoot</code> command
	 * @param shooter the <code>Shooter</code> subsystem
	 * @param shootFar true to set the solenoid to shoot far, false to shoot close
	 */
	public StartShooter(Shooter shooter, boolean shootFar)
	{
		this.requires(shooter);
		
		this.shooter = shooter;
		this.shootFar = shootFar;
	}

	/**
	 * Enables the agitator
	 */
	@Override
	protected void initialize()
	{
		if(shootFar)
			shooter.shootFar();
		else
			shooter.shootClose();
		
		shooter.agitate();
	}
}
