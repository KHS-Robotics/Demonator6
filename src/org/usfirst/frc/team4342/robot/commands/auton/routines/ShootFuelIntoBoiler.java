package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.commands.auton.GoToAngle;
import org.usfirst.frc.team4342.robot.commands.auton.Shoot;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

/**
 * Autonomous routine to shoot fuel into the boiler
 */
public class ShootFuelIntoBoiler extends AutonomousRoutine
{
	private static final int OFFSET = 90;
	
	// Step 1
	private static final double DIRECTION = -0.25;
	private static final int MOVE_DISTANCE_INCHES = 10;
	
	// Step 2 (if blue alliance)
	public static final double SHOOT_YAW = -110;
	
	/**
	 * Creates a new <code>ShootFuelIntoBoiler</code> routine
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param shooter the <code>Shooter</code> subsystem
	 */
	public ShootFuelIntoBoiler(TankDrive drive, Shooter shooter)
	{
		this.addSequential(new GoStraightDistance(DIRECTION, drive.getHeading(), MOVE_DISTANCE_INCHES, drive));
		
		if(this.isRedAlliance())
		{
			drive.setYawOffset(OFFSET);
		}
		else
		{
			drive.setYawOffset(-OFFSET);
			this.addSequential(new GoToAngle(SHOOT_YAW, drive));
		}
		
		this.addSequential(new Shoot(shooter, true, 15));
	}
}
