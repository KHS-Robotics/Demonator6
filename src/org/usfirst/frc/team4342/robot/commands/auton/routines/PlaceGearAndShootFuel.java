package org.usfirst.frc.team4342.robot.commands.auton.routines;

import org.usfirst.frc.team4342.robot.commands.auton.AlignHook;
import org.usfirst.frc.team4342.robot.commands.auton.GoStraightDistance;
import org.usfirst.frc.team4342.robot.commands.auton.GoStraightUntilWithinDistance;
import org.usfirst.frc.team4342.robot.commands.auton.GoToAngle;
import org.usfirst.frc.team4342.robot.commands.auton.OrientToBoiler;
import org.usfirst.frc.team4342.robot.commands.auton.Shoot;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 * Auton routine to place a gear on a peg and then shoot fuel
 * into the boiler
 */
public class PlaceGearAndShootFuel extends AutonomousRoutine
{
	private static final double PLACE_PEG_DISTANCE_INCHES = 5.0;
	
	// Step 1
	private static final double START_YAW = 0;
	private static final double DISTANCE = 43.8; 
	private static final double DIRECTION = 0.67;
	
	// Step 2
	private static final double PEG_YAW = 60;
	
	// For after we place gear
	private static final double BOILER_DIRECTION = -0.67;
	private static final int BOILER_YAW = 159;
	private static final double BOILER_DISTANCE = 78.7;
	
	/**
	 * Creates a new <code>PlaceGearAndShootFuel</code> command
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param placer the <code>GearPlacer</code> subsystem
	 * @param shooter the <code>Shooter</code> subsystem
	 * @param alliance the alliance the robot is on
	 */
	public PlaceGearAndShootFuel(TankDrive drive, GearPlacer placer, Shooter shooter, Alliance alliance)
	{
		super(alliance);

		if(Alliance.Invalid.equals(alliance))
			throw new IllegalArgumentException("Alliance cannot be invalid.");
		
		this.addSequential(new GoStraightDistance(DIRECTION, START_YAW, DISTANCE, drive));
		
		if(this.isUsingDeadReckoning() && Alliance.Red.equals(alliance))
			this.addSequential(new GoToAngle(-PEG_YAW, drive));
		else if(this.isUsingDeadReckoning() && Alliance.Blue.equals(alliance))
			this.addSequential(new GoToAngle(PEG_YAW, drive));
		else if(!this.isUsingDeadReckoning())
			this.addSequential(new AlignHook(drive, placer, this.isRedAlliance() ? AlignHook.Location.LEFT : AlignHook.Location.RIGHT));
		
		if(this.isUsingDeadReckoning())
		{
			this.addSequential(new GoStraightDistance(DIRECTION, PEG_YAW, DISTANCE, drive));
			this.addSequential(new GoStraightUntilWithinDistance(drive, PLACE_PEG_DISTANCE_INCHES));
		}
		
		this.addSequential(new GoStraightDistance(BOILER_DIRECTION, PEG_YAW, BOILER_DISTANCE, drive));
		
		if(this.isRedAlliance())
			this.addSequential(new GoToAngle(BOILER_YAW, drive));
		else
			this.addSequential(new GoToAngle(-BOILER_YAW, drive));
		
		if(this.isUsingVision())
			this.addSequential(new OrientToBoiler(drive));
			
		this.addSequential(new Shoot(shooter, true, 15));
	}
}
