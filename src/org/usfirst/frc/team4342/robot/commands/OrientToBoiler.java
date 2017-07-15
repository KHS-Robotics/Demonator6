package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Auton command to orient the robot to the boiler using 
 * the calculated adjusted yaw using a Raspberry Pi 3
 * and a USB Camera
 */
public class OrientToBoiler extends CommandBase 
{
	private TankDrive drive;
	private double offset;
	
	/**
	 * Creates a new <code>OrientToBoiler</code> command
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param offset number of degrees to overshoot or undershoot
	 */
	public OrientToBoiler(TankDrive drive, double offset)
	{
		super(3);
		
		this.requires(drive);
		
		this.drive = drive;
		this.offset = offset;
	}

	/**
	 * Gets the calculated boiler yaw from the pi and sets
	 * the robot's heading to it. If the boiler yaw is not
	 * in the SmartDashboard it keeps its current heading
	 */
	@Override
	protected void initialize() 
	{
		double boilerYaw = SmartDashboard.getNumber("Boiler-Yaw", drive.getHeading()) + offset;
		drive.setHeading(boilerYaw);
	}
	
	/**
	 * Stops the drive motors
	 */
	@Override
	protected void end() 
	{
		drive.disablePID();
		drive.set(0, 0);
	}
	
	/**
	 * Gets if the robot is oriented towards the boiler
	 * @return true if properly oriented, false otherwise
	 */
	@Override
	protected boolean isFinished() 
	{
		return drive.onTarget() || this.isTimedOut();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void execute() {}
}
