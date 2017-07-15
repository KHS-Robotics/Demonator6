package org.usfirst.frc.team4342.robot.commands;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

public class GoToLoadInYaw extends GoToAngle
{
	private static final double LOAD_IN_YAW = 28.0;
	
	public GoToLoadInYaw(TankDrive drive)
	{
		super(LOAD_IN_YAW, drive);
		
		double loadInYaw = 0.0;
		if(this.isBlueAlliance())
			loadInYaw = LOAD_IN_YAW;
		else if(this.isRedAlliance())
			loadInYaw = -LOAD_IN_YAW;
		else
			loadInYaw = 0;
		
		this.yaw = loadInYaw;
	}
}
