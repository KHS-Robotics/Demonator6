package org.usfirst.frc.team4342.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem
{
	private static final double kF = 0.01;
	
	private boolean isAccumulating, isAgitating, isShooting;
	
	private CANTalon intake, agitator, shooter;
	private Encoder shooterEnc;
	private PIDController shooterPID;
	
	public Shooter(CANTalon intake, CANTalon agitator, CANTalon shooter, Encoder shooterEnc)
	{
		super();
		
		this.intake = intake;
		this.agitator = agitator;
		this.shooter = shooter;
		this.shooterEnc = shooterEnc;
		
		shooter.setPIDSourceType(PIDSourceType.kRate);
		shooterEnc.setPIDSourceType(PIDSourceType.kRate);
		shooterPID = new PIDController(0, 0, 0, kF, this.shooterEnc, this.shooter);
		shooterPID.setInputRange(0, 100);
		shooterPID.setOutputRange(0, 1);
	}
	
	public void accumulate()
	{
		if(isAccumulating)
			return;
		isAccumulating = true;
			
		intake.set(0.67);
	}
	
	public void stopAccumulating()
	{
		if(!isAccumulating)
			return;
		isAccumulating = false;
		
		intake.set(0);
	}
	
	public void agitate()
	{
		if(isAgitating)
			return;
		isAgitating = true;
		
		agitator.set(0.8);
	}
	
	public void stopAgitating()
	{
		if(!isAgitating)
			return;
		isAgitating = false;
		
		agitator.set(0);
	}
	
	public void shoot()
	{
		if (isShooting)
			return;
		isShooting = true;
		
		enableShooterPID();
		shooterPID.setSetpoint(0.85);
	}
	
	public void stopShooting()
	{
		if (!isShooting)
			return;
		isShooting = false;
		
		shooterPID.setSetpoint(0);
		disableShooterPID();
	}
	
	private void disableShooterPID()
	{
		if (shooterPID.isEnabled())
		{
			shooterPID.disable();
		}
	}
	
	private void enableShooterPID()
	{
		shooterPID.enable();
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(null);
		//this.setDefaultCommand(new ShootWithSwitchBox(IO.getSwitchBox(), this));
	}
}
