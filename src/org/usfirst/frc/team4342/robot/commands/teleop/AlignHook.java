package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AlignHook extends TeleopCommand 
{
	private enum HookState 
    {
    	START, FIX, FINISHING, FINISHED
    }
	
	private TankDrive drive;
	
	private HookState hookState = HookState.START;
	
	private double yudist, xudist, udist, ydist, xdist, dist, rdist;
	private double sensorAngle, changeAngle, otherAngle;
	private final double FINAL_DIST = 54, CENTER_DIST = 15, TAPE_DIST = 4.1, MIDDLE_DIST = 17;

    public AlignHook(TankDrive drive)
    {
    	super();
    	
    	this.requires(drive);
    	
    	this.drive = drive;
    	
    	this.setInterruptible(false);
    }
    
    @Override
    protected void initialize() 
    {
    	drive.enablePID();
    }

    @Override
    protected void execute() 
    {
		double hookAngle = 0;
		double hookError = 3;
		double robotAngle = drive.getHeading();
		
		
		final boolean r = drive.getRightSensor();
		final boolean l = drive.getLeftSensor();
		
		final double d = drive.getUltrasonicDistance();
    		
		if(hookState == HookState.START)
		{
			if(l && r && (robotAngle-hookAngle) <= hookError)
			{
				sensorAngle = robotAngle;
				hookState = HookState.FINISHING;
			}
			
			else if(l || r)
			{
				sensorAngle = robotAngle;
				
				udist = d;
				yudist = Math.abs(udist * Math.cos(sensorAngle * (Math.PI/180)));
				xudist = Math.abs(udist * Math.sin(sensorAngle * (Math.PI/180)));
				
				if(r)
					xdist = Math.abs(xudist - (TAPE_DIST));
				else
					xdist = Math.abs(xudist - (TAPE_DIST + (CENTER_DIST/(Math.sin((90 - Math.abs(sensorAngle)) * (Math.PI/180))))));
				
				ydist = Math.abs(yudist - FINAL_DIST);
				otherAngle = Math.abs(Math.toDegrees(Math.atan(ydist/xdist)));
				
				dist = Math.abs(Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2))) + MIDDLE_DIST;
				changeAngle = 90 - Math.abs(Math.abs(sensorAngle) + Math.abs(otherAngle));
				
				rdist = drive.remainingDistance(dist, 0, 0);
				
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
				SmartDashboard.putNumber("Remain Dist -", rdist);
				
				getState();
				
			}
			
			drive.setHeading(hookAngle);
    	}
		
		else if(hookState == HookState.FIX)
		{
			if(drive.remainingDistance(dist, 0, 0) > 0)
				drive.goStraight(.35 , (sensorAngle + changeAngle));
			else
			{
				drive.enablePID();
				drive.setHeading(hookAngle);
				hookState = HookState.FINISHING;
			}
		}
		else if(hookState == HookState.FINISHING)
		{			
			drive.disablePID();
			hookState = HookState.FINISHED;
		}	
    }

    @Override
    protected void end() 
    {
    	drive.setDirection(0);
    	drive.disablePID();
    	drive.set(0, 0);
    }
    
    @Override
    protected boolean isFinished()
    {
    	return hookState.equals(HookState.FINISHED);
    }
    
    public String getState()
    {
    	return hookState.toString();
    }
    
}
