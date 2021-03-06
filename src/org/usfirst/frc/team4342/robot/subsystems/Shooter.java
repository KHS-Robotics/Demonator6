package org.usfirst.frc.team4342.robot.subsystems;


import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Shooter subsystem to shoot fuel into the boilers
 */
public class Shooter extends SubsystemBase
{
	public static final double P = 0.007, I = 0.0, D = 0.0082, F = 1 / 1400;
	
	private boolean isAgitating, isShooting, isSetFar;
	
	private CANTalon agitator, shooter;
	private Encoder shooterEnc;
	private Solenoid shootFar, camLight;
	private PIDController shooterPID;
	
	/**
	 * Creates a new <code>Shooter</code> subsystem
	 * @param agitator the motor to agitate the fuel to help the shooter
	 * @param shooter the motor to shoot the fuel
	 * @param shooterEnc the shoot motor encoder to utilize a rate PID controller
	 * @param shootFar the solenoid to change the trajectory of the fuel to shoot far or close
	 * @param camLight the LED ring used for vision processing
	 */
	public Shooter(CANTalon agitator, CANTalon shooter, Encoder shooterEnc, Solenoid shootFar, Solenoid camLight)
	{
		this.agitator = agitator;
		this.shooter = shooter;
		this.shooterEnc = shooterEnc;
		this.shootFar = shootFar;
		this.camLight = camLight;
		
		setCamLight(true);
		
		shooter.setPIDSourceType(PIDSourceType.kRate);
		shooterEnc.setPIDSourceType(PIDSourceType.kRate);
		shooterPID = new PIDController(P, I, D, F, this.shooterEnc, this.shooter);
		shooterPID.setInputRange(-1400, 0);
		shooterPID.setOutputRange(-1, 0);
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
		setShooter(850);
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
		setShooter(800);
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
	 * @see Encoder#getRate()
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
	 * @param on true to turn on, false otherwise
	 */
	public void setCamLight(boolean on)
	{
		camLight.set(on);
	}
	
	/**
	 * Sets the shooter PID to a specified setpoint without worrying
	 * about the current state of the solenoid
	 * @param setpoint the setpoint for the shooter, ranging from
	 * 0 to 1400
	 */
	public void setShooter(int setpoint)
	{
		if(setpoint > 1400)
			setpoint = -1400;
		else if(setpoint < 0)
			setpoint = 0;
		else
			setpoint = -setpoint;
		
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
