package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShiftAndHoldCurrentYaw extends CommandGroup 
{
	public ShiftAndHoldCurrentYaw(TankDrive drive)
	{
		this.addParallel(new Shift(drive));
		this.addParallel(new HoldCurrentYaw(drive));
	}
}