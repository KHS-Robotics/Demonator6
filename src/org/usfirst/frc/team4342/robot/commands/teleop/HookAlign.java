package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.api.drive.HookState;
import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class HookAlign extends Command {
	private HookState hookState = HookState.START;
	private int sensorAdjust;
	private boolean r = IO.Sensors.getRightSensor(), l = IO.Sensors.getLeftSensor();
	private TankDrive driveTrain = IO.getDrive();

    public HookAlign() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    		double hookAngle = SmartDashboard.getNumber("Angle-Hook");
    		double hookError = SmartDashboard.getNumber("Error Value- Hook");
    		double robotAngle = navX.getAngle();
    		
    		if(hookState == HookState.START)
    		{
    			sensorAdjust = 0;
    			goToAngle(hookAngle);
    			
    			if(r)
    			{
    				if(Math.abs(robotAngle-hookAngle) <= hookError)
    				{
    					
    					if(l)
    					{
    						driveTrain.disablePID();
    						hookState = HookState.FINISHING;
    					}
    				}
    				else
    				{
    					driveTrain.disablePID();
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
    						driveTrain.disablePID();
    						hookState = HookState.FINISHING;
    					}
    				}
    				else
    				{
    					driveTrain.disablePID();
    					hookState = HookState.FIXLEFT;
    					sensorAdjust = -1;
    				}
    			}
    		}
    		else if(hookState == HookState.FIXRIGHT)
    		{
    			
    			if(r && l)
    			{
    				sensorAdjust = 0;
    			}
    			else if(sensorAdjust == 0)
    			{
    				sensorAdjust = -1;
    				
    			}
    			
    			driveTrain.setLeft(-0.75);
    			
    			if(robotAngle < hookAngle)
    			{
    				hookState = HookState.FINISHING;
    			}
    		}
    		else if(hookState == HookState.FIXLEFT)
    		{
    			
    			if(r && l)
    			{
    				sensorAdjust = 0;
    			}
    			else if(sensorAdjust == 0)
    			{
    				sensorAdjust = 1;
    			}
    			
    			driveTrain.setRight(-0.75);
    			
    			if(robotAngle > hookAngle)
    			{
    				hookState = HookState.FINISHING;
    			}
    		}
    		else if(hookState == HookState.FINISHING)
    		{
    			if(r && l)
    			{
    				sensorAdjust = 0;
    			}
    			
    			goStraight(-0.5, (robotAngle + sensorAdjust));
    		}
    		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    public enum HookState 
    {
    	START, FIXRIGHT, FIXLEFT, FINISHING, FINISH
    }
}
