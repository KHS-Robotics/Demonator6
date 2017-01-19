package org.usfirst.frc.team4342.robot.subsystems.shooter;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.command.Command;

public class ShootWithSwitchBox extends Command
{
	
	private static boolean hasAccumulated, hasAgitated, hasShot;

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
	
		if (USER_ACCUMULATE && !hasAccumulated) 
		{
			IO.Shooter.accumulate();
			hasAccumulated = true;
		}
		else if (hasAccumulated)
		{
			IO.Shooter.stopAccumulating();
			hasAccumulated = false;
		}
		
		if (USER_AGITATE && !hasAgitated)
		{
			IO.Shooter.agitate();
			hasAgitated = true;
		}
		else if (hasAgitated)
		{
			IO.Shooter.stopAgitating();
			hasAgitated = false;
		}
		
		if (USER_SHOOT && !hasShot)
		{
			IO.Shooter.shoot();
			hasShot = true;
		}
		else if (hasShot)
		{
			IO.Shooter.stopShooting();
			hasShot = false;
		}
		
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
