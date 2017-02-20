package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Teleop command to drive the <code>TankDrive</code> subsystem
 * with two joysticks
 * 
 * @see org.usfirst.frc.team4342.robot.commands.teleop.TeleopCommand
 */
public class DriveWithJoysticks extends TeleopCommand
{	
	private boolean holdCurrentYaw;
	private double currentYaw;
	
	private Joystick leftJoystick, rightJoystick;
	private TankDrive drive;
	
	/**
	 * Creates a new <code>DriveWithJoysticks</code> command.
	 * @param leftJoystick the left stick to control the left side of the drive train
	 * @param rightJoystick the right stick to control the right side of the drive train
	 * @param drive the <code>TankDrive</code> subsystem to output to
	 * @see org.usfirst.frc.team4342.robot.subsystems.TankDrive
	 */
	public DriveWithJoysticks(Joystick leftJoystick, Joystick rightJoystick, TankDrive drive)
	{
		this.requires(drive);
		
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;
		this.drive = drive;
	}
	
	/**
	 * The main logic of this command to actually drive the robot.
	 */
	@Override
	protected void execute()
	{
		final double LEFT_Y = leftJoystick.getY();
		final double RIGHT_Y = -rightJoystick.getY();
		final boolean SHIFT = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.SHIFT);
		
		if(SHIFT)
			drive.shiftHigh();
		else
			drive.shiftLow();
		
		if(!holdCurrentYaw && rightJoystick.getRawButton(ButtonMap.DriveStick.Right.HOLD_CURRENT_YAW))
		{
			holdCurrentYaw = true;
			currentYaw = drive.getHeading();
		}
		else if(rightJoystick.getRawButton(ButtonMap.DriveStick.Right.HOLD_CURRENT_YAW))
		{
			drive.goStraight(adjust(RIGHT_Y), currentYaw);
			return;
		}
		else
		{
			holdCurrentYaw = false;
		}
		
		if(leftJoystick.getRawButton(ButtonMap.DriveStick.Left.ALIGN_STRAIGHT))
			drive.setHeading(0);
		
		if(Math.abs(LEFT_Y) > IO.JOYSTICK_DEADZONE || Math.abs(RIGHT_Y) > IO.JOYSTICK_DEADZONE)
			drive.disablePID();
		
		if(!drive.pidEnabled())
			drive.set(adjust(RIGHT_Y), adjust(LEFT_Y));
	}
	
	/**
	 * Disables the drive PID and then zeros the outputs
	 */
	@Override
	protected void end()
	{
		drive.disablePID();
		drive.set(0, 0);
		drive.shiftLow();
	}
	
	/**
	 * Internal function to adjust output for sensitiviy control
	 * @param input the desired input before adjustment from -1.0 to 1.0
	 * @return the adjusted output
	 */
	private static double adjust(double input)
	{
		if(input > 1)
			input = 1;
		else if(input < -1)
			input = -1;
		
		final double SENSITIVITY = 0.60;

		double output = 0;
		output = input >= 0 ? 
				IO.JOYSTICK_DEADZONE + (1 - IO.JOYSTICK_DEADZONE) * (SENSITIVITY*Math.pow(input, 3)) + (1 - SENSITIVITY)*input : 
			   -IO.JOYSTICK_DEADZONE + (1 - IO.JOYSTICK_DEADZONE) * (SENSITIVITY*Math.pow(input, 3)) + (1 - SENSITIVITY)*input;
		
		return output;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void initialize() {}
}
