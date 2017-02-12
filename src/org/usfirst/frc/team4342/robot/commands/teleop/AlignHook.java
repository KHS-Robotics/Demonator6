package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AlignHook extends Command 
{
	private enum HookState 
    {
    	START, FIX, FINISHING
    }
	
	private TankDrive drive;
	
	private HookState hookState = HookState.START;
	private int sensorAdjust;
	
	private double yudist, xudist, udist, ydist, xdist, dist;
	private double sensorAngle, changeAngle, otherAngle;
	private final double FINAL_DIST = 36, CENTER_DIST = 6.5, TAPE_DIST = 4.1;

    public AlignHook(TankDrive drive)
    {
    	super(10);
    	
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
		double robotAngle = drive.getYaw();
		
		final boolean r = drive.getRightSensor();
		final boolean l = drive.getLeftSensor();
		
		final double d = drive.getUltrasonic();
    		
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
				
				
				xdist = Math.abs(xudist - (TAPE_DIST + (CENTER_DIST/(Math.sin((90 - Math.abs(sensorAngle)) * (Math.PI/180))))));
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
				
				getState();
				
			}
			
			drive.setHeading(hookAngle);
    	}
		
		else if(hookState == HookState.FIX)
		{
			drive.goStraight(.35 , (sensorAngle + changeAngle));
		}
		else if(hookState == HookState.FINISHING)
		{			
			drive.goStraight(0.35, sensorAngle);
		}	
    }

    protected boolean isFinished() 
    {
        return (false);
    }

    protected void end() 
    {
    	drive.setDirection(0);
    	drive.disablePID();
    	drive.set(0, 0);
    }

    
    protected void interrupted() 
    {
    	this.end();
    }
    
    public String getState()
    {
    	if(hookState == HookState.START)
    		return "start";
    	else if(hookState == HookState.FIX)
    		return "fix";
    	else
    		return "finishing";
    }
    
}
