package org.usfirst.frc.team4342.robot.subsystems;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.commands.teleop.ScaleWithButton;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Scaler subsystem to scale the airship
 */
public class Scaler extends DemonSubsystem
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
	
	/**
	 * <p>Sets the default command to <code>ScaleWithButton</code></p>
	 * 
	 * {@inheritDoc}
	 * 
	 * @see ScaleWithButton
	 */
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(new ScaleWithButton(IO.getScaler(), new JoystickButton(IO.getSwitchBox(), ButtonMap.SwitchBox.Scaler.SCALE)));
	}
}
