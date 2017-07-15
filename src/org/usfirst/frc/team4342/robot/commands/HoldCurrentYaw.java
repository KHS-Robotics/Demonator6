package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class HoldCurrentYaw extends Command
{
	private double desiredYaw;
	
	public HoldCurrentYaw(TankDrive drive)
	{
		desiredYaw = drive.getHeading();
		drive.setHeading(desiredYaw);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}