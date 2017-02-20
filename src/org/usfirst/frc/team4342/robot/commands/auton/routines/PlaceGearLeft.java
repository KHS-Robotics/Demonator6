package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.AlignHook;
import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.commands.auton.PlaceGear;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous routine to place a gear on left peg, which is just
 * a collection of auton commands called a Command Group. This
 * command group calls the following commands, respectively:
 * 
 * <ol>
 * <li>{@link org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance}</li>
 * <li>{@link org.usfirst.frc.team4342.robot.commands.auton.AlignHook}</li>
 * <li>{@link org.usfirst.frc.team4342.robot.commands.auton.PlaceGear}</li>
 * </ol>
 * 
 * @see edu.wpi.first.wpilibj.command.CommandGroup
 */
public class PlaceGearLeft extends CommandGroup
{
	// Step 1
	private static final double START_YAW = 0;
	private static final double DISTANCE = 76;
	private static final double DIRECTION = 0.33;
	
	// Step 2
	private static final AlignHook.Location location = AlignHook.Location.LEFT;
	
	/**
	 * Creates a new autonomous routine to place a gear on the left peg
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param placer the <code>GearPlacer</code> subsystem
	 */
	public PlaceGearLeft(TankDrive drive, GearPlacer placer)
	{
		super(PlaceGearLeft.class.getName());
		
		this.addSequential(new GoStraightDistance(DIRECTION, START_YAW, DISTANCE, drive));
		this.addSequential(new AlignHook(drive, location));
		this.addSequential(new PlaceGear(placer));
	}
}
