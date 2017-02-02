package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraight;
import org.usfirst.frc.team4342.robot.commands.auton.PlaceGear;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearLeft extends CommandGroup
{
	private static final double START_YAW = 0;
	private static final double DISTANCE = 10;
	private static final double DIRECTION = 0.33;
	
	public PlaceGearLeft(TankDrive drive, GearPlacer placer)
	{
		this.addSequential(new GoStraight(DIRECTION, START_YAW, DISTANCE, drive));
		//this.addSequential(new AlignHook())
		this.addSequential(new PlaceGear(placer));
	}
}
