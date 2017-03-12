package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.Shooter;

/**
 * Command to shoot fuel into the boiler
 */
public class Shoot extends AutonomousCommand 
{
	private Shooter shooter;
	private boolean shootFar;
	
	/**
	 * Creates a new <code>Shoot</code> command
	 * @param shooter the <code>Shooter</code> subsystem
	 * @param shootFar true to set the solenoid to shoot far, false to shoot close 
	 * @param duration how long the shooter should run, in seconds
	 */
	public Shoot(Shooter shooter, boolean shootFar, double duration)
	{
		super(duration);
		
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

	@Override
	protected void execute() {}

	/**
	 * Stops the agitator and shooter
	 */
	@Override
	protected void end() 
	{
		shooter.stopAgitating();
		shooter.stopShooting();
	}

	@Override
	protected boolean isFinished() 
	{
		return this.isTimedOut();
	}
}
