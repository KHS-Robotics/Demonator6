package org.usfirst.frc.team4342.robot.subsystems;


import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem
{
	private Spark shooter;
	
	public Shooter(Spark intake, Spark agitator, Spark shooter)
	{
		super();
		this.shooter = shooter;
	}
	
	private static double currentOutputIntake, currentOutputAgitator, currentOutputShooter;
	
	public void accumulate()
	{
		if(Math.abs(0.67 - currentOutputIntake) < 0.01)
			return;
			
		//intake.set(0.67);
	}
	
	public void stopAccumulating()
	{
		if(Math.abs(0 - currentOutputIntake) < 0.01)
			return;
		
		//intake.set(0);
	}
	
	public void agitate()
	{
		if(Math.abs(0.8 - currentOutputAgitator) < 0.01)
			return;
		
		//agitator.set(0.8);
	}
	
	public void stopAgitating()
	{
		if(Math.abs(0 - currentOutputAgitator) < 0.01)
			return;
		
		//agitator.set(0);
	}
	
	public void shoot()
	{
		if(Math.abs(0.83 - currentOutputShooter) < 0.01)
			return;
		
		shooter.set(0.83);
	}
	
	public void stopShooting()
	{
		if(Math.abs(0 - currentOutputShooter) < 0.01)
			return;
		
		shooter.set(0);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(null);
		//this.setDefaultCommand(new ShootWithSwitchBox(IO.getSwitchBox(), this));
	}
}
