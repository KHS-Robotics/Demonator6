package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.commands.auton.AlignHook.Location;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

public class TurnUntilSeePeg extends AutonomousCommand
{
	private static final int PEG_YAW = 60;
	private static final double CENTERING_ANGLE = 61.48;
	
	private boolean sawLeft, sawRight;
	
	private double overturn;
	private Location location;
	private TankDrive drive;
	
	public TurnUntilSeePeg(double overturn, Location location, TankDrive drive)
	{
		if (Location.MIDDLE.equals(location))
			throw new IllegalArgumentException("Location cannot be in middle.");
			
		this.overturn = overturn;
		this.location = location;
		this.drive = drive;
	}

	@Override
	protected void initialize() 
	{
		if(Location.RIGHT.equals(location))
			drive.setHeading(-PEG_YAW - overturn);
		else if(Location.LEFT.equals(location))
			drive.setHeading(PEG_YAW + overturn);
	}

	@Override
	protected void execute() 
	{
		final boolean LEFT = drive.getLeftSensor();
		final boolean RIGHT = drive.getRightSensor();
		
		if(LEFT && !sawLeft)
		{
			drive.setHeading(drive.getHeading() - CENTERING_ANGLE);
			sawLeft = true;
		}
		else if(RIGHT && !sawRight)
		{
			drive.setHeading(drive.getHeading() + CENTERING_ANGLE);
			sawRight = true;
		}
		else if(LEFT && RIGHT)
		{
			drive.setHeading(drive.getHeading());
			sawLeft = sawRight = true;
		}
	}

	@Override
	protected void end() 
	{
		drive.disablePID();
		drive.set(0, 0);
	}

	@Override
	protected boolean isFinished() 
	{
		return (sawLeft || sawRight) && drive.onTarget();
	}
}
