package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.subsystems.Scaler;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to scale the airship
 */
public class Scale extends TeleopCommand
{
	private Scaler scaler;
	private Joystick j;
//	private TankDrive drive;
	
	/**
	 * Creates a new <code>Scale</code> command with a 10 second timeout.
	 * @param scaler the <code>Scaler</code> subsystem
	 * @param drive the <code>TankDrive</code> subsystem to output to
	 * @see org.usfirst.frc.team4342.robot.subsystems.Scaler
	 * @see org.usfirst.frc.team4342.robot.subsystems.TankDrive
	 */
	public Scale(Joystick j, Scaler scaler)
	{
		super();
		
		this.requires(scaler);
//		this.requires(drive);
		
		this.j = j;
		this.scaler = scaler;
//		this.drive = drive;
		
		this.setInterruptible(false);
	}
	
	/**
	 * Tells the tank drive to go straight at 1/4 power at the current 
	 * yaw and enable the scaler
	 */
	@Override
	public void initialize()
	{
//		drive.goStraight(0.25, drive.getHeading());
//		scaler.enable();
	}
	
	@Override
	protected void execute() 
	{
		final boolean SCALE = j.getRawButton(ButtonMap.DriveStick.Right.SCALE);
		
		if(SCALE)
			scaler.enable();
	}
	
	/**
	 * Disables the scaler and drive PID and zeros drive outputs
	 */
	@Override
	protected void end()
	{
		scaler.disable();
		//drive.disablePID();
//		drive.set(0, 0);
	}
	
	/**
	 * Returns if the robot has scaled or if the command timed out
	 * @return true if the robot is finished scaling or timed out, false otherwise
	 */
	@Override
	protected boolean isFinished() 
	{
		return scaler.hasScaled() || this.isTimedOut();
	}
}
