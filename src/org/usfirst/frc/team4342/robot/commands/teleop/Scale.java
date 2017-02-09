package org.usfirst.frc.team4342.robot.commands.teleop;

import org.usfirst.frc.team4342.robot.subsystems.Scaler;
import org.usfirst.frc.team4342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class Scale extends Command
{
	private Scaler scaler;
	private TankDrive drive;
	
	public Scale(Scaler scaler, TankDrive drive)
	{
		super(10);
		
		this.requires(scaler);
		this.requires(drive);
		
		this.scaler = scaler;
		this.drive = drive;
		
		this.setInterruptible(false);
	}
	
	@Override
	protected boolean isFinished() 
	{
		return scaler.hasScaled() || this.isTimedOut();
	}
	
	@Override
	public void initialize()
	{
		drive.goStraight(0.25, drive.getYaw());
		scaler.enable();
	}
	
	@Override
	protected void execute()
	{

	}
	
	@Override
	protected void end()
	{
		scaler.disable();
		drive.disablePID();
	}
}
