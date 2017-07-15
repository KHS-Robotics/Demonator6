package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

public class GoToLoadInYaw extends CommandBase
{
	private TankDrive drive;
	
	private static final double LOAD_IN_YAW = 28.0;
	private double desiredYaw;
	
	public GoToLoadInYaw(TankDrive drive)
	{
		this.requires(drive);
		
		this.drive = drive;
	}

	@Override
	protected void initialize() 
	{
		if(this.isBlueAlliance())
			desiredYaw = LOAD_IN_YAW;
		else if(this.isRedAlliance())
			desiredYaw = -LOAD_IN_YAW;
		else
			desiredYaw = 0;
		
		drive.setHeading(desiredYaw);
	}

	@Override
	protected void end() 
	{
		drive.disablePID();
		drive.set(0.0, 0.0);
	}

	@Override
	protected boolean isFinished() 
	{
		return drive.onTarget() || this.isTimedOut();
	}
	
	@Override
	protected void execute() {}
}
