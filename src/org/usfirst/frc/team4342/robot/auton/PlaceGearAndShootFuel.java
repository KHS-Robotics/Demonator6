package org.usfirst.frc.team4342.robot.auton;

import org.usfirst.frc.team4342.robot.commands.AlignPeg;
import org.usfirst.frc.team4342.robot.commands.GoStraightDistance;
import org.usfirst.frc.team4342.robot.commands.GoToAngle;
import org.usfirst.frc.team4342.robot.commands.LowerGear;
import org.usfirst.frc.team4342.robot.commands.OrientToBoiler;
import org.usfirst.frc.team4342.robot.commands.StartShooter;
import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.Shooter;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class PlaceGearAndShootFuel extends AutonomousRoutine 
{
	// After PlaceGear
	// Go Straight
	private static final double DIRECTION = -0.70;
	private static final int PEG_YAW = 60;
	private static final int DISTANCE = 60;
	
	// Go To Angle
	private static final int BOILER_YAW = 135;
	
	public PlaceGearAndShootFuel(TankDrive drive, GearPlacer placer, Shooter shooter, Alliance alliance)
	{
		super(alliance);
		
		this.addSequential(new PlaceGear(drive, placer, this.isRedAlliance() ? AlignPeg.Location.LEFT : AlignPeg.Location.RIGHT));
		this.addSequential(new LowerGear(placer));
		this.addSequential(new GoStraightDistance(DIRECTION, this.isBlueAlliance() ? PEG_YAW : -PEG_YAW, DISTANCE, drive));
		this.addSequential(new GoToAngle(this.isRedAlliance() ? BOILER_YAW : -BOILER_YAW, drive));
		this.addSequential(new OrientToBoiler(drive, this.isRedAlliance() ? 5 : -5));
		this.addSequential(new StartShooter(shooter, true));
	}
}
