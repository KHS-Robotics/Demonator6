package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CrossBaseline extends CommandGroup 
{
	private static final int DISTANCE = 112;
	
	public CrossBaseline(TankDrive drive)
	{
		this.addSequential(new GoStraightDistance(0.75, drive.getHeading(), DISTANCE, drive));
	}
}
