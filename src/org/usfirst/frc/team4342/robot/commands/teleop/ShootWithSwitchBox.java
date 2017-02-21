package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Teleop command to control the shooter.
 */
public class ShootWithSwitchBox extends TeleopCommand
{
	private Joystick switchBox;
	private JoystickButton extraAccumButton;
	private Shooter shooter;
	
	/**
	 * Creates a new <code>ShootWithSwitchBox</code> command.
	 * @param switchBox the switch box to poll the inputs from
	 * @param extraAccumButton a button that will also accumulate if pressed
	 * in addition to a switch box switch
	 * @param shooter the <code>Shooter</code> subsystem
	 * @see org.usfirst.frc.team4342.robot.subsystems.Shooter
	 */
	public ShootWithSwitchBox(Joystick switchBox, JoystickButton extraAccumButton, Shooter shooter)
	{
		this.requires(shooter);
		
		this.switchBox = switchBox;
		this.extraAccumButton = extraAccumButton;
		this.shooter = shooter;
	}
	
	/** 
	 * {@inheritDoc} 
	 * 
	 */
	@Override
	protected void initialize() 
	{
		SmartDashboard.putNumber("Shooter-Setpoint", 0.0);
	}
	
	/**
	 * Main logic to run the shooter in teleop.
	 */
	@Override
	protected void execute()
	{
		final boolean ACCUMULATE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.ACCUMULATE) || extraAccumButton.get();
		final boolean AGITATE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.AGITATE);
		final boolean SHOOT_FAR = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.SHOOT_FAR);
		final boolean SHOOT_CLOSE = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.SHOOT_CLOSE);
		final boolean CAM_LIGHT = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.CAM_LIGHT);
		final boolean MANUAL_SETPOINT = switchBox.getRawButton(ButtonMap.SwitchBox.Shooter.MANUAL_SETPOINT);

	
		if (ACCUMULATE) 
			shooter.accumulate();
		else
			shooter.stopAccumulating();
		
		if (AGITATE)
			shooter.agitate();
		else
			shooter.stopAgitating();
	
		if(MANUAL_SETPOINT)
			shooter.setShooter((int) SmartDashboard.getNumber("Shooter-Setpoint", 0.0));
		else if(SHOOT_FAR && SHOOT_CLOSE)
			shooter.stopShooting();
		else if (SHOOT_FAR)
			shooter.shootFar();
		else if (SHOOT_CLOSE)
			shooter.shootClose();
		else
			shooter.stopShooting();
		
		shooter.setCamLight(CAM_LIGHT);
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
}
