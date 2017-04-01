package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.ButtonMap;
import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Teleop command to drive the <code>TankDrive</code> subsystem
 * with two joysticks
 * 
 * @see org.usfirst.frc.team4342.robot.commands.teleop.TeleopCommand
 */
public class DriveWithJoysticks extends TeleopCommand
{	
	private static final double BOILER_YAW = 135.0;
	private static final double LOAD_IN_YAW = 28.0;
	
	private boolean holdDesiredYaw;
	private double desiredYaw;
	
	private long lastTimeShiftedMs;
	private boolean usedShiftHighAndGoStraight;
	
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
		final boolean SHIFT_AND_HOLD_CURRENT_YAW = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.SHIFT_AND_HOLD_CURRENT_YAW);
		final double LEFT_Y = leftJoystick.getY();
		final double RIGHT_Y = -rightJoystick.getY();
		final boolean SHIFT = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.SHIFT);
		final boolean ALIGN_STRAIGHT = leftJoystick.getRawButton(ButtonMap.DriveStick.Left.ALIGN_STRAIGHT);
		final boolean HOLD_CURRENT_YAW = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.HOLD_CURRENT_YAW);
		final boolean AIM_BOILER = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.BOILER_YAW);
		final boolean GO_TO_LOAD_IN_YAW = leftJoystick.getRawButton(ButtonMap.DriveStick.Left.GO_TO_LOAD_IN_YAW);
		final boolean ROTATE = rightJoystick.getRawButton(ButtonMap.DriveStick.Right.ROTATE);
		
		if(SHIFT)
		{
			final long CURRENT_TIME = System.currentTimeMillis();
			
			if(CURRENT_TIME - lastTimeShiftedMs >= 500)
			{
				lastTimeShiftedMs = System.currentTimeMillis();
				drive.shift();
			}
		}
		
		if(ROTATE)
		{
			drive.disablePID();
			drive.set(RIGHT_Y, RIGHT_Y);
			return;
		}
		
		if(!holdDesiredYaw && ALIGN_STRAIGHT)
		{
			desiredYaw = 0;
			drive.setHeading(desiredYaw);
			holdDesiredYaw = true;
		}
		else if(!holdDesiredYaw && HOLD_CURRENT_YAW)
		{
			desiredYaw = drive.getHeading();
			drive.setHeading(desiredYaw);
			holdDesiredYaw = true;
		}
		else if(!holdDesiredYaw &&  SHIFT_AND_HOLD_CURRENT_YAW)
		{
			usedShiftHighAndGoStraight = true;
			
			drive.shiftHigh();
			desiredYaw = drive.getHeading();
			drive.setHeading(desiredYaw);
			holdDesiredYaw = true;
		}
		else if(!holdDesiredYaw && AIM_BOILER)
		{
			if(this.isBlueAlliance())
				desiredYaw = -BOILER_YAW;
			else  if(this.isRedAlliance())
				desiredYaw = BOILER_YAW;
			else
				desiredYaw = BOILER_YAW + 45;
			
			desiredYaw = SmartDashboard.getNumber("Boiler-Yaw", desiredYaw);
			
			drive.setHeading(desiredYaw);
			holdDesiredYaw = true;
		}
		else if(!holdDesiredYaw && GO_TO_LOAD_IN_YAW)
		{
			if(this.isBlueAlliance())
				desiredYaw = LOAD_IN_YAW;
			else if(this.isRedAlliance())
				desiredYaw = -LOAD_IN_YAW;
			else
				desiredYaw = 0;
			
			drive.setHeading(desiredYaw);
			holdDesiredYaw = true;
		}
		else if(holdDesiredYaw && (HOLD_CURRENT_YAW || SHIFT_AND_HOLD_CURRENT_YAW || AIM_BOILER))
		{
			drive.goStraight(adjust(RIGHT_Y), desiredYaw);
		}
		else
		{
			if(holdDesiredYaw && (Math.abs(LEFT_Y) > IO.JOYSTICK_DEADZONE || Math.abs(RIGHT_Y) > IO.JOYSTICK_DEADZONE))
			{	
				drive.disablePID();
				holdDesiredYaw = false;
			}
			else if(!holdDesiredYaw)
			{
				if(usedShiftHighAndGoStraight)
				{
					usedShiftHighAndGoStraight = false;
					drive.shiftLow();
				}
				
				drive.set(adjust(LEFT_Y), adjust(RIGHT_Y));
			}
		}
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
