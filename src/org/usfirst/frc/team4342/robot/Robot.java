package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;
import org.usfirst.frc.team4342.robot.subsystems.drive.DriveWithJoystick;
import org.usfirst.frc.team4342.robot.subsystems.gearplacer.PlaceGearWithSwitchBox;
import org.usfirst.frc.team4342.robot.subsystems.shooter.ShootWithSwitchBox;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot 
{
	private DriveWithJoystick drive;
	private ShootWithSwitchBox shooter;
	private PlaceGearWithSwitchBox gearPlacer;
	
	private static boolean ranTest;
	
	@Override
	public void robotInit()
	{
		IO.initialize();
		DemonDashboard.start();
		
		drive = new DriveWithJoystick();
		shooter = new ShootWithSwitchBox();
		gearPlacer = new PlaceGearWithSwitchBox();
		
		Scheduler.getInstance().add(drive);
		Scheduler.getInstance().add(shooter);
		Scheduler.getInstance().add(gearPlacer);
			
		Logger.info("Finished bootstrapping Demonator6.");
	}

	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit()
	{
		Scheduler.getInstance().removeAll();
	}
	
	@Override
	public void disabledInit()
	{
		if(ranTest)
		{
			Scheduler.getInstance().add(drive);
			Scheduler.getInstance().add(shooter);
			Scheduler.getInstance().add(gearPlacer);
			ranTest = false;
		}
	}
	
	@Override
	public void testInit()
	{
		ranTest = true;
	}
	
	@Override
	public void testPeriodic()
	{
		LiveWindow.run();
	}
}
