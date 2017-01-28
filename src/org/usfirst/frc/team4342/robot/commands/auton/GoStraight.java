package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class GoStraight extends Command
{
	private double direction, yaw, leftVal, rightVal, distance;
	private TankDrive drive;
	
	public GoStraight(double direction, double yaw, double distance, TankDrive drive)
	{
		this.requires(drive);
		
		this.drive = drive;
		this.yaw = yaw;
		this.distance = distance;
	}
	
	@Override
	public void initialize()
	{
		leftVal = drive.getLeftDistance();
		rightVal = drive.getRightDistance();
		drive.enablePID();
		drive.goStraight(direction, yaw);
	}
	
	@Override
	protected void execute()
	{
		
	}
	
	@Override
	public void end()
	{
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
		final double LEFT_VAL = drive.getLeftDistance();
		final double RIGHT_VAL = drive.getRightDistance();
		
		final double TOTAL = Math.abs(LEFT_VAL - leftVal) + Math.abs(RIGHT_VAL - rightVal);
		
		return (TOTAL >= distance);
	}
	
}
