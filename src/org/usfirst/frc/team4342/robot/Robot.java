package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot 
{
	@Override
	public void robotInit()
	{
		IO.initialize();
		DemonDashboard.start();
		
		Logger.info("Finished bootstrapping Demonator6.");
	}

	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testPeriodic()
	{
		LiveWindow.run();
	}
}
