package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class HoldCurrentYaw extends GoToAngle
{
	
	public HoldCurrentYaw(TankDrive drive)
	{
		super(drive.getHeading(), drive);
	}	
}