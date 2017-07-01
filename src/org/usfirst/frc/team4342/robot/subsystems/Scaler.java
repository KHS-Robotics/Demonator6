package org.usfirst.frc.team4342.robot.subsystems;


import com.ctre.CANTalon;

/**
 * Scaler subsystem to scale the airship
 */
public class Scaler extends SubsystemBase
{
	private CANTalon motor;
	
	private boolean enabled;
	
	/**
	 * Creates a new <code>Scaler</code> subsystem
	 * @param motor the winch to pull the robot up
	 */
	public Scaler(CANTalon motor)
	{
		this.motor = motor;
	}
	
	/**
	 * Enables the winch
	 */
	public void enable()
	{
		if(enabled)
			return;
		enabled = true;
		
		motor.set(-1.0);
	}
	
	/**
	 * Disables the winch
	 */
	public void disable()
	{
		if(!enabled)
			return;
		enabled = false;
		
		motor.set(0);
	}
}
