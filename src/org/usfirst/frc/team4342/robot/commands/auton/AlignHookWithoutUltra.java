package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.GearPlacer;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AlignHookWithoutUltra extends AutonomousCommand 
{
	/**
	 * Location of the hook to place the gear onto
	 */
	public enum Location
	{
		LEFT, MIDDLE, RIGHT
	}
	
	/**
	 * Used to keep track of the align hook state machine
	 */
	private enum HookState 
    {
    	START, FIX, FIX2, GOSTRAIGHT, FINISHING, FINISHED
    }
	
	private TankDrive drive;
	private Location location;
	private GearPlacer gearplacer;
	
	private HookState hookState;
	
	private boolean robotPos;
	private double hookAngle;
	private double yudist, xudist, udist, ydist, xdist, dist, rmain;
	private double sensorAngle, angleError, otherAngle, maxAngle;
	private double initL, initR;
	private final double ULTRA_DIST = 7.25, TAPE_DIST = 4.1, MAX_ANGLE = 48.8;

	/**
	 * Creates a new <code>AlignHookWithoutUltra</code> command
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param gearPlacer the <code>GearPlacer</code> subsystem
	 * @param location the location of the hook
	 * @see Location
	 */
    public AlignHookWithoutUltra(TankDrive drive, GearPlacer gearPlacer, Location location) 
    {
    	super();
    	
    	this.requires(drive);
    	
    	this.drive = drive;
    	this.location = location;
    	this.gearplacer = gearPlacer;
    }
    
    /**
     * Sets the location
     * @param location the location
     * @see Location
     */
    public void setLocation(Location location)
    {
    	this.location = location;
    }

    /**
     * Determines the hook angle based on location, enables drive PID,
     * and sets the hook state to start
     */
    @Override
    protected void initialize() 
    {
    	SmartDashboard.putNumber("First Angle -", 0.0);
		SmartDashboard.putNumber("Change in Angle -", 0.0);
		SmartDashboard.putNumber("other Angle -", 0.0);
		SmartDashboard.putNumber("Ultra Dist -", 0.0);
		SmartDashboard.putNumber("Ultra Y Dist -", 0.0);
		SmartDashboard.putNumber("Ultra X Dist -", 0.0);
		SmartDashboard.putNumber("X Dist -", 0.0);
		SmartDashboard.putNumber("Y Dist -", 0.0);
		SmartDashboard.putNumber("Dist -", 0.0);
		
		if(Location.LEFT.equals(location))
			hookAngle = -60;
		else if(Location.RIGHT.equals(location))
			hookAngle = 60;
		else
			hookAngle = 0;
		
		if(drive.getHeading() > 0)
		{
			robotPos = true;
			maxAngle = MAX_ANGLE;
		}
		else
		{
			robotPos = false;
			maxAngle = -MAX_ANGLE;
		}
			
		angleError = .3;
		
    	drive.enablePID();
    	
    	drive.shiftLow();
    	
    	hookState = HookState.START;
    }

    /**
     * Aligns the gear placer to the hook w/out using
     * the ultrasonic
     */
    @Override
    protected void execute() 
    {
    	double hookError = 0.1;
		double robotAngle = drive.getHeading();
		
		final boolean r = drive.getRightSensor();
		final boolean l = drive.getLeftSensor();
		
		final double d = drive.getUltrasonicDistance();
		
		if(hookState == HookState.START)
		{
			if(l && r && (robotAngle-hookAngle) <= hookError)
			{
				sensorAngle = robotAngle;
				hookState = HookState.GOSTRAIGHT;
			}
			
			else if(robotAngle == hookAngle)
			{
				yudist = d + 3.5;	
			}
			
			else if(r && !robotPos)
			{
				sensorAngle = robotAngle;
				xdist = yudist / (Math.tan(sensorAngle)) - 8;
			}
			
			else if(r && robotPos)
			{
				sensorAngle = robotAngle;
				xdist = yudist / (Math.tan(sensorAngle)) + 8;
			}
			
			else if(l && robotPos)
			{
				sensorAngle = robotAngle;
				xdist = yudist / (Math.tan(sensorAngle)) + 8;
			}
			
			else if(l && !robotPos)
			{
				sensorAngle = robotAngle;
				xdist = yudist / (Math.tan(sensorAngle)) - 8;
			}
			
			if(!robotPos)
				drive.setHeading(hookAngle + 90);
			else
				drive.setHeading(hookAngle - 90);
			
			if(((robotAngle - (hookAngle + 90)) <= angleError) || ((robotAngle - (hookAngle - 90)) <= angleError))
			{
				initL = drive.getLeftDistance();
				initR = drive.getRightDistance();
				
				hookState = HookState.FIX;
			}
		}
		
		else if(hookState == HookState.FIX)
		{
			rmain = drive.remainingDistance(xdist, initL, initR);
			SmartDashboard.putNumber("Remain -", rmain);
			
			if(drive.remainingDistance(dist, initL, initR) > 0)
			{
				drive.goStraight(.7, robotAngle);
			}
				
			else
			{
				hookState = HookState.FIX2;
			}
		}
		
		else if(hookState == HookState.FIX2)
		{
			drive.setHeading(hookAngle);
			
			sensorAngle = hookAngle;
			
			if(r && l)
			{
				sensorAngle = robotAngle;
				hookState = HookState.GOSTRAIGHT;
			}
			else if(Math.abs(robotAngle - hookAngle) < hookError)
				hookState = HookState.FINISHING;
		}
		
		else if(hookState == HookState.GOSTRAIGHT)
		{
			drive.goStraight(0.35, sensorAngle);
			
			if(drive.getUltrasonicDistance() <= 4.5)
				hookState = HookState.FINISHING;
		}
		
		else if(hookState == HookState.FINISHING)
		{
			drive.disablePID();
			hookState = HookState.FINISHED;
		}
    }

    /**
     * Checks the state of the command based on the hook state
     * @return true if the hook state is equal to finished, false otherwise
     */
    @Override
    protected boolean isFinished() 
    {
    	return hookState.equals(HookState.FINISHED) || gearplacer.isInPeg();
    }

    /**
     * Disabled drive PID and zeros drive outputs
     */
    @Override
    protected void end() 
    {
    	drive.setDirection(0);
    	drive.disablePID();
    	drive.set(0, 0);
    }
}
