package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShiftHighAndHoldCurrentYaw extends CommandGroup 
{
	public ShiftHighAndHoldCurrentYaw(Joystick joystick, TankDrive drive)
	{
		this.addSequential(new ShiftHigh(drive));
		this.addParallel(new HoldCurrentYaw(joystick, drive));
	}
}