package org.usfirst.frc.team4342.robot.subsystems;

import com.ctre.CANTalon;

public class Accumulator extends SubsystemBase
{
	private boolean enabled;
	
	private CANTalon motor;
	
	public Accumulator(CANTalon motor)
	{
		this.motor = motor;
	}
	
	public void enable()
	{
		if(enabled)
			return;
		enabled = true;
		
		motor.set(-1);
	}
	
	public void disable()
	{
		if(!enabled)
			return;
		enabled = false;
		
		motor.set(0);
	}
	
	public void toggle()
	{
		if(enabled)
			disable();
		else
			enable();
	}
}
