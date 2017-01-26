package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveWithJoystick extends Command
{
	private static final double JOYSTICK_DEADBAND = 0.07;
	
	private Joystick joystick;
	private TankDrive drive;
	
	public DriveWithJoystick(Joystick joystick, TankDrive drive)
	{
		super();
		
		this.requires(drive);
		
		this.joystick = joystick;
		this.drive = drive;
	}
	
	@Override
	protected void execute()
	{
		final double JOYSTICK_X = -joystick.getX();
		final double JOYSTICK_Y = -joystick.getY();
		
		if(Math.abs(JOYSTICK_X) > JOYSTICK_DEADBAND || Math.abs(JOYSTICK_Y) > JOYSTICK_DEADBAND) {
			drive.disablePID();
			drive.set(adjust(JOYSTICK_X), adjust(JOYSTICK_Y));
		}
		else if(!drive.pidEnabled() && (Math.abs(JOYSTICK_X) < JOYSTICK_DEADBAND || Math.abs(JOYSTICK_Y) < JOYSTICK_DEADBAND)) {
			drive.set(0, 0);
		}
		
		if(joystick.getRawButton(7)) {
			drive.enablePID();
			drive.setHeading(0);
		}
		
		// EXTREMELY TEMPORARY!!
		if(joystick.getRawButton(12)) {
			drive.resetNavx();
		}
		
		SmartDashboard.putBoolean("Drive-PID-Enabled", drive.pidEnabled());
		
		drive.setPID(
			-SmartDashboard.getNumber("Drive-P", 0.0), 
			-SmartDashboard.getNumber("Drive-I", 0.0),
			-SmartDashboard.getNumber("Drive-D", 0.0)
		);
	}
	
	@Override
	protected void end()
	{
		drive.disablePID();
		drive.set(0, 0);
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
		final double SENSITIVITY = 0.67;
		return (SENSITIVITY * Math.pow(input, 3) + (1 - SENSITIVITY) * input);
	}
}
