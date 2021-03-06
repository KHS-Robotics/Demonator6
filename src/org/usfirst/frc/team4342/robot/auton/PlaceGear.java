package org.usfirst.frc.team4342.robot.auton;

import org.usfirst.frc.team4342.robot.commands.AlignPeg;
import org.usfirst.frc.team4342.robot.commands.GoStraightDistance;
import org.usfirst.frc.team4342.robot.commands.GoStraightUntilWithinDistance;
import org.usfirst.frc.team4342.robot.commands.GoToAngle;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

/**
 * Autonomous routine to place a gear on a peg.
 * 
 * @see AutonomousRoutine
 */
public class PlaceGear extends AutonomousRoutine
{
	private static final double PLACE_PEG_DISTANCE_INCHES = 5.0;
	
	// Step 1
	private static final double START_YAW = 0;
	private static final double DISTANCE = 56; 
	private static final double DIRECTION = 0.67;
	private static final double LEFT_DISTANCE = 69;
	private static final double RIGHT_DISTANCE = 69;
	private static final double PEG_DISTANCE = 56;
	
	// Step 2
	private static final double PEG_YAW = 60;
	
	/**
	 * Creates a new autonomous routine to place a gear on a peg
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param placer the <code>GearPlacer</code> subsystem
	 * @param location the location of the peg
	 * @see AlignPeg.Location
	 */
	public PlaceGear(TankDrive drive, GearPlacer placer, AlignPeg.Location location)
	{
		if(AlignPeg.Location.MIDDLE.equals(location))
		{
			this.addSequential(new GoStraightDistance(DIRECTION, START_YAW, DISTANCE, drive));
			this.addSequential(new GoStraightUntilWithinDistance(drive, PLACE_PEG_DISTANCE_INCHES));
		}
		else if(AlignPeg.Location.LEFT.equals(location))
		{
			this.addSequential(new GoStraightDistance(DIRECTION, START_YAW, LEFT_DISTANCE, drive));
			this.addSequential(new GoToAngle(-PEG_YAW, drive));
			this.addSequential(new GoStraightDistance(DIRECTION, -PEG_YAW, PEG_DISTANCE, drive));
			this.addSequential(new GoStraightUntilWithinDistance(drive, PLACE_PEG_DISTANCE_INCHES));
		}
		else if(AlignPeg.Location.RIGHT.equals(location))
		{
			this.addSequential(new GoStraightDistance(DIRECTION, START_YAW, RIGHT_DISTANCE, drive));
			this.addSequential(new GoToAngle(PEG_YAW, drive));
			this.addSequential(new GoStraightDistance(DIRECTION, PEG_YAW, PEG_DISTANCE, drive));
			this.addSequential(new GoStraightUntilWithinDistance(drive, PLACE_PEG_DISTANCE_INCHES));
		}
	}
}
