package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aligns the gear placer to place the gear on the hook
 */
public class AlignHook extends AutonomousCommand 
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
	
	private HookState hookState;
	
	private boolean robotPos;
	private double hookAngle;
	private double yudist, xudist, udist, ydist, xdist, dist, rmain;
	private double sensorAngle, changeAngle, otherAngle;
	private double initL, initR;
	private final double FINAL_DIST = 36 + 17, ULTRA_DIST = 7.25, TAPE_DIST = 4.1;

	/**
	 * Creates a new <code>AlignHook</code> command
	 * @param drive the <code>TankDrive</code> subsystem
	 * @param gearPlacer the <code>GearPlacer</code> subsystem
	 * @param location the location of the hook
	 * @see Location
	 */
    public AlignHook(TankDrive drive)
    {
    	super();
    	
    	this.requires(drive);
    	
    	this.drive = drive;
    	
    	setLocation(Location.MIDDLE);
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
     * Starts the command with the specified location
     * @param location the location the command should use
     */
    public void start(Location location)
    {
    	this.setLocation(location);
    	this.start();
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
			robotPos = true;
		else
			robotPos = false;
		
    	drive.enablePID();
    	
    	drive.shiftLow();
    	
    	hookState = HookState.START;
    }

    /**
     * Aligns the gear placer to the hook
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
			
			else if((l && robotPos)|| (r && !robotPos))
			{
				sensorAngle = robotAngle;
				
				udist = d;
				
				yudist = Math.abs(udist * Math.cos(Math.toRadians(sensorAngle)));
				xudist = Math.abs(udist * Math.sin(Math.toRadians(sensorAngle)));
				
				xdist = Math.abs(xudist - (TAPE_DIST + (ULTRA_DIST/(Math.sin(((Math.toRadians(90 - Math.abs(sensorAngle))))))))) - 12; //subtract 12 cause too far left on left
				
				ydist = Math.abs(yudist - FINAL_DIST);
				otherAngle = Math.abs(Math.toDegrees(Math.atan(ydist/xdist)));
				
				dist = Math.abs(Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2)));
				changeAngle = 90 - Math.abs(Math.abs(sensorAngle) + Math.abs(otherAngle));
				
				if(r)
					changeAngle = -changeAngle;
				
				hookState = HookState.FIX;
				
				SmartDashboard.putNumber("First Angle -", sensorAngle);
				SmartDashboard.putNumber("Change in Angle -", changeAngle);
				SmartDashboard.putNumber("other Angle -", otherAngle);
				SmartDashboard.putNumber("Ultra Dist -", udist);
				SmartDashboard.putNumber("Ultra Y Dist -", yudist);
				SmartDashboard.putNumber("Ultra X Dist -", xudist);
				SmartDashboard.putNumber("X Dist -", xdist);
				SmartDashboard.putNumber("Y Dist -", ydist);
				SmartDashboard.putNumber("Dist -", dist);
				
				drive.disablePID();
				initL = drive.getLeftDistance();
				initR = drive.getRightDistance();
			}
			
			else
				drive.setHeading(hookAngle);
    	}
		
		else if(hookState == HookState.FIX)
		{
			rmain = drive.remainingDistance(dist, initL, initR);
			SmartDashboard.putNumber("Remain -", rmain);
			
			if(drive.remainingDistance(dist, initL, initR) > 0)
			{
				drive.goStraight(.7 , (sensorAngle + changeAngle));
			}
				
			else
			{
				hookState = HookState.FIX2;
			}
		}
		
		else if(hookState == HookState.FIX2)
		{
			drive.setHeading(hookAngle);
			
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
     * Disabled drive PID and zeros drive outputs
     */
    @Override
    protected void end() 
    {
    	drive.setDirection(0);
    	drive.disablePID();
    	drive.set(0, 0);
    }
    
    /**
     * Checks the state of the command based on the hook state
     * @return true if the hook state is equal to finished, false otherwise
     */
    @Override
    protected boolean isFinished()
    {
    	return hookState.equals(HookState.FINISHED);// || gearplacer.isInPeg();
    }
}