package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ShootWithSwitchBox extends Command
{
	private Joystick switchBox;
	private Shooter shooter;
	
	public ShootWithSwitchBox(Joystick switchBox, Shooter shooter)
	{
		super();
		
		this.requires(shooter);
		
		this.switchBox = switchBox;
		this.shooter = shooter;
	}

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
		final boolean USER_ACCUMULATE = switchBox.getRawButton(ButtonMap.Shooter.ACCUMULATE);
		final boolean USER_AGITATE = switchBox.getRawButton(ButtonMap.Shooter.AGITATE);
		final boolean USER_SHOOT = switchBox.getRawButton(ButtonMap.Shooter.SHOOT);
	
		if (USER_ACCUMULATE) 
			shooter.accumulate();
		else
			shooter.stopAccumulating();
		
		if (USER_AGITATE)
			shooter.agitate();
		else
			shooter.stopAgitating();
		
		if (USER_SHOOT)
			shooter.shoot();
		else
			shooter.stopShooting();
		
	}
	
	@Override
	protected void end()
	{
		shooter.stopAccumulating();
		shooter.stopAgitating();
		shooter.stopShooting();
	}
	
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
