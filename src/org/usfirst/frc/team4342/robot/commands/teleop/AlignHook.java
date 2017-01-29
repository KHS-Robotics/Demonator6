package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignHook extends Command 
{
	private enum HookState 
    {
    	START, FIXRIGHT, FIXLEFT, FINISHING
    }
	
	private TankDrive drive;
	
	private HookState hookState = HookState.START;
	private int sensorAdjust;

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
    		
		if(hookState == HookState.START)
		{
			sensorAdjust = 0;
			drive.setHeading(hookAngle);
			
			if(r)
			{
				if(Math.abs(robotAngle-hookAngle) <= hookError)
				{
					
					if(l)
					{
						drive.disablePID();
						hookState = HookState.FINISHING;
					}
				}
				else
				{
					drive.disablePID();
					hookState = HookState.FIXRIGHT;
					sensorAdjust = 1;
				}
			}
			else if(l)
			{
				if(Math.abs(robotAngle-hookAngle) <= hookError)
				{
					if(r)
					{
						drive.disablePID();
						hookState = HookState.FINISHING;
					}
				}
    			else
    			{
					drive.disablePID();
					hookState = HookState.FIXLEFT;
					sensorAdjust = -1;
    			}
			}
    	}
		else if(hookState == HookState.FIXRIGHT)
		{
			
			if(r && l)
				sensorAdjust = 0;
			else if(sensorAdjust == 0)
				sensorAdjust = -1;
			
			drive.setLeft(-0.75);
			
			if(robotAngle < hookAngle)
			{
				hookState = HookState.FINISHING;
			}
		}
		else if(hookState == HookState.FIXLEFT)
		{
			
			if(r && l)
				sensorAdjust = 0;
			else if(sensorAdjust == 0)
				sensorAdjust = 1;
			
			drive.setRight(-0.75);
			
			if(robotAngle > hookAngle)
				hookState = HookState.FINISHING;
		}
		else if(hookState == HookState.FINISHING)
		{
			if(r && l)
				sensorAdjust = 0;
			
			drive.goStraight(-0.5, (robotAngle + sensorAdjust));
		}	
    }

    protected boolean isFinished() 
    {
        return (hookState == HookState.FINISHING);
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
    
}
