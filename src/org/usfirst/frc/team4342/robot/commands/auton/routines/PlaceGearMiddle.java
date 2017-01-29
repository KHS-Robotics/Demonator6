package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraight;
import org.usfirst.frc.team4342.robot.commands.auton.PlaceGear;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearMiddle extends CommandGroup
{
	private static final double DIRECTION = 0.33;
	private static final int YAW = 0;
	private static final int DISTANCE = 10;
	
	public PlaceGearMiddle(TankDrive drive, GearPlacer placer)
	{
		this.addSequential(new GoStraight(DIRECTION, YAW, DISTANCE, drive));
		this.addSequential(new PlaceGear(placer));
	}
}
