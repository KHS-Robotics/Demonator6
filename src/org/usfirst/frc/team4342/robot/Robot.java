package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.auton.AlignHook;
import org.usfirst.frc.team4342.robot.commands.auton.routines.AutonomousRoutine;
import org.usfirst.frc.team4342.robot.commands.auton.routines.CrossBaseline;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGear;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearAndShootFuel;
import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * FRC Team 4342's robot code for Steamworks
 * 
 * @author Liam Briening
 * @author Harry Lehr
 * @author Jacob Rainbow
 * @author Katie Schuetz
 * @author Harrison Stankiewicz
 * @author Brian Lucas
 * @author Ernie Wilson
 */
public class Robot extends IterativeRobot 
{
	// Teleop commands
	private AlignHook alignHook;
	
	// Autonomous chooser and routine
	private SendableChooser<AutonomousRoutine> autonomousChooser;
	private AutonomousRoutine autonomousRoutine;
	
	/**
	 * Robot-wide initialization code
	 */
	@Override
	public void robotInit()
	{
		Logger.info("Bootstrapping Demonator6...");
		
		IO.initialize();
		DemonDashboard.start();
		
		alignHook = new AlignHook(IO.getDrive());
		
		Logger.info("Initializing autonomous routines...");
		autonomousChooser = new SendableChooser<AutonomousRoutine>();
		autonomousChooser.addDefault("None", null);
		autonomousChooser.addObject("Place Middle Gear", new PlaceGear(IO.getDrive(), IO.getGearPlacer(), AlignHook.Location.MIDDLE));
		autonomousChooser.addObject("Place Left Gear", new PlaceGear(IO.getDrive(), IO.getGearPlacer(), AlignHook.Location.RIGHT));
		autonomousChooser.addObject("Place Right Gear", new PlaceGear(IO.getDrive(), IO.getGearPlacer(), AlignHook.Location.LEFT));
		autonomousChooser.addObject("Place Gear & Shoot Fuel (Blue)", new PlaceGearAndShootFuel(IO.getDrive(), IO.getGearPlacer(), IO.getShooter(), Alliance.Blue));
		autonomousChooser.addObject("Place Gear & Shoot Fuel (Red)", new PlaceGearAndShootFuel(IO.getDrive(), IO.getGearPlacer(), IO.getShooter(), Alliance.Red));
		autonomousChooser.addObject("Cross Baseline", new CrossBaseline(IO.getDrive()));
		SmartDashboard.putData("Autonomous Chooser", autonomousChooser);
		
		Logger.info("Finished bootstrapping Demonator6.");
	}
	
	/**
	 * Initialization code for teleop (operator control) mode
	 */
	@Override
	public void teleopInit()
	{
		stopAutonomousRoutine();
	}

	/**
	 * Periodic code for teleop (operator control) mode
	 */
	@Override
	public void teleopPeriodic()
	{
		if(IO.getSwitchBox().getRawButton(ButtonMap.SwitchBox.RESET)) // for testing purposes
		{
			IO.getDrive().resetNavX();
			IO.getDrive().resetEncoders();
		}
		
		if(!alignHook.isRunning())
		{
			if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Left.ALIGN_HOOK_LEFT))
				alignHook.start(AlignHook.Location.RIGHT);
			else if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Left.ALIGN_HOOK_MIDDLE))
				alignHook.start(AlignHook.Location.MIDDLE);
			else if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Left.ALIGN_HOOK_RIGHT))
				alignHook.start(AlignHook.Location.LEFT);
		}
		else
		{
			if(IO.shouldCancelAutoCommand())
				alignHook.cancel();
		}
		
		Scheduler.getInstance().run();
	}
	
	/**
	 * Initialization code for autonomous mode 
	 */
	@Override
	public void autonomousInit()
	{
		stopAutonomousRoutine();
		alignHook.cancel();

		autonomousRoutine = autonomousChooser.getSelected();
		
		startAutonomousRoutine();
	}
	
	/**
	 * Periodic code for autonomous mode
	 */
	@Override
	public void autonomousPeriodic()
	{
		if(autonomousRoutine != null)
			Scheduler.getInstance().run();
	}
	
	/**
	 * Initialization code for test mode 
	 */
	@Override
	public void testInit()
	{
		stopAutonomousRoutine();
		alignHook.cancel();
		Scheduler.getInstance().run();
	}
	
	/**
	 * Periodic code for test mode
	 */
	@Override
	public void testPeriodic()
	{
		LiveWindow.run();
	}
	
	/**
	 * Initialization code for disabled mode
	 */
	@Override
	public void disabledInit()
	{
		stopAutonomousRoutine();
		alignHook.cancel();
		
		Scheduler.getInstance().run();
	}
	
	/**
	 * Starts the autonomous routine
	 */
	private void startAutonomousRoutine()
	{
		if(autonomousRoutine != null && !autonomousRoutine.isRunning())
		{
			IO.getDrive().resetNavX();
			autonomousRoutine.start();
		}
	}
	
	/**
	 * Stops the autonomous routine
	 */
	private void stopAutonomousRoutine()
	{
		if(autonomousRoutine != null)
			autonomousRoutine.cancel();
	}
}
