package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearLeft;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearMiddle;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGearRight;
import org.usfirst.frc.team4342.robot.commands.teleop.AlignHook;
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
	private AlignHook alignHook;
	
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
		Logger.info("Bootstrapping Demonator6...");
		
		IO.initialize();
		DemonDashboard.start();
		PDPLogger.start();
		
		Logger.info("Initializing teleop commands...");
		drive = new DriveWithJoysticks(IO.getLeftDriveStick(), IO.getRightDriveStick(), IO.getDrive());
		shooter = new ShootWithSwitchBox(IO.getSwitchBox(), new JoystickButton(IO.getRightDriveStick(), ButtonMap.DriveStick.Right.ACCUMULATE), IO.getShooter());
		gearPlacer = new PlaceGearWithSwitchBox(IO.getSwitchBox(), IO.getGearPlacer());
		scaler = new Scale(IO.getScaler(), new JoystickButton(IO.getSwitchBox(), ButtonMap.SwitchBox.Scaler.SCALE));
		alignHook = new AlignHook(IO.getDrive());
		
		Logger.info("Initializing autonomous routines...");
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
		stopAutonomousRoutine();
		startTeleopCommands();
	}
	
	/**
	 * Periodic code for teleop (operator control) mode
	 */
	@Override
	public void teleopPeriodic()
	{
		if(IO.getRightDriveStick().getRawButton(6))
			IO.navx.reset();
		if(IO.getRightDriveStick().getRawButton(7))
			alignHook.start();
		
		Scheduler.getInstance().run();
	}
	
	/**
	 * Initialization code for autonomous mode 
	 */
	@Override
	public void autonomousInit()
	{
		stopAutonomousRoutine();
		stopTeleopCommands();
		
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
	
	/**
	 * Starts the autonomous routine
	 */
	private void startAutonomousRoutine()
	{
		if(autonomousRoutine != null && !autonomousRoutine.isRunning())
			autonomousRoutine.start();
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
