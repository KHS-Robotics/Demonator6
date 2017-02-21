package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

/**
 * Autonomous routine to simply cross the baseline.
 * 
 * @see edu.wpi.first.wpilibj.command.CommandGroup
 */
public class CrossBaseline extends AutonomousRoutine 
{
	private static final int DISTANCE = 122; // Calculated distance is 112", add 12" to be safe
	
	/**
	 * Creates a new autonomous routine to cross the baseline
	 * @param drive the <code>TankDrive</code> subsystem
	 */
	public CrossBaseline(TankDrive drive)
	{
		this.addSequential(new GoStraightDistance(0.75, drive.getHeading(), DISTANCE, drive));
	}
}
