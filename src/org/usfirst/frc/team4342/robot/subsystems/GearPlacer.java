package org.usfirst.frc.team4342.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearPlacer extends Subsystem
{
	private static final Value LOWER = Value.kForward, RAISE = Value.kReverse;
	
	private DoubleSolenoid placer;
	private DigitalInput limitSwitch;
	
	public GearPlacer(DoubleSolenoid placer, DigitalInput ls)
	{
		super();
		
		this.placer = placer;
		limitSwitch = ls;
	}
	
	public void lower()
	{	
		placer.set(LOWER);	
	}
	
	public void raise()
	{	
		placer.set(RAISE);	
	}
	
	public boolean isLowered()
	{
		return limitSwitch.get();
	}
	
	public boolean isRaised()
	{
		return !isLowered();
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(null);
		//this.setDefaultCommand(new PlaceGearWithSwitchBox(IO.getSwitchBox(), this));
	}

}
