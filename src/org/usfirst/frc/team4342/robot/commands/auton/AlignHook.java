package org.usfirst.frc.team4342.robot.commands.auton;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AlignHook extends AutonomousCommand 
{
	public enum Location
	{
		LEFT, MIDDLE, RIGHT
	}
	
	private enum HookState 
    {
    	START, FIX, FINISHING, FINISHED
    }
	
	private TankDrive drive;
	private Location location;
	
	private HookState hookState;
	
	private double hookAngle;
	private double yudist, xudist, udist, ydist, xdist, dist, rmain;
	private double sensorAngle, changeAngle, otherAngle;
	private double initL, initR;
	private final double FINAL_DIST = 36, ULTRA_DIST = 14.5, TAPE_DIST = 4.1;

    public AlignHook(TankDrive drive, Location location)
    {
    	super();
    	
    	this.requires(drive);
    	
    	this.drive = drive;
    	this.location = location;
    }
    
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
		
    	drive.enablePID();
    	hookState = HookState.START;
    }

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
//			if(l && r && (robotAngle-hookAngle) <= hookError)
//			{
//				sensorAngle = robotAngle;
//				hookState = HookState.FINISHING;
//			}
			
			if(l || r)
			{
				sensorAngle = robotAngle;
				if(r)
					udist = d - (7.25/Math.tan(Math.toRadians(90 - sensorAngle)));
				else
					udist = d + (7.25/Math.tan(Math.toRadians(90 - sensorAngle)));
				
				yudist = Math.abs(udist * Math.cos(Math.toRadians(sensorAngle)));
				xudist = Math.abs(udist * Math.sin(Math.toRadians(sensorAngle)));
				
				xdist = Math.abs(xudist - (TAPE_DIST + (ULTRA_DIST/((Math.sin(((90 - Math.abs(sensorAngle)) * (Math.PI/180))))))));
				
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
			
			drive.setHeading(hookAngle);
    	}
		
		else if(hookState == HookState.FIX)
		{
			rmain = drive.remainingDistance(dist, initL, initR);
			SmartDashboard.putNumber("Remain -", rmain);
			
			if(drive.remainingDistance(dist, initL, initR) > 0)
			{
				
				drive.goStraight(.35 , (sensorAngle + changeAngle));
			}
				
			else
			{
				drive.setHeading(hookAngle);
				if(Math.abs(robotAngle - hookAngle) < hookError)
					hookState = HookState.FINISHING;
			}
//			}
//			else
//			{
//				drive.setHeading(robotAngle);
//				
//				if (Math.abs(robotAngle - hookAngle) <= hookError)
//					hookState = HookState.FINISHING;
//			}
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
}