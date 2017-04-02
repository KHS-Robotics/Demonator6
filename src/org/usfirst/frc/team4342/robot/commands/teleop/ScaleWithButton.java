package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Command to scale the airship
 */
public class ScaleWithButton extends TeleopCommand
{
	private Scaler scaler;
	private JoystickButton button;
	
	/**
	 * Creates a new <code>Scale</code> command.
	 * @param scaler the <code>Scaler</code> subsystem
	 * @param button the joystick button to poll
	 * @see org.usfirst.frc.team4342.robot.subsystems.Scaler
	 */
	public ScaleWithButton(Scaler scaler, JoystickButton button)
	{
		this.requires(scaler);
		
		this.scaler = scaler;
		this.button = button;
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
	 * Disables the scaler
	 */
	@Override
	protected void end()
	{
		scaler.disable();
	}
	
	/** {@inheritDoc} */
	@Override
	public void initialize() {}
}
