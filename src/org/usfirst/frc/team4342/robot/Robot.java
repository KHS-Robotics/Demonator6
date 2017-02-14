package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearLeft;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearMiddle;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearRight;
import org.usfirst.frc.team4342.robot.commands.teleop.DriveWithJoysticks;
import org.usfirst.frc.team4342.robot.commands.teleop.PlaceGearWithSwitchBox;
import org.usfirst.frc.team4342.robot.commands.teleop.Scale;
import org.usfirst.frc.team4342.robot.commands.teleop.ShootWithSwitchBox;
import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;
import org.usfirst.frc.team4342.robot.logging.PDPLogger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.CommandGroup;
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
	private DriveWithJoysticks drive;
	private ShootWithSwitchBox shooter;
	private PlaceGearWithSwitchBox gearPlacer;
	private Scale scaler;
	
	// Autonomous chooser and routine
	private SendableChooser<CommandGroup> autonomousChooser;
	private CommandGroup autonomousRoutine;
	
	private boolean startedTeleopCommands;
	
	/**
	 * Robot-wide initialization code
	 */
	@Override
	public void robotInit()
	{
		IO.initialize();
		DemonDashboard.start();
		PDPLogger.start();
		
		drive = new DriveWithJoysticks(IO.getLeftDriveStick(), IO.getRightDriveStick(), IO.getDrive());
		shooter = new ShootWithSwitchBox(IO.getSwitchBox(), IO.getShooter());
		gearPlacer = new PlaceGearWithSwitchBox(IO.getSwitchBox(), IO.getGearPlacer());
		scaler = new Scale(IO.getScaler(), new JoystickButton(IO.getRightDriveStick(), ButtonMap.DriveStick.Right.SCALE));
		
		autonomousChooser = new SendableChooser<CommandGroup>();
		autonomousChooser.addDefault("None", null);
		autonomousChooser.addObject("Place Middle Gear", new PlaceGearMiddle(IO.getDrive(), IO.getGearPlacer()));
		autonomousChooser.addObject("Place Left Gear", new PlaceGearLeft(IO.getDrive(), IO.getGearPlacer()));
		autonomousChooser.addObject("Place Right Gear", new PlaceGearRight(IO.getDrive(), IO.getGearPlacer()));
		SmartDashboard.putData("Autonomous Chooser", autonomousChooser);
			
		Logger.info("Finished bootstrapping Demonator6.");
	}
	
	/**
	 * Initialization code for teleop (operator control) mode
	 */
	@Override
	public void teleopInit()
	{
		if(autonomousRoutine != null && autonomousRoutine.isRunning())
			autonomousRoutine.cancel();
		
		startTeleopCommands();
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
		stopTeleopCommands();
		
		autonomousRoutine = autonomousChooser.getSelected();
		
		if(autonomousRoutine != null)
			autonomousRoutine.start();
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
		stopTeleopCommands();
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
	 * Starts the commands needed for operator control
	 */
	private void startTeleopCommands()
	{
		if(!startedTeleopCommands)
		{
			drive.start();
			shooter.start();
			gearPlacer.start();
			scaler.start();
			
			startedTeleopCommands = true;
		}
	}
	
	/**
	 * Stops the commands needed for operator control
	 */
	private void stopTeleopCommands()
	{
		if(startedTeleopCommands)
		{
			drive.cancel();
			shooter.cancel();
			gearPlacer.cancel();
			scaler.cancel();
			
			startedTeleopCommands = false;
		}
	}
}
