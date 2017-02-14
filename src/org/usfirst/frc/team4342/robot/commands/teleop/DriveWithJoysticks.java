package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
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
	private Joystick leftJoystick, rightJoystick;
	private TankDrive drive;
	
	private boolean flipOrientation, pressedButtonLastIteration;
	
	/**
	 * Creates a new <code>DriveWithJoysticks</code> command.
	 * @param leftJoystick the left stick to control the left side of the drive train
	 * @param rightJoystick the right stick to control the right side of the drive train
	 * @param drive the <code>TankDrive</code> subsystem to output to
	 * @see org.usfirst.frc.team4342.robot.subsystems.TankDrive
	 */
	public DriveWithJoysticks(Joystick leftJoystick, Joystick rightJoystick, TankDrive drive)
	{
		super(DriveWithJoysticks.class.getName());
		
		this.requires(drive);
		
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;
		this.drive = drive;
	}
	
	/**
	 * The main logic of this command to actually drive the robot. The method does the following
	 * in the respective order:
	 * 
	 * <ol>
	 * <li>Grab the left and right stick's current y magnitude, if the driver wants to shift or wants to flip orientation</li>
	 * <li>Shift if the driver wants to</li>
	 * <li>Set left and right of drive train to left and right y magnitudes, respectively. May negate magnitudes depending on flip orientation</li>
	 * </ol>
	 */
	@Override
	protected void execute()
	{
		final double LEFT_Y = leftJoystick.getY();
		final double RIGHT_Y = -rightJoystick.getY();
		final boolean SHIFT = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.SHIFT);
		final boolean FLIP_ORIENTATION = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.FLIP_ORIENTATION);
		
		flipOrientation = FLIP_ORIENTATION && !pressedButtonLastIteration ? !flipOrientation : flipOrientation;
		pressedButtonLastIteration = FLIP_ORIENTATION;
		
		if(SHIFT)
			drive.shift();
		
		if(flipOrientation)
		{
			drive.set(adjust(RIGHT_Y), adjust(LEFT_Y));
			return;
		}
		
		drive.set(adjust(-RIGHT_Y), adjust(-LEFT_Y));
	}
	
	/**
	 * Disables the drive PID and then zeros the outputs
	 */
	@Override
	protected void end()
	{
		drive.disablePID();
		drive.set(0, 0);
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
		return (SENSITIVITY * Math.pow(input, 3)+ (1 - SENSITIVITY) * input);
	}
	
	/** {@inheritDoc} */
	@Override
	protected void initialize() {}
}
