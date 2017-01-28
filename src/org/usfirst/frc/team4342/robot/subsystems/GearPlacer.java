package org.usfirst.frc.team4342.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearPlacer extends Subsystem
{
	private static final Value LOWER = Value.kForward, RAISE = Value.kReverse;
	private DoubleSolenoid placer;
	
	private Value currentValue;
	
	public GearPlacer(DoubleSolenoid placer)
	{
		super();
		
		this.placer = placer;
	}
	
	public void lower()
	{
		if(currentValue == LOWER)
			return;
		
		placer.set(LOWER);
		
		currentValue = LOWER;	
	}
	
	public void raise()
	{
		if(currentValue == RAISE)
			return;
		
		placer.set(RAISE);
		
		currentValue = RAISE;	
	}
	
	public boolean isLowered()
	{
		return currentValue.equals(LOWER);
	}
	
	public boolean isRaised()
	{
		return currentValue.equals(RAISE);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(null);
		//this.setDefaultCommand(new PlaceGearWithSwitchBox(IO.getSwitchBox(), this));
	}

}
