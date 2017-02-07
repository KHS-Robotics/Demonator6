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
		final boolean ACCUMULATE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.ACCUMULATE);
		final boolean AGITATE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.AGITATE);
		final boolean SHOOT_CLOSE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.SHOOT_CLOSE);
		final boolean SHOOT_FAR = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.SHOOT_FAR);
	
		if (ACCUMULATE) 
			shooter.accumulate();
		else
			shooter.stopAccumulating();
		
		if (AGITATE)
			shooter.agitate();
		else
			shooter.stopAgitating();
	
		if (SHOOT_CLOSE)
			shooter.shootClose();
		else if (SHOOT_FAR)
			shooter.shootFar();
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
