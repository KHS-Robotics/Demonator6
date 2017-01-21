package org.usfirst.frc.team4342.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearPlacer extends Subsystem
{
	private DoubleSolenoid placer;
	
	private Value currentValue;
	
	public GearPlacer(DoubleSolenoid placer)
	{
		super();
		
		this.placer = placer;
	}
	
	public void lower()
	{
		if(currentValue == Value.kForward)
			return;
		
		currentValue = Value.kForward;
			
		placer.set(Value.kForward);
	}
	
	public void raise()
	{
		if(currentValue == Value.kReverse)
			return;
		
		currentValue = Value.kReverse;
		
		placer.set(Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(null);
		//this.setDefaultCommand(new PlaceGearWithSwitchBox(IO.getSwitchBox(), this));
	}

}
