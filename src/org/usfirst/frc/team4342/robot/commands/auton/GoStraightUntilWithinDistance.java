package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

/**
 * Command to go straight until the ultrasonic reads a distance
 * that is less than or equal to the desired distance
 */
public class GoStraightUntilWithinDistance extends AutonomousCommand 
{
	private TankDrive drive;
	private double desiredDistance;
	
	/**
	 * Creates a new command to go straight until the robot is a certain distance from an object
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param desiredDistance the desired distance in inches
	 */
	public GoStraightUntilWithinDistance(TankDrive drive, double desiredDistance)
	{
		this.requires(drive);
		
		this.drive = drive;
		this.desiredDistance = desiredDistance;
	}

	/**
	 * Tells the robot to go straight at half power at the current heading
	 */
	@Override
	protected void initialize() 
	{
		drive.shiftLow();
		drive.goStraight(0.5, drive.getHeading());
	}

	/**
	 * Disabled the drive PID
	 */
	@Override
	protected void end() 
	{
		drive.disablePID();
		drive.set(0, 0);
	}

	/**
	 * Gets if the robot is within the desired distance
	 * @return true if the robot is within the desired distance, false otherwise
	 */
	@Override
	protected boolean isFinished() 
	{
		return drive.getUltrasonicDistance() <= desiredDistance;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void execute() {}
}
