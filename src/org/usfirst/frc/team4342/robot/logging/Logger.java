package org.usfirst.frc.team4342.robot.logging;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Logger to make it easier to log the Driver Station and stdout/stderr
 */
public class Logger 
{
	private Logger() {}
	
	private static Severity severity = Severity.DEBUG;
	
	/**
	 * Sets the minimum severity to log (default is Severity.DEBUG)
	 * @param sev the minimum severity to log
	 */
	public static void setSeverity(Severity sev)
	{
		severity = sev;
	}
	
	/**
	 * Logs a message to the Driver Station
	 * @param sev the severity of the message
	 * @param message the message to send
	 * @param t the exception to log to stderr
	 */
	public static void log(Severity sev, String message, Throwable t)
	{
		if(sev.level() <= severity.level())
		{
			if (sev.level() <= Severity.ERROR.level())
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
	
	/**
	 * Debug message to send to the Driver Station
	 * @param message the message to report to the Driver Station
	 */
	public static void debug(String message)
	{
		log(Severity.DEBUG, message, null);
	}
	
	/**
	 * Info message to send to the Driver Station
	 * @param message the message to report to the Driver Station
	 */
	public static void info(String message)
	{
		log(Severity.INFO, message, null);
	}
	
	/**
	 * Warning message to send to the Driver Station
	 * @param message the message to report to the Driver Station
	 */
	public static void warning(String message)
	{
		log(Severity.WARNING, message, null);
	}
	
	/**
	 * Debug message to send to the Driver Station
	 * @param message the message to report to the Driver Station
	 * @param t the exception to print to stderr
	 */
	public static void error(String message, Throwable t)
	{
		log(Severity.ERROR, message, null);
	}
}
