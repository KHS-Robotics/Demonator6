package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

/**
 * Command to go straight a fixed distance. The command utilizes
 * the <code>TankDrive</code> subsystem to go straight (forward or
 * backward) at an inputed yaw for a certain distance.
 * 
 * @see org.usfirst.frc.team4342.robot.commands.auton.AutonomousCommand
 */
public class GoStraightDistance extends CommandBase
{
	private double direction, yaw, leftVal, rightVal, distance;
	private TankDrive drive;
	
	/**
	 * Creates a new <code>GoStraight</code> command.
	 * @param direction the forward or backward direction ranging from -1.0 to 1.0
	 * @param yaw the yaw (angle from -180 to 180) to hold while going straight
	 * @param distance the fixed distance to straight (in feet)
	 * @param drive the <code>TankDrive</code> subsystem to output to
	 */
	public GoStraightDistance(double direction, double yaw, double distance, TankDrive drive)
	{
		this.requires(drive);
		
		this.direction = direction;
		this.drive = drive;
		this.yaw = yaw;
		this.distance = distance;
	}
	
	/**
	 * Initializes this command. We snapshot the left and right encoder distances
	 * so we know where our "zero" mark is to start measuring from. Then we enable
	 * PID and go straight using the specified direction and yaw.
	 */
	@Override
	public void initialize()
	{
		drive.shiftLow();
		leftVal = Math.abs(drive.getLeftDistance());
		rightVal = Math.abs(drive.getRightDistance());
		drive.goStraight(direction, yaw);
	}
	
	/**
	 * Disabled the drive PID and zero outputs
	 */
	@Override
	public void end()
	{
		drive.disablePID();
		drive.set(0, 0);
	}

	/**
	 * Returns if the command is finished. The command is finished when
	 * the robot moves the fixed distance.
	 * @return true if the robot has moved the distance, false otherwise
	 */
	@Override
	protected boolean isFinished() 
	{
		return drive.remainingDistance(distance, leftVal, rightVal) <= 0;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void execute() {}
}
