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
	public static final double P = 0.007, I = 0.0, D = 0.0082, F = 1 / 1400;
	
	private boolean isAccumulating, isAgitating, isShooting, isSetFar;
	
	private CANTalon intake, agitator, shooter;
	private Encoder shooterEnc;
	private Solenoid shootFar, camLight;
	private PIDController shooterPID;
	
	/**
	 * Creates a new <code>Shooter</code> subsystem
	 * @param intake the motor to intake fuel
	 * @param agitator the motor to agitate the fuel to help the shooter
	 * @param shooter the motor to shoot the fuel
	 * @param shooterEnc the shoot motor encoder to utilize a rate PID controller
	 * @param shootFar the solenoid to change the trajectory of the fuel to shoot far or close
	 */
	public Shooter(CANTalon intake, CANTalon agitator, CANTalon shooter, Encoder shooterEnc, Solenoid shootFar, Solenoid camLight)
	{
		this.intake = intake;
		this.agitator = agitator;
		this.shooter = shooter;
		this.shooterEnc = shooterEnc;
		this.shootFar = shootFar;
		this.camLight = camLight;
		
		shooter.setPIDSourceType(PIDSourceType.kRate);
		shooterEnc.setPIDSourceType(PIDSourceType.kRate);
		shooterPID = new PIDController(P, I, D, F, this.shooterEnc, this.shooter);
		shooterPID.setInputRange(-1400, 0);
		shooterPID.setOutputRange(-1, 0);
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
		setShooter(-850);
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
		setShooter(-800);
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
	 * Gets if the solenoid is set to far
	 * @return true if set to far, false otherwise
	 */
	public boolean isSetFar()
	{
		return isSetFar;
	}
	
	/**
	 * Sets the Cam Light On/Off
	 * @param value 
	 */
	public void setCamLight(boolean on)
	{
		camLight.set(on);
	}
	
	/**
	 * Sets the shooter PID to a specified setpoint
	 * @param setpoint the setpoint for the shooter, ranging from
	 * zero percent to 100 percent
	 */
	public void setShooter(int setpoint)
	{
		if(setpoint > 0)
			setpoint = 0;
		else if(setpoint < -1400)
			setpoint = -1400;
		
		shooterPID.setSetpoint(setpoint);
		enableShooterPID();
	}
	
	/**
	 * Sets the solenoid on the shooter to a longer ranged shot
	 */
	private void setFar()
	{
		if (isSetFar)
			shootFar.set(false);
		isSetFar = false;
	}
	
	/**
	 * Sets the solenoid on the shooter to a shorter ranged shot
	 */
	private void setClose()
	{
		if (!isSetFar)
			shootFar.set(true);
		isSetFar = true;
	}
	
	/**
	 * Internal method to enable the shooter PID
	 */
	private void enableShooterPID()
	{
		if(!shooterPID.isEnabled())
		{
			shooterPID.enable();
		}
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
