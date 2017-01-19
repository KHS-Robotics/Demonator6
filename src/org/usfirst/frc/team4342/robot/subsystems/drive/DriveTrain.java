package org.usfirst.frc.team4342.robot.subsystems.drive;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.PIDOutput;

public class DriveTrain implements PIDOutput
{
	private double direction;

	public void setDirection(double direction)
	{
		this.direction = direction;
	}
	
	@Override
	public void pidWrite(double output)
	{
		IO.Drive.set(output, direction);
	}
}
