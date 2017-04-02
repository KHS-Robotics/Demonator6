package org.usfirst.frc.team4342.robot.subsystems;

import org.usfirst.frc.team4342.robot.IO;
import org.usfirst.frc.team4342.robot.commands.teleop.DriveWithJoysticks;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Tank Drive subsystem to control the drive train
 */
public class TankDrive extends DemonSubsystem implements PIDSource, PIDOutput
{
	private static final double P = 0.04, I = 0.0001, D = 0.012;
	private static final Value HIGH_GEAR = Value.kForward, LOW_GEAR = Value.kReverse;
	
	private CANTalon fr, fl, rr, rl, mr , ml;
	private AHRS navx;
	private DoubleSolenoid shifter;
	private Encoder left, right;
	private DigitalInput rsensor, lsensor;
	private Ultrasonic ultrasonic;
	private PIDController yawPID;
	
	private Value currentGear;
	private double direction;
	private double offset;
	private PIDSourceType pidSourceType;
	
	/**
	 * Creates a new <code>TankDrive</code> subsystem
	 * @param fr the front right motor of the drive train
	 * @param fl the front left motor of the drive train
	 * @param mr the middle right motor of the drive train
	 * @param ml the middle left motor of the drive train
	 * @param rr the rear right motor of the drive train
	 * @param rl the right left motor of the drive train
	 * @param navx the NavX board
	 * @param shifter the <code>DoubleSolenoid</code> used for shifting
	 * @param left the left encoder
	 * @param right the right encoder
	 * @param rsensor the right photosensor
	 * @param lsensor the left photosensor
	 * @param ultrasonic the ultrasonic sensor
	 */
	public TankDrive(CANTalon fr, CANTalon fl, CANTalon mr, CANTalon ml, CANTalon rr, CANTalon rl, AHRS navx,
			DoubleSolenoid shifter, Encoder left, Encoder right, DigitalInput rsensor, DigitalInput lsensor, Ultrasonic ultrasonic)
	{
		this.fr = fr;
		this.fl = fl;
		this.mr = mr;
		this.ml = ml;
		this.rr = rr;
		this.rl = rl;
		this.navx = navx;
		this.shifter = shifter;
		this.left = left;
		this.right = right;
		this.rsensor = rsensor;
		this.lsensor = lsensor;
		this.ultrasonic = ultrasonic;
		
		shiftLow();
		
		setPIDSourceType(PIDSourceType.kDisplacement);
		
		yawPID = new PIDController(P, I, D, this, this);
		yawPID.setInputRange(-180.0, 180.0);
		yawPID.setOutputRange(-1.0, 1.0);
		yawPID.setContinuous();
		yawPID.setAbsoluteTolerance(3);
		disablePID();
	}
	
	/**
	 * Sets the left and right sides of the drive train 
	 * to the specified outputs
	 * @param left the output for the left side
	 * @param right the output for the right side
	 */
	public void set(double left, double right)
	{
		left = normalizeOutput(left);
		right = normalizeOutput(right);
		
		fr.set(right);
		fl.set(left);
		mr.set(right);
		ml.set(left);
		rr.set(right);
		rl.set(left);
	}
	
	/**
	 * Shifts the drive train. If the drive train is in low gear, it sets
	 * to high gear, otherwise it sets to low gear
	 */
	public void shift()
	{
		if(LOW_GEAR.equals(currentGear))
			shiftHigh();
		else 
			shiftLow();
	}
	
	/**
	 * Shifts to low gear if in high gear
	 */
	public void shiftLow()
	{
		if(LOW_GEAR.equals(currentGear))
			return;
		
		shifter.set(LOW_GEAR);
		currentGear = LOW_GEAR;
	}
	
	/**
	 * Shifts to high gear if in low gear
	 */
	public void shiftHigh()
	{
		if(HIGH_GEAR.equals(currentGear))
			return;
		
		shifter.set(HIGH_GEAR);
		currentGear = HIGH_GEAR;
	}
	
	/**
	 * Gets the current range of the ultrasonic in inches
	 * @return the current range of the ultrasonic in inches
	 */
	public double getUltrasonicDistance()
	{
		return ultrasonic.getRangeInches();
	}
	
	/**
	 * Gets the right drive encoder's distance
	 * @return the right drive encoder's distance
	 */
	public double getRightDistance()
	{
		return right.getDistance();
	}
	
	/**
	 * Gets the left drive encoder's distance
	 * @return the left drive encoder's distance
	 */
	public double getLeftDistance()
	{
		return left.getDistance();
	}
	
	/**
	 * Resets the encoders, should only be used when testing
	 */
	public void resetEncoders()
	{
		left.reset();
		right.reset();
	}
	
	/**
	 * Resets the NavX, should only be used when testing
	 */
	public void resetNavX()
	{
		navx.reset();
	}
	
	/**
	 * Gets the current heading of the robot
	 * @return the current heading of the robot ranging from -180.0 to 180.0 degrees
	 */
	public double getHeading()
	{
		return normalizeYaw(navx.getYaw() + offset);
	}
	
