package org.usfirst.frc.team4342.robot.subsystems;

import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.commands.teleop.PlaceGearWithSwitchBox;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Gear Placer subsystem to place gears on pegs for the airship
 */
public class GearPlacer extends DemonSubsystem
{
	private static final Value LOWER = Value.kReverse, RAISE = Value.kForward;
	
	private Value current;
	
	private DoubleSolenoid placer;
	private DigitalInput limitSwitch;
	
	/**
	 * Creates a new <code>GearPlacer</code> subsystem.
	 * @param placer the double solenoid to raise and lower the gear placer
	 * @param ls the limit switch to determine if the gear placer is aligned to the peg
	 */
	public GearPlacer(DoubleSolenoid placer, DigitalInput ls)
	{
		this.placer = placer;
		limitSwitch = ls;
		
		this.raise();
	}
	
	/**
	 * Lowers the gear placer. Effectively sets the <code>DoubleSolenoid</code> to kFORWARD
	 */
	public void lower()
	{
		if(LOWER.equals(current))
			return;
		
		placer.set(LOWER);	
		current = LOWER;
	}
	
	/**
	 * Raises the gear placer. Effectively sets the <code>DoubleSolenoid</code> to kREVERSE
	 */
	public void raise()
	{	
		if(RAISE.equals(current))
			return;
		
		placer.set(RAISE);	
		current = RAISE;
	}
	
	/**
	 * <p>Sets the default command to <code>PlaceGearWithSwitchBox</code>
	 * 
	 * {@inheritDoc}
	 * @see PlaceGearWithSwitchBox
	 */
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(new PlaceGearWithSwitchBox(IO.getSwitchBox(), IO.getGearPlacer()));
	}
	
	/**
	 * Gets if the gear placer is aligned properly with the peg
	 * @return true if the gear placer is aligned, false otherwise
	 * @deprecated the mount for this caused more harm than good,
	 * you're on your own
	 */
	@Deprecated
	public boolean isInPeg()
	{
		return limitSwitch.get();
	}
}
