package org.usfirst.frc.team4342.robot.logging;

import edu.wpi.first.wpilibj.DriverStation;

public class Logger 
{
	private Logger() {}
	
	private static Severity severity = Severity.DEBUG;
	
	public static void setSeverity(Severity sev)
	{
		severity = sev;
	}
	
	public static void log(Severity sev, String message, Throwable t)
	{
		if(sev.value() <= severity.value())
		{
			if (sev.value() <= Severity.ERROR.value())
				DriverStation.reportError(sev.toString().toUpperCase() + ": " + message, false);
			else
				DriverStation.reportWarning(sev.toString().toUpperCase() + ": " + message, false);
				
			if(t != null)
			{
				for(StackTraceElement ste : t.getStackTrace())
				{
					System.err.println(ste);
				}
			}
		}
	}
	
	public static void debug(String message)
	{
		log(Severity.DEBUG, message, null);
	}
	
	public static void info(String message)
	{
		log(Severity.INFO, message, null);
	}
	
	public static void warning(String message)
	{
		log(Severity.WARNING, message, null);
	}
	
	public static void error(String message, Throwable t)
	{
		log(Severity.ERROR, message, null);
	}
}
