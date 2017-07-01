package org.usfirst.frc.team4342.robot.logging;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class to put data to the smart dashboard on a separate thread
 */
public class DemonDashboard 
{
	private DemonDashboard() {}
	
	private static boolean running;
	
	/**
	 * Starts the <code>DemonDashboard</code> on a new thread.
	 */
	public static void start()
	{
		if(running)
			return;
		
		Logger.info("Starting DemonDashboard...");
		new DemonDashboardThread().start();
		
		running = true;
	}
	
	/**
	 * Stops the <code>DemonDashboard</code>
	 */
	public static void stop()
	{
		running = false;
	}
	
	/**
	 * The magic behind this class...
	 */
	private static class DemonDashboardThread extends Thread implements Runnable
	{
		private static final IO io = IO.getInstance();
		
		/**
		 * Puts data to the Smart Dashboard every 50ms. The data is retrieved from IO.java
		 * @see org.usfirst.frc.team4342.robot.IO
		 */
		@Override
		public void run()
		{
			SmartDashboard.putBoolean("DemonDashboard", true);
			
			while(running)
			{
				try
				{
					SmartDashboard.putNumber("NavX-Yaw", io.Drive.getHeading());
					SmartDashboard.putBoolean("Photo-Right", io.Drive.getRightSensor());
					SmartDashboard.putBoolean("Photo-Left", io.Drive.getLeftSensor());
					SmartDashboard.putNumber("Shooter-Speed ", io.Shooter.getSpeed());
					SmartDashboard.putNumber("Drive-Enc-Right", io.Drive.getRightDistance());
					SmartDashboard.putNumber("Drive-Enc-Left", io.Drive.getLeftDistance());
					SmartDashboard.putNumber("Drive-Ultra-Dist", io.Drive.getUltrasonicDistance());
					SmartDashboard.putBoolean("Shooter-Solenoid", io.Shooter.isSetFar());
					
					Thread.sleep(50);
				}
				catch(Exception ex)
				{
					Logger.error("DemonDashboard crashed!", ex);
					DemonDashboard.stop();
				}
			}
			
			SmartDashboard.putBoolean("DemonDashboard", false);
		}
	}
}
