package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraight;
import org.usfirst.frc.team4342.robot.commands.auton.PlaceGear;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous routine to place a gear on middle peg, which is just
 * a collection of auton commands called a Command Group. This
 * command group calls the following commands, respectively:
 * 
 * <ol>
 * <li>{@link org.usfirst.frc.team4342.robot.commands.auton.GoStraight}</li>
 * <li>{@link org.usfirst.frc.team4342.robot.commands.auton.PlaceGear}</li>
 * </ol>
 * 
 * @see edu.wpi.first.wpilibj.command.CommandGroup
 */
public class PlaceGearMiddle extends CommandGroup
{
	private static final double DIRECTION = 0.33;
	private static final int YAW = 0;
	private static final int DISTANCE = 10;
	
	/**
	 * Creates a new autonomous routine to place a gear on the middle peg
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param placer the <code>GearPlacer</code> subsystem
	 */
	public PlaceGearMiddle(TankDrive drive, GearPlacer placer)
	{
		super(PlaceGearMiddle.class.getName());
		
		this.addSequential(new GoStraight(DIRECTION, YAW, DISTANCE, drive));
		this.addSequential(new PlaceGear(placer));
	}
}
