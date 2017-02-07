package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearLeft;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearMiddle;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearRight;
import org.usfirst.frc.team4342.robot.commands.teleop.DriveWithJoystick;
import org.usfirst.frc.team4342.robot.commands.teleop.PlaceGearWithSwitchBox;
import org.usfirst.frc.team4342.robot.commands.teleop.ShootWithSwitchBox;
import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
	private DriveWithJoystick drive;
	private ShootWithSwitchBox shooter;
	private PlaceGearWithSwitchBox gearPlacer;
	
	private SendableChooser<CommandGroup> autonomousChooser;
	private CommandGroup autonomousRoutine;
	
	private boolean removedTeleopCommands = true;
	
	@Override
	public void robotInit()
	{
		IO.initialize();
		DemonDashboard.start();
		
		drive = new DriveWithJoystick(IO.getLeftDriveStick(), IO.getRightDriveStick(), IO.getDrive());
		shooter = new ShootWithSwitchBox(IO.getSwitchBox(), IO.getShooter());
		gearPlacer = new PlaceGearWithSwitchBox(IO.getSwitchBox(), IO.getGearPlacer());
		
		autonomousChooser = new SendableChooser<CommandGroup>();
		autonomousChooser.addDefault("None", null);
		autonomousChooser.addObject("Place Middle Gear", new PlaceGearMiddle(IO.getDrive(), IO.getGearPlacer()));
		autonomousChooser.addObject("Place Left Gear", new PlaceGearLeft(IO.getDrive(), IO.getGearPlacer()));
		autonomousChooser.addObject("Place Right Gear", new PlaceGearRight(IO.getDrive(), IO.getGearPlacer()));
		SmartDashboard.putData("Autonomous Chooser", autonomousChooser);
			
		Logger.info("Finished bootstrapping Demonator6.");
	}
	
	@Override
	public void teleopInit()
	{
		if(autonomousRoutine != null && autonomousRoutine.isRunning())
			autonomousRoutine.cancel();
		
		startTeleopCommands();
	}
	
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit()
	{
		stopTeleopCommands();
		
		autonomousRoutine = autonomousChooser.getSelected();
		
		if(autonomousRoutine != null)
			autonomousRoutine.start();
	}
	
	@Override
	public void autonomousPeriodic()
	{
		if(autonomousRoutine != null)
			Scheduler.getInstance().run();
	}
	
	@Override
	public void testInit()
	{
		stopTeleopCommands();
	}
	
	@Override
	public void testPeriodic()
	{
		LiveWindow.run();
	}
	
	private void startTeleopCommands()
	{
		if(removedTeleopCommands)
		{
			drive.start();
			shooter.start();
			gearPlacer.start();
			
			removedTeleopCommands = false;
		}
	}
	
	private void stopTeleopCommands()
	{
		if(!removedTeleopCommands)
		{
			drive.cancel();
			shooter.cancel();
			gearPlacer.cancel();
			
			removedTeleopCommands = true;
		}
	}
}
