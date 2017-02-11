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
		/**
		 * Puts data to the Smart Dashboard every 20ms. The data is retrieved from IO.java
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
					SmartDashboard.putNumber("NavX-Yaw", IO.getDrive().getHeading());
					SmartDashboard.putBoolean("Photo-Right", IO.getDrive().getRightSensor());
					SmartDashboard.putBoolean("Photo-Left", IO.getDrive().getLeftSensor());
					SmartDashboard.putBoolean("hasScaled", IO.getScaler().hasScaled());
					
					Thread.sleep(20);
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
