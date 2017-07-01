package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.auton.AutonomousRoutine;
import org.usfirst.frc.team4342.robot.auton.CrossBaseline;
import org.usfirst.frc.team4342.robot.auton.PlaceGear;
import org.usfirst.frc.team4342.robot.auton.PlaceGearAndShootFuel;
import org.usfirst.frc.team4342.robot.commands.AlignPeg;
import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;
import org.usfirst.frc.team4342.robot.logging.PDPLogger;

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
	// Autonomous chooser and routine
	private SendableChooser<Boolean> useDeadReckoningChooser;
	private SendableChooser<AutonomousRoutine> autonomousChooser;
	private AutonomousRoutine autonomousRoutine;
	
	/**
	 * Robot-wide initialization code
	 */
	@Override
	public void robotInit()
	{
		Logger.info("Bootstrapping Demonator6...");
		
		IO io = IO.getInstance();
		DemonDashboard.start();
		PDPLogger.start();
		
		Logger.info("Initializing autonomous routines...");
		autonomousChooser = new SendableChooser<AutonomousRoutine>();
		autonomousChooser.addDefault("None", null);
		autonomousChooser.addObject("Place Middle Gear", new PlaceGear(io.Drive, io.GearPlacer, AlignPeg.Location.MIDDLE));
		autonomousChooser.addObject("Place Left Gear", new PlaceGear(io.Drive, io.GearPlacer, AlignPeg.Location.RIGHT));
		autonomousChooser.addObject("Place Right Gear", new PlaceGear(io.Drive, io.GearPlacer, AlignPeg.Location.LEFT));
		autonomousChooser.addObject("Place Gear & Shoot Fuel (Blue)", new PlaceGearAndShootFuel(io.Drive, io.GearPlacer, io.Shooter, Alliance.Blue));
		autonomousChooser.addObject("Place Gear & Shoot Fuel (Red)", new PlaceGearAndShootFuel(io.Drive, io.GearPlacer, io.Shooter, Alliance.Red));
		autonomousChooser.addObject("Cross Baseline", new CrossBaseline(io.Drive));
		SmartDashboard.putData("Autonomous Chooser", autonomousChooser);
		
		useDeadReckoningChooser = new SendableChooser<Boolean>();
		useDeadReckoningChooser.addDefault("Use Dead Reckoning", true);
		useDeadReckoningChooser.addObject("Don't use Dead Reckoning", false);
		
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
		Scheduler.getInstance().run();
	}
	
	/**
	 * Initialization code for autonomous mode 
	 */
	@Override
	public void autonomousInit()
	{
		stopAutonomousRoutine();
		autonomousRoutine = autonomousChooser.getSelected();
		autonomousRoutine.setUseDeadReckoning(useDeadReckoningChooser.getSelected());
		
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
		Scheduler.getInstance().run();
	}
	
	/**
	 * Starts the autonomous routine
	 */
	private void startAutonomousRoutine()
	{
		if(autonomousRoutine != null && !autonomousRoutine.isRunning())
		{
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
