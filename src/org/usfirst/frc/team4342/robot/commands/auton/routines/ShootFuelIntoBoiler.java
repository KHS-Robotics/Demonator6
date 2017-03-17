package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.commands.auton.GoToAngle;
import org.usfirst.frc.team4342.robot.commands.auton.OrientToBoiler;
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
	
	// Step 2
	public static final double RED_SHOOT_YAW = 95;
	public static final double BLUE_SHOOT_YAW = -100;

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
		
		if(this.isRedAlliance())
			this.addSequential(new GoToAngle(RED_SHOOT_YAW, drive));
		else if(this.isBlueAlliance() && !this.isUsingVision())
			this.addSequential(new GoToAngle(BLUE_SHOOT_YAW, drive));
		
		if(this.isUsingVision())
			this.addSequential(new OrientToBoiler(drive));

		this.addSequential(new Shoot(shooter, true, SHOOT_SECONDS));
		this.addSequential(new GoToAngle(0, drive));
		this.addSequential(new GoStraightDistance(CROSS_BASELINE_DIRECTION, 0, CROSS_BASELINE_DISTANCE, drive));
	}
	
	@Override
	public void initialize()
	{
		if(this.isRedAlliance())
		{
			drive.setYawOffset(OFFSET);
		}
		else
		{
			drive.setYawOffset(-OFFSET);
		}
	}
}
