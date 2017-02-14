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
	public static final double P = 0.01, I = 0.0, D = 0.01, F = 0.01;
	
	private boolean isAccumulating, isAgitating, isShooting, isSetFar;
	
	private CANTalon intake, agitator, shooter;
	private Encoder shooterEnc;
	private Solenoid shootFar;
	private PIDController shooterPID;
	
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
		super(Shooter.class.getName());
		
		this.intake = intake;
		this.agitator = agitator;
		this.shooter = shooter;
		this.shooterEnc = shooterEnc;
		this.shootFar = shootFar;
		
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
			
		intake.set(-1.0);
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
		
		setFar();
		shoot(62);
	}
	
	/**
	 * Sets the solenoid to shoot close and then enables the shooter
	 */
	public void shootClose()
	{	
		if (isShooting)
			return;
		isShooting = true;
		
		setClose();
		shoot(62);
	}
	
	/**
	 * Disables the shooter
	 */
	public void stopShooting()
	{
		if (!isShooting)
			return;
		isShooting = false;
		
		disableShooterPID();
	}
	
	/**
	 * Gets the current speed of the shooter
	 * @return the speed of the shooter, as read by the encoder
	 * @see {@link edu.wpi.first.wpilibj.Encoder#getRate()}
	 */
	public double getSpeed()
	{
		return shooterEnc.getRate();
	}
	
	/**
	 * Sets the solenoid on the shooter to a longer ranged shot
	 */
	private void setFar()
	{
		if (!isSetFar)
			shootFar.set(true);
		isSetFar = true;
	}
	
	/**
	 * Sets the solenoid on the shooter to a shorter ranged shot
	 */
	private void setClose()
	{
		if (isSetFar)
			shootFar.set(false);
		isSetFar = false;
	}
	
	/**
	 * Sets the shooter PID to a specified setpoint
	 * @param setpoint the setpoint for the shooter, ranging from
	 * zero percent to 100 percent
	 */
	private void shoot(int setpoint)
	{
		shooterPID.setSetpoint(setpoint);
		enableShooterPID();
	}
	
	/**
	 * Internal method to enable the shooter PID
	 */
	private void enableShooterPID()
	{
		shooterPID.enable();
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
}
