package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.AlignHook;
import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.commands.auton.GoStraightUntilWithinDistance;
import org.usfirst.frc.team4342.robot.commands.auton.GoToAngle;
import org.usfirst.frc.team4342.robot.commands.auton.TurnUntilSeePeg;
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
	private static final double DISTANCE = 43.8; 
	private static final double DIRECTION = 0.67;
	
	// Step 2
	private static final double PEG_YAW = 60;
	
	/**
	 * Creates a new autonomous routine to place a gear on a peg
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param placer the <code>GearPlacer</code> subsystem
	 * @param location the location of the peg
	 * @see AlignHook.Location
	 */
	public PlaceGear(TankDrive drive, GearPlacer placer, AlignHook.Location location)
	{
		if(AlignHook.Location.MIDDLE.equals(location))
		{
			this.addSequential(new GoStraightDistance(DIRECTION, START_YAW, DISTANCE, drive));
			this.addSequential(new GoStraightUntilWithinDistance(drive, PLACE_PEG_DISTANCE_INCHES));
		}
		else
		{
			this.addSequential(new GoStraightDistance(DIRECTION, START_YAW, DISTANCE, drive));
		}
		
		if (this.isUsingDeadReckoning() && AlignHook.Location.RIGHT.equals(location))
			this.addSequential(new GoToAngle(-PEG_YAW, drive));
		else if (this.isUsingDeadReckoning() && AlignHook.Location.LEFT.equals(location))
			this.addSequential(new GoToAngle(PEG_YAW, drive));
		else if(this.isUsingTurnUntilSeePeg() && !AlignHook.Location.MIDDLE.equals(location))
			this.addSequential(new TurnUntilSeePeg(10, location, drive));
		else if(this.isUsingAlignHook() && !AlignHook.Location.MIDDLE.equals(location))
			this.addSequential(new AlignHook(drive, placer, location));
		
		if(this.isUsingDeadReckoning() && !AlignHook.Location.MIDDLE.equals(location))
		{
			this.addSequential(new GoStraightDistance(DIRECTION, PEG_YAW, DISTANCE, drive));
			this.addSequential(new GoStraightUntilWithinDistance(drive, PLACE_PEG_DISTANCE_INCHES));
		}
		else if(this.isUsingTurnUntilSeePeg() && !AlignHook.Location.MIDDLE.equals(location))
		{
			this.addSequential(new GoStraightUntilWithinDistance(drive, PLACE_PEG_DISTANCE_INCHES));
		}
	}
}
