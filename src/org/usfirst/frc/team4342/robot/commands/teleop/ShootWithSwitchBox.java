package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Teleop command to control the shooter.
 */
public class ShootWithSwitchBox extends TeleopCommand
{
	private Joystick switchBox;
	private Shooter shooter;
	
	/**
	 * Creates a new <code>ShootWithSwitchBox</code> command.
	 * @param switchBox the switch box to poll the inputs from
	 * @param shooter the <code>Shooter</code> subsystem
	 * @see org.usfirst.frc.team4342.robot.subsystems.Shooter
	 */
	public ShootWithSwitchBox(Joystick switchBox, Shooter shooter)
	{
		super();
		
		this.requires(shooter);
		
		this.switchBox = switchBox;
		this.shooter = shooter;
	}
	
	/**
	 * Main logic to run the shooter in teleop. The method does the following in
	 * the specified order:
	 * 
	 * <ol>
	 * <li>Polls the button inputs</li>
	 * <li>Starts or stops the accumulator depending on the specified switch position when polled</li>
	 * <li>Starts or stops the agitator depending on the specified switch position when polled</li>
	 * <li>Starts or stops the shooter and aims farther or closer depending on the specified switch position when polled</li>
	 * </ol>
	 */
	@Override
	protected void execute()
	{
		final boolean ACCUMULATE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.ACCUMULATE);
		final boolean AGITATE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.AGITATE);
		final boolean SHOOT_CLOSE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.SHOOT_CLOSE);
		final boolean SHOOT_FAR = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.SHOOT_FAR);
	
		if (ACCUMULATE) 
			shooter.accumulate();
		else
			shooter.stopAccumulating();
		
		if (AGITATE)
			shooter.agitate();
		else
			shooter.stopAgitating();
	
		if (SHOOT_CLOSE)
			shooter.shootClose();
		else if (SHOOT_FAR)
			shooter.shootFar();
		else
			shooter.stopShooting();
	}
	
	/**
	 * Stops the accumulator, agitator and shooter
	 */
	@Override
	protected void end()
	{
		shooter.stopAccumulating();
		shooter.stopAgitating();
		shooter.stopShooting();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void initialize() {}
}
