package org.usfirst.frc.team4342.robot.logging;

import org.usfirst.frc.team4342.robot.IO;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DemonDashboard 
{
	private DemonDashboard() {}
	
	private static boolean running;
	
	public static void start()
	{
		if(running)
			return;
		
		new DemonDashboardThread().start();
		
		running = true;
	}
	
	public static void stop()
	{
		running = false;
	}
	
	private static class DemonDashboardThread extends Thread implements Runnable
	{
		@Override
		public void run()
		{
			SmartDashboard.putBoolean("DemonDashboard", true);
			
			while(running)
			{
				try
				{
					SmartDashboard.putNumber("Drive-Joystick-X", IO.Drive.getX());
					SmartDashboard.putNumber("Drive-Joystick-Y", IO.Drive.getY());
					SmartDashboard.putNumber("NavX-Yaw", IO.Drive.getYaw());
					
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