	/**
	 * Sets the internal PID conroller's setpoint to the specified yaw and enables PID
	 * @param yaw the yaw to orient the robot to
	 */
	public void setHeading(double yaw)
	{
		yawPID.setSetpoint(normalizeYaw(yaw));
		enablePID();
	}
	
	/**
	 * Enables the internal PID controller
	 */
	public void enablePID()
	{
		yawPID.enable();
	}
	
	/**
	 * Disables the internal PID controller if it's enabled
	 */
	public void disablePID()
	{
		if(yawPID.isEnabled())
		{
			yawPID.disable();
			direction = 0;
		}
	}
	
	/**
	 * Returns if the internal PID controller is enabled
	 * @return true if the internal PID controller is enabled, false otherwise
	 */
	public boolean pidEnabled()
	{
		return yawPID.isEnabled();
	}
	
	/**
	 * Orients the robot to a certain yaw and then goes straight
	 * @param direction the speed to go straight ranging from -1.0 to 1.0
	 * @param yaw the yaw to orient and hold
	 */
	public void goStraight(double direction, double yaw)
	{
		this.setHeading(normalizeYaw(yaw));
		this.setDirection(direction);
		enablePID();
	}

	/**
	 * Sets the output of the drive train to go forwards or backwards
	 * when using the PID controller to hold a specified yaw
	 * @param direction the output ranging from -1.0 to 1.0
	 */
	public void setDirection(double direction)
	{
		this.direction = direction;
	}
	
	/**
	 * Sets the yaw offset to return the proper values from the NavX
	 * @param offset the offset ranging from -180.0 to 180.0
	 */
	public void setYawOffset(double offset)
	{
		this.offset = offset;
	}
	
	/**
	 * Gets the current state of the right photosensor
	 * @return true if the photosensor is detecting a reflection, false otherwise
	 */
	public boolean getRightSensor()
	{
		return rsensor.get();
	}
	
	/**
	 * Gets the current state of the right photosensor
	 * @return true if the photosensor is detecting a reflection, false otherwise
	 */
	public boolean getLeftSensor()
	{
		return lsensor.get();
	}
	
	/**
	 * Gets if the internal PID controller is on target with its setpoint
	 * within a tolerance of three degrees
	 * @return true if the internal PID controller is at its setpoint, false otherwise
	 */
	public boolean onTarget()
	{
		return yawPID.onTarget();
	}
	
	/**
	 * Sets the internal PID controller's PIDSourceType
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		pidSourceType = pidSource;
	}
	
	/**
	 * Gets the internal PID Controller's current PIDSourceType
	 * @return the internal PID Controller's current PIDSourceType
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return pidSourceType;
	}
	
	/**
	 * Gets the heading of the robot
	 * @return the current yaw of the robot
	 */
	@Override
	public double pidGet()
	{
		if(pidSourceType == PIDSourceType.kRate)
			return navx.getRate();
		else
			return this.getHeading();
	}
	
	/**
	 * Overridden method to specify how the PIDController should output
	 * to the drive train
	 */
	@Override
	public void pidWrite(double output)
	{
		double left = direction + output;
		double right = direction - output;
		
		this.set(-left, right);
	}
	
	/**
	 * Calculates the remaining distance the robot needs to drive before
	 * reaching the desired distance
	 * @param distance the desired distance (in inches)
	 * @param initialLeft the initial left encoder distance (in inches, basically a snapshot of {@link #getLeftDistance()})
	 * @param initialRight the initial right encoder distance (in inches, basically a snapshot of {@link #getRightDistance()})
	 * @return the remaining distance the robot needs to drive
	 */
	public double remainingDistance(double distance, double initialLeft, double initialRight)
	{
		final double CURRENT_RIGHT_VAL = Math.abs(getRightDistance());
		final double CURRENT_LEFT_VAL = Math.abs(getLeftDistance());
		final double DELTA_RIGHT = Math.abs(CURRENT_RIGHT_VAL - initialRight);
		final double DELTA_LEFT = Math.abs(CURRENT_LEFT_VAL - initialLeft);
		
		final double AVERAGE = (DELTA_RIGHT + DELTA_LEFT) / 2;
		
		final double REMAINING = distance - AVERAGE;
		
		return REMAINING;
	}
	
	/**
	 * <p>Sets the default command to <code>DriveWithJoysticks</code></p>
	 * 
	 * {@inheritDoc}
	 * @see DriveWithJoysticks
	 */
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(new DriveWithJoysticks(IO.getLeftDriveStick(), IO.getRightDriveStick(), IO.getDrive()));
	}
	
	/**
	 * Internal function to normalize a motor output
	 * @param output the unnormalized output
	 * @return the normalized output ranging from -1.0 to 1.0
	 */
	private static double normalizeOutput(double output)
	{
		if(output > 1)
			return 1.0;
		else if(output < -1)
			return -1.0;
		return output;
	}
	
	/**
	 * Internal function to normalize yaw
	 * @param yaw the unnormalized yaw
	 * @return the normalized yaw ranging from -180.0 o 180.0
	 */
	private static double normalizeYaw(double yaw)
	{
		while(yaw >= 180)
			yaw -= 360;
		while(yaw <= -180)
			yaw += 360;
		
		return yaw;
	}
}
