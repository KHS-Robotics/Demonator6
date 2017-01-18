package org.usfirst.frc.team4342.robot.subsystems.drive;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem implements PIDOutput
{
	private double direction;
	
	public DriveTrain()
	{
		super();
	}
	
	public void setDirection(double direction)
	{
		this.direction = direction;
	}

	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(new DriveWithJoystick(IO.Drive.getDriveTrain()));
	}
	
	@Override
	public void pidWrite(double output)
	{
		IO.Drive.set(output, direction);
	}
}
