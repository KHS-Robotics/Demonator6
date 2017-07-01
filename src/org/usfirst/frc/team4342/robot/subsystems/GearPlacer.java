package org.usfirst.frc.team4342.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Gear Placer subsystem to place gears on pegs for the airship
 */
public class GearPlacer extends SubsystemBase
{
	private static final Value LOWER = Value.kReverse, RAISE = Value.kForward;
	
	private Value current;
	
	private DoubleSolenoid placer;
	
	/**
	 * Creates a new <code>GearPlacer</code> subsystem.
	 * @param placer the double solenoid to raise and lower the gear placer
	 * @param ls the limit switch to determine if the gear placer is aligned to the peg
	 */
	public GearPlacer(DoubleSolenoid placer)
	{
		this.placer = placer;
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
}
