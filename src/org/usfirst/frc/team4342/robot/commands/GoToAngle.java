package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

/**
 * Command to orient the robot to a certain yaw
 * 
 * @see org.usfirst.frc.team4342.robot.commands.auton.AutonomousCommand
 */
public class GoToAngle extends CommandBase
{
	private double yaw;
	private TankDrive drive;
	
	/**
	 * Creates a new <code>GoToAngle</code> command.
	 * @param yaw the yaw the robot should orient to from -180 to 180
	 * @param drive the <code>TankDrive</code> subsystem to output to
	 */
	public GoToAngle(double yaw, TankDrive drive)
	{
		this.requires(drive);
		
		this.yaw = yaw;
		this.drive = drive;
	}
	
	/**
	 * Enables the drive PID and sets the setpoint to
	 * the specified yaw
	 */
	@Override
	protected void initialize()
	{
		drive.shiftLow();
		drive.enablePID();
		drive.setHeading(yaw);
	}
	
	/**
	 * Disabled the drive PID and zeros the outputs
	 */
	@Override
	protected void end()
	{
		drive.disablePID();
		drive.set(0, 0);
	}
	
	/**
	 * Returns if the robot is oriented at the specified yaw
	 * within the three degree tolerance range
	 * @return true if the robot is oriented at the specified yaw, false otherwise
	 */
	@Override
	protected boolean isFinished() 
	{
		return (Math.abs(drive.getHeading() - yaw) <= 1);
	}
	
	/** {@inheritDoc} */
	@Override
	protected void execute() {}
}
