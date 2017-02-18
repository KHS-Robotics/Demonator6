package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraight;
import org.usfirst.frc.team4342.robot.commands.auton.PlaceGear;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous routine to place a gear on right peg, which is just
 * a collection of auton commands called a Command Group. This
 * command group calls the following commands, respectively:
 * <ol>
 * <li>{@link org.usfirst.frc.team4342.robot.commands.auton.GoStraight}</li>
 * <li>Align Hook Right</li>
 * <li>{@link org.usfirst.frc.team4342.robot.commands.auton.PlaceGear}</li>
 * </ol>
 * 
 * @see edu.wpi.first.wpilibj.command.CommandGroup
 */
public class PlaceGearRight extends CommandGroup
{
	private static final double START_YAW = 0;
	private static final double DISTANCE = 10;
	private static final double DIRECTION = 0.33;
	
	/**
	 * Creates a new autonomous routine to place a gear on the right peg
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param placer the <code>GearPlacer</code> subsystem
	 */
	public PlaceGearRight(TankDrive drive, GearPlacer placer)
	{
		super(PlaceGearRight.class.getName());;
		
		this.addSequential(new GoStraight(DIRECTION, START_YAW, DISTANCE, drive));
		//this.addSequential(new AlignHookRight(drive));
		this.addSequential(new PlaceGear(placer));
	}
}