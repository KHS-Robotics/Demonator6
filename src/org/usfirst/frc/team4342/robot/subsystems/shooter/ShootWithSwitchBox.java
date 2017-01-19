package org.usfirst.frc.team4342.robot.subsystems.shooter;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.command.Command;

public class ShootWithSwitchBox extends Command
{

	@Override
	protected boolean isFinished() 
	{
		return false;
	}
	
	@Override
	public void initialize()
	{
		
	}
	
	@Override
	protected void execute()
	{
		final boolean USER_ACCUMULATE = IO.Shooter.getRawButton(IO.Shooter.ACCUMULATE_BUTTON);
		final boolean USER_AGITATE = IO.Shooter.getRawButton(IO.Shooter.AGITATE_BUTTON);
		final boolean USER_SHOOT = IO.Shooter.getRawButton(IO.Shooter.SHOOT_BUTTON);
	
		if (USER_ACCUMULATE) 
			IO.Shooter.accumulate();
		else
			IO.Shooter.stopAccumulating();
		
		if (USER_AGITATE)
			IO.Shooter.agitate();
		else
			IO.Shooter.stopAgitating();
		
		if (USER_SHOOT)
			IO.Shooter.shoot();
		else
			IO.Shooter.stopShooting();
		
	}
	
	@Override
	protected void end()
	{
		IO.Shooter.stopAccumulating();
		IO.Shooter.stopAgitating();
		IO.Shooter.stopShooting();
	}
	
	@Override
	protected void interrupted()
	{
		this.end();
	}

}
