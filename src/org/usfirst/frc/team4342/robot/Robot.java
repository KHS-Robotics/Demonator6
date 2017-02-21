package org.usfirst.frc.team4342.robot;

import org.usfirst.frc.team4342.robot.commands.auton.AlignHook;
import org.usfirst.frc.team4342.robot.commands.auton.routines.AutonomousRoutine;
import org.usfirst.frc.team4342.robot.commands.auton.routines.CrossBaseline;
import org.usfirst.frc.team4342.robot.commands.auton.routines.PlaceGear;
import org.usfirst.frc.team4342.robot.commands.teleop.DriveWithJoysticks;
import org.usfirst.frc.team4342.robot.commands.teleop.PlaceGearWithSwitchBox;
import org.usfirst.frc.team4342.robot.commands.teleop.Scale;
import org.usfirst.frc.team4342.robot.commands.teleop.ShootWithSwitchBox;
import org.usfirst.frc.team4342.robot.logging.DemonDashboard;
import org.usfirst.frc.team4342.robot.logging.Logger;
import org.usfirst.frc.team4342.robot.logging.PDPLogger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
	private SendableChooser<AutonomousRoutine> autonomousChooser;
	private SendableChooser<Boolean> useDeadReckoningChooser;
	private AutonomousRoutine autonomousRoutine;
	
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
		drive = new DriveWithJoysticks(IO.getLeftDriveStick(), IO.getRightDriveStick(), new JoystickButton(IO.getRightDriveStick(), ButtonMap.DriveStick.Right.BOILER_YAW), IO.getDrive());
		shooter = new ShootWithSwitchBox(IO.getSwitchBox(), new JoystickButton(IO.getRightDriveStick(), ButtonMap.DriveStick.Right.ACCUMULATE), IO.getShooter());
		gearPlacer = new PlaceGearWithSwitchBox(IO.getSwitchBox(), IO.getGearPlacer());
		scaler = new Scale(IO.getScaler(), new JoystickButton(IO.getSwitchBox(), ButtonMap.SwitchBox.Scaler.SCALE));
		alignHookLeft = new AlignHook(IO.getDrive(), AlignHook.Location.LEFT);
		alignHookMiddle = new AlignHook(IO.getDrive(), AlignHook.Location.MIDDLE);
		alignHookRight = new AlignHook(IO.getDrive(), AlignHook.Location.RIGHT);
		
		Logger.info("Initializing autonomous routines...");
		autonomousChooser = new SendableChooser<AutonomousRoutine>();
		autonomousChooser.addDefault("None", null);
		autonomousChooser.addObject("Place Middle Gear", new PlaceGear(IO.getDrive(), IO.getGearPlacer(), AlignHook.Location.MIDDLE));
		autonomousChooser.addObject("Place Left Gear", new PlaceGear(IO.getDrive(), IO.getGearPlacer(), AlignHook.Location.LEFT));
		autonomousChooser.addObject("Place Right Gear", new PlaceGear(IO.getDrive(), IO.getGearPlacer(), AlignHook.Location.RIGHT));
		autonomousChooser.addObject("Cross Baseline", new CrossBaseline(IO.getDrive()));
		SmartDashboard.putData("Autonomous Chooser", autonomousChooser);
		
		useDeadReckoningChooser = new SendableChooser<Boolean>();
		useDeadReckoningChooser.addDefault("Use Dead Reckoning", true);
		useDeadReckoningChooser.addObject("Use Align Hook", false);
		SmartDashboard.putData("Use Dead Reckoning", useDeadReckoningChooser);
			
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
			if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Left.ALIGN_HOOK_LEFT))
				startAlignHookCommand(alignHookLeft);
			else if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Left.ALIGN_HOOK_MIDDLE))
				startAlignHookCommand(alignHookMiddle);
			else if(IO.getLeftDriveStick().getRawButton(ButtonMap.DriveStick.Left.ALIGN_HOOK_RIGHT))
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
		
		boolean useDeadReckoning = useDeadReckoningChooser.getSelected();
		autonomousRoutine = autonomousChooser.getSelected();
		autonomousRoutine.setUseDeadReckoning(useDeadReckoning);
		
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
