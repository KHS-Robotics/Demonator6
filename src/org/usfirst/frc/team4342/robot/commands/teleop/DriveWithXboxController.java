package org.usfirst.frc.team4342.robot.commands.teleop;

import java.util.HashMap;

import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithXboxController extends Command
{
	private HashMap<Integer, Double> pov;
	private Double currentPOV;
	
	private XboxController xboxController;
	private TankDrive drive;
	
	public DriveWithXboxController(XboxController xboxController, TankDrive drive)
	{
		super();
		
		this.requires(drive);
		
		this.xboxController = xboxController;
		this.drive = drive;
		
		pov = new HashMap<Integer, Double>();
		pov.put(-1, null);
		pov.put(0, 0.0);
		pov.put(45, 45.0);
		pov.put(90, 90.0);
		pov.put(135, 135.0);
		pov.put(180, 180.0);
		pov.put(225, -135.0);
		pov.put(270, -90.0);
		pov.put(315, -45.0);
	}
	
	@Override
	protected void execute()
	{
		final double LEFT = xboxController.getY(Hand.kLeft);
		final double RIGHT = xboxController.getY(Hand.kRight);
		final Double POV = pov.get(xboxController.getPOV());
		
		if (Math.abs(LEFT) >= 0.04 || Math.abs(RIGHT) >= 0.04)
			drive.disablePID();
		
		if (POV != null && !POV.equals(currentPOV))
		{
			drive.enablePID();
			drive.setHeading(POV);
			
			currentPOV = POV;
			
			return;
		}

		drive.set(adjust(LEFT), adjust(RIGHT));
	}
	
	@Override
	protected void end()
	{
		drive.set(0, 0);
		drive.disablePID();
	}
	
	@Override
	public void interrupted()
	{
		this.end();
	}
	
	@Override
	protected boolean isFinished() 
	{
		return false;
	}
	
	private static double adjust(double input)
	{
		final double SENSITIVITY = 0.60;
		return (SENSITIVITY * Math.pow(input, 3)+ (1 - SENSITIVITY) * input);
	}
}
