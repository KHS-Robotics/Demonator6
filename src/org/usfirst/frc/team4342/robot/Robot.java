package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.auton.AlignHook;
import org.usfirst.frc.team4342.robot.commands.auton.routines.CrossBaseline;
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
	private AlignHook alignHookLeft, alignHookMiddle, alignHookRight;
	
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
		shooter = new ShootWithSwitchBox(IO.getSwitchBox(), new JoystickButton(IO.getRightDriveStick(), ButtonMap.DriveStick.Left.ACCUMULATE), IO.getShooter());
		gearPlacer = new PlaceGearWithSwitchBox(IO.getSwitchBox(), IO.getGearPlacer());
		scaler = new Scale(IO.getScaler(), new JoystickButton(IO.getSwitchBox(), ButtonMap.SwitchBox.Scaler.SCALE));
		alignHookLeft = new AlignHook(IO.getDrive(), AlignHook.Location.LEFT);
		alignHookMiddle = new AlignHook(IO.getDrive(), AlignHook.Location.MIDDLE);
		alignHookRight = new AlignHook(IO.getDrive(), AlignHook.Location.RIGHT);
		
		Logger.info("Initializing autonomous routines...");
		autonomousChooser = new SendableChooser<CommandGroup>();
		autonomousChooser.addDefault("None", null);
		autonomousChooser.addObject("Place Middle Gear", new PlaceGearMiddle(IO.getDrive(), IO.getGearPlacer()));
		autonomousChooser.addObject("Place Left Gear", new PlaceGearLeft(IO.getDrive(), IO.getGearPlacer()));
		autonomousChooser.addObject("Place Right Gear", new PlaceGearRight(IO.getDrive(), IO.getGearPlacer()));
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
		startTeleopCommands();
	}

	/**
	 * Periodic code for teleop (operator control) mode
	 */
	@Override
	public void teleopPeriodic()
	{
		if(!alignHookIsRunning())
		{
			if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Right.ALIGN_HOOK_LEFT))
				startAlignHookCommand(alignHookLeft);
			else if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Right.ALIGN_HOOK_MIDDLE))
				startAlignHookCommand(alignHookMiddle);
			else if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Right.ALIGN_HOOK_RIGHT))
				startAlignHookCommand(alignHookRight);
			else if(!drive.isRunning())
				drive.start();
		}
		else
		{
			if(IO.shouldCancelAutoCommand())
				stopAlignHookCommands();
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
	 * Initialization code for disabled mode
	 */
	@Override
	public void disabledInit()
	{
		stopAutonomousRoutine();
		stopTeleopCommands();
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
			stopAlignHookCommands();
			
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
	
	/**
	 * Stops <code>DriveWithJoyticks</code> and starts <code>AlignHook</code>
	 * @param alignHook the align hook command to start
	 */
	private void startAlignHookCommand(AlignHook alignHook)
	{
		drive.cancel();
		alignHook.start();
	}
	
	/**
	 * Stops the align hook commands
	 */
	private void stopAlignHookCommands()
	{
		alignHookLeft.cancel();
		alignHookMiddle.cancel();
		alignHookRight.cancel();
	}
	
	/**
	 * Gets if any of the align hook commands are running
	 * @return true if an align hook command is running, false otherwise
	 */
	private boolean alignHookIsRunning()
	{
		return alignHookLeft.isRunning() || alignHookMiddle.isRunning() || alignHookRight.isRunning();
	}
}
