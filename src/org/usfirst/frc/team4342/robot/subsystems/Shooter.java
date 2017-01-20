package org.usfirst.frc.team4342.robot.subsystems;

import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.commands.teleop.ShootWithSwitchBox;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem
{
	private CANTalon intake, agitator, shooter;
	
	public Shooter(CANTalon intake, CANTalon agitator, CANTalon shooter)
	{
		super();
		
		this.intake = intake;
		this.agitator = agitator;
		this.shooter = shooter;
	}
	
	public static final int ACCUMULATE_BUTTON = 1, AGITATE_BUTTON = 2, SHOOT_BUTTON = 3;
	private static double currentOutputIntake, currentOutputAgitator, currentOutputShooter;
	
	public void accumulate()
	{
		if(Math.abs(0.67 - currentOutputIntake) < 0.05)
			return;
			
		intake.set(0.67);
	}
	
	public void stopAccumulating()
	{
		if(Math.abs(0 - currentOutputIntake) < 0.05)
			return;
		
		intake.set(0);
	}
	
	public void agitate()
	{
		if(Math.abs(0.8 - currentOutputAgitator) < 0.05)
			return;
		
		agitator.set(0.8);
	}
	
	public void stopAgitating()
	{
		if(Math.abs(0 - currentOutputAgitator) < 0.05)
			return;
		
		agitator.set(0);
	}
	
	public void shoot()
	{
		if(Math.abs(0.85 - currentOutputShooter) < 0.05)
			return;
		
		shooter.set(0.85);
	}
	
	public void stopShooting()
	{
		if(Math.abs(0 - currentOutputShooter) < 0.05)
			return;
		
		shooter.set(0);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(new ShootWithSwitchBox(IO.getSwitchBox(), this));
	}
}
