package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.commands.auton.GoToAngle;
import org.usfirst.frc.team4342.robot.commands.auton.Shoot;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 * Autonomous routine to shoot fuel into the boiler
 */
public class ShootFuelIntoBoiler extends AutonomousRoutine
{
	private TankDrive drive;
	
	private static final int OFFSET = 90;
	
	// Step 2 (if blue alliance)
	public static final double SHOOT_YAW = -100;

	private static final double SHOOT_SECONDS = 7;
	
	private static final double CROSS_BASELINE_DIRECTION = 0.50;
	private static final int CROSS_BASELINE_DISTANCE = 72;
	
	/**
	 * Creates a new <code>ShootFuelIntoBoiler</code> routine
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param shooter the <code>Shooter</code> subsystem
	 */
	public ShootFuelIntoBoiler(TankDrive drive, Shooter shooter, Alliance alliance)
	{
		super(alliance);
		
		this.drive = drive;
		
		this.addSequential(new GoToAngle(5, drive));
		
		if(this.isBlueAlliance())
			this.addSequential(new GoToAngle(SHOOT_YAW, drive));
		
		this.addSequential(new Shoot(shooter, true, SHOOT_SECONDS));
		this.addSequential(new GoToAngle(0, drive));
		this.addSequential(new GoStraightDistance(CROSS_BASELINE_DIRECTION, 0, CROSS_BASELINE_DISTANCE, drive));
		
	}
	
	@Override
	public void initialize()
	{
//		if(this.isRedAlliance())
//		{
//			drive.setYawOffset(OFFSET);
//		}
//		else
//		{
//			drive.setYawOffset(-OFFSET);
//		}
	}
}
