package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Teleop command to place gears on the pegs for the airship.
 * 
 * @see org.usfirst.frc.team4342.robot.commands.teleop.TeleopCommand
 */
public class PlaceGearWithSwitchBox extends TeleopCommand
{	
	private Joystick switchBox;
	private GearPlacer placer;
	
	/**
	 * Creates a new <code>PlaceGearWithSwitchBox</code> command.
	 * @param switchBox the switch box to poll inputs from
	 * @param placer the <code>GearPlacer</code> subsystem
	 * @see org.usfirst.frc.team4342.robot.GearPlacer
	 */
	public PlaceGearWithSwitchBox(Joystick switchBox, GearPlacer placer)
	{
		super();
		
		this.requires(placer);
		
		this.switchBox = switchBox;
		this.placer = placer;
	}
	
	/**
	 * The main logic to use the gear placer to teleop. The method polls a single switch
	 * on the switch box to set the gear placer to lower or raised. If the switch is up
	 * then the gear placer to lowered to place a gear on the peg, otherwise the gear
	 * placer is raised up
	 */
	@Override
	protected void execute()
	{
		final boolean USER_LOWER = switchBox.getRawButton(ButtonMap.SwitchBox.GearPlacer.LOWER);
		
		if(USER_LOWER)
			placer.lower();
		else
			placer.raise();	
	}
	
	/** {@inheritDoc} */
	@Override
	protected void initialize() {}
	
	/** {@inheritDoc} */
	@Override
	protected void end() {}
}
