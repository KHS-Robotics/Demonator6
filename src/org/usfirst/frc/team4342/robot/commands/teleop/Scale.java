package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Command to scale the airship
 */
public class Scale extends TeleopCommand
{
	private Scaler scaler;
	private JoystickButton button;
	
	/**
	 * Creates a new <code>Scale</code> command with a 10 second timeout.
	 * @param scaler the <code>Scaler</code> subsystem
	 * @see org.usfirst.frc.team4342.robot.subsystems.Scaler
	 */
	public Scale(Scaler scaler, JoystickButton button)
	{
		super();
		
		this.requires(scaler);
		
		this.scaler = scaler;
		this.button = button;
	}
	
	/**
	 * Tells the tank drive to go straight at 1/4 power at the current 
	 * yaw and enable the scaler
	 */
	@Override
	public void initialize()
	{
		scaler.enable();
	}
	
	/** 
	 * Poll the button and change the state of the scaler based on input
	 */
	@Override
	protected void execute() 
	{
		final boolean PRESSED = button.get();
		
		if(PRESSED)
			scaler.enable();
		else
			scaler.disable();
	}
	
	/**
	 * Disables the scaler and drive PID and zeros drive outputs
	 */
	@Override
	protected void end()
	{
		scaler.disable();
	}
}
