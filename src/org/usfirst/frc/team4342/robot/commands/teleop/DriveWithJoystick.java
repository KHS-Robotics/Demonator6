package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command
{	
	private Joystick leftJoystick, rightJoystick;
	private TankDrive drive;
	
	private boolean flipOrientation;
	
	public DriveWithJoystick(Joystick leftJoystick, Joystick rightJoystick, TankDrive drive)
	{
		super();
		
		this.requires(drive);
		
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;
		this.drive = drive;

	}
	
	@Override
	protected void execute()
	{
		final double LEFT_Y = leftJoystick.getY();
		final double RIGHT_Y = rightJoystick.getY();
		final boolean SHIFT = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.SHIFT);
		
		if(SHIFT)
			drive.shift();
		
		flipOrientation = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.FLIP_ORIENTATION) ? !flipOrientation : flipOrientation;
		if(flipOrientation)
		{
			drive.set(adjust(LEFT_Y), adjust(RIGHT_Y));
			return;
		}
		
		drive.set(adjust(-LEFT_Y), adjust(-RIGHT_Y));
	}
	
	@Override
	protected void end()
	{
		drive.set(0, 0);
		drive.disablePID();
	}
	
	@Override
	public void interrupted()
	{
		this.end();
	}
	
	@Override
	protected boolean isFinished() 
	{
		return false;
	}
	
	private static double adjust(double input)
	{
		final double SENSITIVITY = 0.60;
		return (SENSITIVITY * Math.pow(input, 3)+ (1 - SENSITIVITY) * input);
	}
}
