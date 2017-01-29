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
	private AlignHook align;
	
	private static boolean shouldSetTargetHeading = true;
	private static double targetHeading;
	
	public DriveWithJoystick(Joystick joystick, TankDrive drive)
	{
		super();
		
		this.requires(drive);
		
		this.joystick = joystick;
		this.drive = drive;
		
		align = new AlignHook(drive);
	}
	
	@Override
	protected void execute()
	{
		SmartDashboard.putString("HookState-", align.getState());
		
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
		
		if(joystick.getRawButton(2))
		{
//			if(drive.getRightSensor() || drive.getLeftSensor())
//			{
//				targetHeading = drive.getYaw();
//			}
//			
//			drive.setHeading(targetHeading);
			
			align.start();
		}
		
		// EXTREMELY TEMPORARY!!
		if(joystick.getRawButton(12)) {
			shouldSetTargetHeading = true;
			targetHeading = 0;
			drive.resetNavx();
		}
		
		SmartDashboard.putBoolean("Drive-PID-Enabled", drive.pidEnabled());
		SmartDashboard.putBoolean("shouldSetTargetHeading", shouldSetTargetHeading);
		SmartDashboard.putNumber("targetHeading", targetHeading);
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
