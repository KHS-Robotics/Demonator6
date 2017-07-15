package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AimBoiler extends CommandBase
{
	private TankDrive drive;
	
	private static final double BOILER_YAW = 135.0;
	private double desiredYaw;
	
	public AimBoiler(TankDrive drive)
	{
		this.requires(drive);
			
		this.drive = drive;
	}

	@Override
	protected void initialize() 
	{
		if(this.isBlueAlliance())
			desiredYaw = -BOILER_YAW;
		else if(this.isRedAlliance())
			desiredYaw = BOILER_YAW;
		else
			desiredYaw = BOILER_YAW + 45;
		
		desiredYaw = SmartDashboard.getNumber("Boiler-Yaw", desiredYaw);
		
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
