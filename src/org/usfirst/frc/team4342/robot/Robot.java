package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.teleop.AlignHook;
import org.usfirst.frc.team4342.robot.commands.teleop.DriveWithJoystick;
import org.usfirst.frc.team4342.robot.commands.teleop.PlaceGearWithSwitchBox;
import org.usfirst.frc.team4342.robot.commands.teleop.ShootWithSwitchBox;
import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
	private DriveWithJoystick drive;
	private ShootWithSwitchBox shooter;
	private PlaceGearWithSwitchBox gearPlacer;
	private AlignHook hookAlign;
	
	private SendableChooser<String> autonomousChooser;
	private Command autonomousRoutine;
	
	private boolean removedTeleopCommands = true;
	
	@Override
	public void robotInit()
	{
		IO.initialize();
		DemonDashboard.start();
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		
		drive = new DriveWithJoystick(IO.getDriveSitck(), IO.getDrive());
		//shooter = new ShootWithSwitchBox(IO.getSwitchBox(), IO.getShooter());
		//gearPlacer = new PlaceGearWithSwitchBox(IO.getSwitchBox(), IO.getGearPlacer());
		
		autonomousChooser = new SendableChooser<String>();
		autonomousChooser.addDefault("None", null);
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
		if (!drive.isRunning() && !IO.alignHookIsRunning())
			drive.start();
		
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit()
	{
		stopTeleopCommands();
		
		String routine = autonomousChooser.getSelected();
		switch(routine)
		{
			default:
				autonomousRoutine = null;
		}
		
		if(autonomousRoutine != null)
			autonomousRoutine.start();
		
		SmartDashboard.putString("AutoCommand", drive.getName());
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
			//shooter.start();
			//gearPlacer.start();
			
			removedTeleopCommands = false;
		}
	}
	
	private void stopTeleopCommands()
	{
		if(!removedTeleopCommands)
		{
			drive.cancel();
			//shooter.cancel();
			//gearPlacer.cancel();
			
			removedTeleopCommands = true;
		}
	}
}
