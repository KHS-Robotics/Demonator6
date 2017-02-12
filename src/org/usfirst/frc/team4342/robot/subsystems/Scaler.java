package org.usfirst.frc.team4342.robot.subsystems;

import org.usfirst.frc.team4342.robot.ButtonMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Scaler subsystem to scale the airship
 */
public class Scaler extends DemonSubsystem
{
	private CANTalon motor;
	private DigitalInput limitSwitch;
	private Joystick rightstick;
	
	private boolean enabled;
	
	/**
	 * Creates a new <code>Scaler</code> subsystem
	 * @param motor the winch to pull the robot up
	 * @param limitSwitch the limit switch to recognize when
	 * the robot is fully scaled
	 */
	public Scaler(CANTalon motor, DigitalInput limitSwitch, Joystick rightstick)
	{
		super();
		
		this.motor = motor;
		this.limitSwitch = limitSwitch;
		this.rightstick = rightstick;
	}
	
	/**
	 * Enables the winch
	 */
	public void enable()
	{
		if(enabled)
			return;
		enabled = true;
		
		motor.set(0.5);
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
	
	/**
	 * Gets if the robot has scaled, effectively if the state of the limit switch.
	 * @return true if the robot has scaled, false otherwise
	 */
	public boolean hasScaled()
	{	
		return limitSwitch.get();
	}
}
