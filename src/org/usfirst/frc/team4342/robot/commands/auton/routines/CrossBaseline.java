package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

/**
 * Autonomous routine to simply cross the baseline.
 * 
 * @see AutonomousRoutine
 */
public class CrossBaseline extends AutonomousRoutine 
{
	private static final double DIRECTION = 0.50;
	private static final double YAW = 0.0;
	private static final int DISTANCE = 112 + 36; // Calculated distance is 112", add 36" to be safe
	
	/**
	 * Creates a new autonomous routine to cross the baseline
	 * @param drive the <code>TankDrive</code> subsystem
	 */
	public CrossBaseline(TankDrive drive)
	{
		this.addParallel(new GoStraightDistance(DIRECTION, YAW, DISTANCE, drive));
	}
}
