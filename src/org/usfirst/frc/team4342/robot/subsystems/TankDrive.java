package org.usfirst.frc.team4342.robot.subsystems;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Tank Drive subsystem to control the drive train
 */
public class TankDrive extends DemonSubsystem implements PIDOutput
{
	private static final double P = 0.04, I = 0.0001, D = 0.012;
	private static final Value HIGH_GEAR = Value.kForward, LOW_GEAR = Value.kReverse;
	private static final double MAX_SHIFT_LOW_MOTOR_OUTPUT = 0.25;
	
	private CANTalon fr, fl, rr, rl, mr , ml;
	private AHRS navx;
	private DoubleSolenoid shifter;
	private Encoder left, right;
	private DigitalInput rsensor, lsensor;
	private AnalogInput ultrasonic;
	private PIDController yawPID;
	
	private Value currentGear;
	private double direction;
	
	private boolean leftDead, rightDead;
	
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
			DoubleSolenoid shifter, Encoder left, Encoder right, DigitalInput rsensor, DigitalInput lsensor, AnalogInput ultrasonic)
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
		this.leftDead = false;
		this.rightDead = false;
		
		shiftLow();
		
		yawPID = new PIDController(P, I, D, navx, this);
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
	 * Shifts the drive trian. If the drive train is in low gear, it sets
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
		if(LOW_GEAR.equals(currentGear) || isMoving(MAX_SHIFT_LOW_MOTOR_OUTPUT))
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
		double mm = ((ultrasonic.getVoltage() - .293)/(.000977)) + 300;
		double  inch = mm / 25.4;
		return inch;
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
		return navx.getYaw();
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
			yawPID.disable();
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
		navx.setAngleAdjustment(normalizeYaw(offset));
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
	 * Gets if the left side of the drive train is moving
	 * @return true if the left side of the drive train is moving, false otherwise
	 */
	public boolean leftIsActive()
	{
		return !left.getStopped();
	}
	
	/**
	 * Gets if the right side of the drive train is moving
	 * @return true if the right side of the drive train is moving, false otherwise
	 */
	public boolean rightIsActive()
	{
		return !right.getStopped();
	}
	
	/**
	 * Gets if the left encoder for the left side 
	 * of the drive train is dead
	 * @return true if the left encoder for the drive is dead, false otherwise
	 */
	public boolean leftIsDead()
	{
		return leftDead;
	}
	
	/**
	 * Gets if the right encoder for the left side 
	 * of the drive train is dead
	 * @return true if the right encoder for the drive is dead, false otherwise
	 */
	public boolean rightIsDead()
	{
		return rightDead;
	}
	
	/**
	 * Sets the left encoder to dead or alive
	 * @param dead true if dead, false otherwise
	 */
	public void setLeftDead(boolean dead)
	{
		leftDead = dead;
	}
	
	/**
	 * Sets the right encoder to dead or alive
	 * @param dead true if dead, false otherwise
	 */
	public void setRightDead(boolean dead)
	{
		rightDead = dead;	
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
		final double CURRENT_RIGHT_VAL = getRightDistance();
		final double CURRENT_LEFT_VAL = getLeftDistance();
		final double RIGHT_VAL = Math.abs(CURRENT_RIGHT_VAL - initialRight);
		final double LEFT_VAL = Math.abs(CURRENT_LEFT_VAL - initialLeft);
		
		final double AVERAGE = (RIGHT_VAL + LEFT_VAL) / 2;
		
//		if (leftIsDead())
//		{
//			return Math.abs(RIGHT_VAL - initialRight);
//		}
//		else if (rightIsDead())
//		{
//			return Math.abs(LEFT_VAL - initialLeft);
//		}
		
		final double TOTAL = distance -AVERAGE;
//		final double TOTAL = (Math.abs(LEFT_VAL - initialLeft) + Math.abs(RIGHT_VAL - initialRight)) / 2;
//		
//		if (TOTAL > distance / 4)
//		{
//			if (!leftIsActive())
//			{
//				setLeftDead(true);
//				return Math.abs(RIGHT_VAL - initialRight);
//			}
//			else if (!rightIsActive())
//			{
//				setRightDead(true);
//				return Math.abs(LEFT_VAL - initialLeft);
//			}
//		}
		
		return TOTAL;
	}
	
	/**
	 * Gets if the drive train's outputs are above the given threshold
	 * @param thresholdOutput the threshold to determine if a wheel is moving
	 * @return true if any of the Talon's outputs are above the given threshold, false otherwise
	 */
	public boolean isMoving(double thresholdOutput)
	{
		thresholdOutput = normalizeOutput(thresholdOutput);
		
		final boolean FRONT_RIGHT = Math.abs(fr.get()) > thresholdOutput;
		final boolean FRONT_LEFT = Math.abs(fl.get()) > thresholdOutput;
		final boolean MIDDLE_RIGHT = Math.abs(mr.get()) > thresholdOutput;
		final boolean MIDDLE_LEFT = Math.abs(ml.get()) > thresholdOutput;
		final boolean REAR_RIGHT = Math.abs(rr.get()) > thresholdOutput;
		final boolean REAR_LEFT = Math.abs(rl.get()) > thresholdOutput;
		
		return FRONT_RIGHT || FRONT_LEFT || MIDDLE_RIGHT || MIDDLE_LEFT || REAR_RIGHT || REAR_LEFT;
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
