package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
	private SendableChooser<String> autonomousChooser;
	private Command autonomousRoutine;
	
	@Override
	public void robotInit()
	{
		IO.initialize();
		DemonDashboard.start();
		
		autonomousChooser = new SendableChooser<String>();
		autonomousChooser.addDefault("None", null);
		SmartDashboard.putData("Autonomous Chooser", autonomousChooser);
			
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
		
		String routine = autonomousChooser.getSelected();
		switch(routine)
		{
			default:
				autonomousRoutine = null;
		}
		
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
	public void disabledInit()
	{
		if(autonomousRoutine != null && autonomousRoutine.isRunning())
			autonomousRoutine.cancel();
	}
	
	@Override
	public void testPeriodic()
	{
		LiveWindow.run();
	}
}
