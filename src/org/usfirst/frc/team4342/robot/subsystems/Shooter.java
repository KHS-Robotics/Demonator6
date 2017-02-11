package org.usfirst.frc.team4342.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Shooter subsystem to shoot fuel into the boilers
 */
public class Shooter extends DemonSubsystem
{
	private static final double P = 0.0, I = 0.0, D = 0.0, F = 0.01;
	
	private boolean isAccumulating, isAgitating, isShooting;
	
	private CANTalon intake, agitator, shooter;
	private Encoder shooterEnc;
	private Solenoid shootFar;
	private PIDController shooterPID;
	
	private boolean isSetFar;
	
	/**
	 * Creates a new <code>Shooter</code> subsystem
	 * @param intake the motor to intake fuel
	 * @param agitator the motor to agitate the fuel to help the shooter
	 * @param shooter the motor to shoot the fuel
	 * @param shooterEnc the shoot motor encoder to utilize a rate PID controller
	 * @param shootFar the solenoid to change the trajectory of the fuel to shoot far or close
	 */
	public Shooter(CANTalon intake, CANTalon agitator, CANTalon shooter, Encoder shooterEnc, Solenoid shootFar)
	{
		super();
		
		this.intake = intake;
		this.agitator = agitator;
		this.shooter = shooter;
		this.shooterEnc = shooterEnc;
		this.shootFar = shootFar;
		
		this.shootFar.set(false);
		
		shooter.setPIDSourceType(PIDSourceType.kRate);
		shooterEnc.setPIDSourceType(PIDSourceType.kRate);
		shooterPID = new PIDController(P, I, D, F, this.shooterEnc, this.shooter);
		shooterPID.setInputRange(0, 100);
		shooterPID.setOutputRange(0, 1);
	}
	
	/**
	 * Enables the accumulator
	 */
	public void accumulate()
	{
		if(isAccumulating)
			return;
		isAccumulating = true;
			
		intake.set(0.67);
	}
	
	/**
	 * Disables the accumulator
	 */
	public void stopAccumulating()
	{
		if(!isAccumulating)
			return;
		isAccumulating = false;
		
		intake.set(0);
	}
	
	/**
	 * Enables the agitator
	 */
	public void agitate()
	{
		if(isAgitating)
			return;
		isAgitating = true;
		
		agitator.set(0.8);
	}
	
	/**
	 * Disables the agitator
	 */
	public void stopAgitating()
	{
		if(!isAgitating)
			return;
		isAgitating = false;
		
		agitator.set(0);
	}
	
	/**
	 * Sets the solenoid to shoot far and then enables the shooter
	 */
	public void shootFar()
	{
		if (isShooting)
			return;
		isShooting = true;
		
		if (!isSetFar)
			shootFar.set(true);
		isSetFar = true;
		
		enableShooterPID();
		shooterPID.setSetpoint(85);
	}
	
	/**
	 * Sets the solenoid to shoot close and then enables the shooter
	 */
	public void shootClose()
	{
		if (isShooting)
			return;
		isShooting = true;
		
		if (isSetFar)
			shootFar.set(false);
		isSetFar = false;
		
		enableShooterPID();
		shooterPID.setSetpoint(0.85);
	}
	
	/**
	 * Disables the shooter
	 */
	public void stopShooting()
	{
		if (!isShooting)
			return;
		isShooting = false;
		
		shooterPID.setSetpoint(0);
		disableShooterPID();
	}
	
	/**
	 * Internal method to disable the shooter PID
	 */
	private void disableShooterPID()
	{
		if (shooterPID.isEnabled())
		{
			shooterPID.disable();
		}
	}
	
	/**
	 * Internal method to enable the shooter PID
	 */
	private void enableShooterPID()
	{
		shooterPID.enable();
	}
}
