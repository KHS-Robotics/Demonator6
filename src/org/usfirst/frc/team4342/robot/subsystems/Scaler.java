package org.usfirst.frc.team4342.robot.subsystems;

import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.commands.teleop.Scale;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Scaler extends Subsystem
{
	private CANTalon motor;
	private DigitalInput limitSwitch;
	
	private boolean enabled;
	
	public Scaler(CANTalon motor, DigitalInput limitSwitch)
	{
		super();
		
		this.motor = motor;
		this.limitSwitch = limitSwitch;
	}
	
	public void enable()
	{
		if(enabled)
			return;
		enabled = true;
		
		motor.set(0.5);
	}
	
	public void disable()
	{
		if(!enabled)
			return;
		enabled = false;
		
		motor.set(0);
	}
	
	public boolean hasScaled()
	{	
		return limitSwitch.get();
	}

	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(new Scale(this, IO.getDrive()));
	}
}
